package pe.joseval.util.states.machine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class StatesManager {
	
	private Map<Integer, State> states = new HashMap<>();
	private List<StateTransition> transitionMatrix = new ArrayList<>();

	public StatesManager addState(State state){
		states.put(state.getStateId(), state);
		return this;
	}
	
	public StatesManager addTransition(StateTransition transition){
		transitionMatrix.add(transition);
		return this;
	}
	
	public TransitionResponse executeTransition(State currenState, Map<String, Object> params) {
		
		List<StateTransition> statesWichConcerns = transitionMatrix.stream().filter(st->st.getInit().equals(currenState)).collect(Collectors.toList());
		
		StateTransition selectedStateTransition = null;
		for(StateTransition st: statesWichConcerns) {
			try {
				boolean response = st.getCondition().runValidation(params);
				log.debug("{}->{} APPLIES?:{}",st.getInit().getStateId(),st.getEnd().getStateId(),response);
				if(response) {
					selectedStateTransition = st;
					break;
				}
				
			} catch (ConditionValidationException e) {
				// TODO Auto-generated catch block
				log.error("ERROR",e);
				continue;
			}
		}
		
		
		if(selectedStateTransition!=null) {
			log.debug("{} APPLIES becauseOf {}",selectedStateTransition.getEnd(),selectedStateTransition.getCondition());
			
			TransitionResponse transitionResponse = new TransitionResponse();
			transitionResponse.setActionToMake(selectedStateTransition.getActionToMake());
			transitionResponse.setNextState(selectedStateTransition.getEnd());
			if(selectedStateTransition.isAutomatic()) {
				log.debug("Automatic transition");
				return executeTransition(selectedStateTransition.getEnd(),params);
			}else {
				return transitionResponse;
			}
			
		}else {
			log.error("No link applies to these params. Falling to default null response");
			return null;
		}
		
	}

	public static class Builder{
		private Map<Integer, State> states=new HashMap<>();
		private List<StateTransition> transitionMatrix = new ArrayList<>();
		
		public Builder registerState(State state){
			states.put(state.getStateId(), state);
			return this;
		}
		
		public Builder registerStateTransition(StateTransition stateTransition){
			transitionMatrix.add(stateTransition);
			return this;
		}
		
		public StatesManager build(){
			return new StatesManager(states, transitionMatrix);
		}
		
	}
}
