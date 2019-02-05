package pe.joseval.util.states.machine.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransitionResponse {
	
	private String actionToMake;
	private State nextState;
}
