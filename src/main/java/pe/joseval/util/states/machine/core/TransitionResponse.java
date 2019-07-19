package pe.joseval.util.states.machine.core;

import java.util.Optional;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransitionResponse<T> {
	private UUID nextState;
	private Action action;

	public Optional<UUID> getSafeNextState(){
		return Optional.ofNullable(nextState);
	}
	
	public Optional<Action> getSafeAction(){
		return Optional.ofNullable(action);
	}
	
}
