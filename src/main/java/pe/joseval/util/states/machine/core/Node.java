package pe.joseval.util.states.machine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder", builderMethodName = "builder")
public class Node {
	private UUID stateId;
	@Default
	private Boolean root = Boolean.FALSE;
	private String tag;
	@Default
	private List<Edge> children = new ArrayList<>();
	
	public Optional<Boolean> safeIsRoot(){
		return Optional.ofNullable(root);
	}
	public Optional<UUID> getSafeStateId() {
		return Optional.ofNullable(stateId);
	}

	public Optional<String> getSafeTag() {
		return Optional.ofNullable(tag);
	}

	public Optional<List<Edge>> getSafeChildren() {
		return Optional.ofNullable(children);
	}

	public Node withEdge(Edge edge) {
		Optional<List<Edge>> childrenOpt = Optional.of(children);
		childrenOpt.orElseThrow(RuntimeException::new).add(edge);
		return this;
	}

}
