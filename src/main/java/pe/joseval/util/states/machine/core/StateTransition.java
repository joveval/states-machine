package pe.joseval.util.states.machine.core;

import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
	private String actionToMake;
	private Condition condition;
	@Default
	private boolean automatic = false;
	private Map<String,Object> customParams;
	private Function<Map<String, Object>,?> customAction;
	
	

}
