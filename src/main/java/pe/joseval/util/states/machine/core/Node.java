package pe.joseval.util.states.machine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder", builderMethodName = "builder")
public class Node {

	private State root;
	// @Singular(value = "child")
	@Default
	private List<Edge> children = new ArrayList<>();

	public Node withEdge(Edge edge) {
		//log.info(edge.getClass().toGenericString());
		//log.info(edge.toString());
		try {
			children.add(edge);
		} catch (Exception e) {
			log.debug("ERROR:", e);
		}
		return this;
	}

	public void populateStateManager(StatesManager statesManager) {

		if (!statesManager.getStates().containsKey(root.getStateId()))
			statesManager.addState(root);
		// log.debug("State = {}",root.getStateId());
		for (Edge edge : children) {

			List<StateTransition> intersection = statesManager.getTransitionMatrix().stream().filter(st -> {

				// log.debug("Init = {},End = {}",st.getInit(),st.getEnd());
				return st.getInit().equals(root) && st.getEnd().equals(edge.getTargetNode().getRoot());
			}).collect(Collectors.toList());
			if (intersection.size() > 0) {
				log.warn(
						"There is an intersection in transition matrix. This is because of certain states join to each other more than once.");
				continue;
			}

			StateTransition sT = StateTransition.builder().condition(edge.getCondition()).init(root)
					.end(edge.getTargetNode().getRoot()).build();
			// sT.setAction(edge.getAction());
			sT.setAutomatic(edge.isAutomatic());
			sT.setActionToMake(edge.getActionToMake());
			statesManager.addTransition(sT);

			if (!statesManager.getStates().containsKey(edge.getTargetNode().getRoot().getStateId()))
				statesManager.addState(edge.getTargetNode().getRoot());

			edge.getTargetNode().populateStateManager(statesManager);
		}
	}
}
