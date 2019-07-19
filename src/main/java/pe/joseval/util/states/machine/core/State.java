package pe.joseval.util.states.machine.core;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName="Builder",builderMethodName="builder")
@ToString(of= {"stateId","name"})
@EqualsAndHashCode
public class State {
	private UUID stateId;
	//private int stateId;
	@EqualsAndHashCode.Exclude
	@Default
	private String tag=null;
	@EqualsAndHashCode.Exclude
	private String name;
	
	public Optional<UUID> getSafeStateId(){
		return Optional.ofNullable(stateId);
	}
	public Optional<String> getSafeTag(){
		return Optional.ofNullable(tag);
	}
	
}
