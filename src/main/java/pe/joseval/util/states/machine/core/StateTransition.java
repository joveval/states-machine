package pe.joseval.util.states.machine.core;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.joseval.util.rules.manager.core.Condition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName="Builder",builderMethodName="builder")
public class StateTransition implements AutoCloseable{
	private UUID init;
	private UUID end;
	//private TransitionType transitionType;
	//private String targetTag;
	private Condition condition;
	private Action action;
	
	public Optional<UUID> getSafeInit(){
		return Optional.ofNullable(init);
	}
	public Optional<UUID> getSafeEnd(){
		return Optional.ofNullable(end);
	}
	
	public static enum TransitionType{
		TO_NEXT,TO_TAG,TO_MYSELF,TO_INIT,TO_PARENT
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
