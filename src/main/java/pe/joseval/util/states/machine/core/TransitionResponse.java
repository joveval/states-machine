package pe.joseval.util.states.machine.core;

import java.util.Map;
import java.util.function.Function;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransitionResponse<T> {
	
	private String actionToMake;
	private State nextState;
	private Map<String, Object> customParams;
	private Function<Map<String, Object>, T> customAction;

}
