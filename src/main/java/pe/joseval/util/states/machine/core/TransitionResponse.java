package pe.joseval.util.states.machine.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransitionResponse<T> {
	private State nextState;
	private Action action;

}
