package pe.joseval.util.states.machine.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName="Builder",builderMethodName="builder")
@ToString(of= {"stateId","name"})
@EqualsAndHashCode
public class State {
	private int stateId;
	@EqualsAndHashCode.Exclude
	private String name;
	@EqualsAndHashCode.Exclude
	private String description;
	
	
	
}
