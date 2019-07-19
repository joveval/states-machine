package pe.joseval.util.states.machine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.joseval.util.rules.manager.core.ConditionValidationException;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class StatesManager<T> {

	private Set<UUID> states = new HashSet<>();
	private Map<String, UUID> taggedStates = new HashMap<>();
	private List<StateTransition> transitionMatrix = new ArrayList<>();
	private UUID initState;

	public Optional<Set<UUID>> getSafeStates() {
		return Optional.of(states);
	}

	public Optional<List<StateTransition>> getSafeTransitionMatrix() {
		return Optional.of(transitionMatrix);
	}

	public Optional<UUID> getSafeInitState() {
		return Optional.ofNullable(initState);
	}

	public void initStatesManager(Optional<Node> rootNode) {
		addStateRegister(rootNode);
		populateStateManager(rootNode, Optional.empty());
	}

	public void addStateRegister(Optional<Node> nodeOpt) {

		Optional<UUID> rootOpt = nodeOpt.flatMap(Node::getSafeStateId);
		UUID rootId = rootOpt.orElseThrow(RuntimeException::new);
		boolean isRoot = nodeOpt.flatMap(Node::safeIsRoot).orElse(Boolean.FALSE);
		List<Edge> edgesFromNode = nodeOpt.flatMap(Node::getSafeChildren).orElseGet(ArrayList::new);

		if (isRoot)
			initState = rootId;
		states.add(rootId);
		nodeOpt.flatMap(Node::getSafeTag).ifPresent(t->this.taggedStates.put(t,rootId));
		edgesFromNode.forEach(e -> this.addStateRegister(e.getSafeNode()));
	}

	public void populateStateManager(Optional<Node> nodeOpt, Optional<Node> parent) {
		if (!nodeOpt.isPresent())
			return;

		UUID stateId = nodeOpt.flatMap(Node::getSafeStateId).orElseThrow(RuntimeException::new);
		List<Edge> edgesFromNode = nodeOpt.flatMap(Node::getSafeChildren).orElseGet(ArrayList::new);
		edgesFromNode.forEach(e-> {
			StateTransition sT = StateTransition.builder()
						.condition(e.getCondition())
						.init(stateId)
						.action(e.getAction()).build();
			switch (e.getTransitionType()) {
			case TO_INIT:
				sT.setEnd(initState);
				break;
			case TO_MYSELF:
				sT.setEnd(stateId);
				break;
			case TO_NEXT:
				sT.setEnd(e.getSafeNode().flatMap(Node::getSafeStateId).orElseThrow(RuntimeException::new));
				break;
			case TO_PARENT:
				sT.setEnd(parent.flatMap(Node::getSafeStateId).orElseThrow(RuntimeException::new));
				break;
			case TO_TAG:
				String tag = nodeOpt.flatMap(Node::getSafeTag).orElseThrow(RuntimeException::new);
				Optional<UUID> taggedStateOpt = Optional.ofNullable(this.taggedStates.get(tag));
				sT.setEnd(taggedStateOpt.orElseThrow(RuntimeException::new));
				break;
			default:
				break;

			}
			this.transitionMatrix.add(sT);
			this.populateStateManager(e.getSafeNode(), nodeOpt);
		});
		

	}


	public Optional<TransitionResponse<T>> executeTransition(Optional<UUID> currStateOpt, Map<String, Object> params) {

		UUID currState = currStateOpt.orElseGet(()->this.initState);
		List<StateTransition> transitionsThatApply = transitionMatrix.stream()
				.filter(st -> st.getInit().equals(currState)).collect(Collectors.toList());

		Optional<StateTransition> selectedStateTransitionOpt = transitionsThatApply.stream().filter(st->{
			try{
				boolean validation= st.getCondition().runValidation(params);
				log.debug("{}->{} APPLIES?:{}", st.getInit(), st.getEnd(), validation);
				return validation;
			}catch(ConditionValidationException e) {
				log.error("ERROR", e);
				return false;
			}
		}).findFirst();
		
		
		try(StateTransition selectedStateTransition = selectedStateTransitionOpt.orElseThrow(RuntimeException::new)){
			TransitionResponse<T> transitionResponse = new TransitionResponse<>();

			transitionResponse.setNextState(selectedStateTransition.getEnd());

			if (selectedStateTransition.getAction().isAutomatic()) {
				log.debug("Automatic transition");
				return this.executeTransition(selectedStateTransition.getSafeEnd(), params);
			} else {
				log.debug("Action = {}", selectedStateTransition.getAction());
				transitionResponse.setAction(selectedStateTransition.getAction());
				return Optional.ofNullable(transitionResponse);
			}
		}catch(RuntimeException e) {
			log.error("No link applies to these params. Falling to default null response");
			return Optional.empty();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Unespected error:",e1);
			return Optional.empty();
		}
		

	}

}
