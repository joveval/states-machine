package pe.joseval.util.states.machine.core;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
@Builder(builderClassName="Builder",builderMethodName="builder")
@ToString(of= {"stateId","shortName"})
@EqualsAndHashCode
public class State {
	private UUID stateId;
	@EqualsAndHashCode.Exclude
	private String shortName;
	
	public Optional<UUID> getSafeStateId(){
		return Optional.ofNullable(stateId);
	}
	public Optional<String> getSafeShortName(){
		return Optional.ofNullable(shortName);
	}
	
}
