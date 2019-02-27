package pe.joseval.util.states.machine.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.joseval.util.rules.manager.core.Condition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName="Builder",builderMethodName="builder")
public class StateTransition {
	private State init;
	private State end;
	private Condition condition;
	private Action action;
	
	

}
