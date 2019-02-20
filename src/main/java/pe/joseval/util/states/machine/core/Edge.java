package pe.joseval.util.states.machine.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pe.joseval.util.rules.manager.core.Condition;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of= {"condition","targetNode"})
public class Edge{

	private Condition condition;
	private Node targetNode;
	private String actionToMake;
	private boolean automatic = false;
	private Map<String, Object> customParams = new HashMap<>();
	private Function<Map<String, Object>, ?> customAction;

	public Edge withCondition(Condition condition) {
		this.condition = condition;
		return this;
	}

	public Edge toTarget(Node targetNode) {
		this.targetNode = targetNode;
		return this;
	}
	
		
	public Edge withAction(String actionToMake){
		this.actionToMake = actionToMake;
		return this;
	}
	
	public Edge automatic(boolean automatic) {
		this.automatic = automatic;
		return this;
	}
	
	public Edge withParam(String key,Object value) {
		this.customParams.put(key, value);
		return this;
	}
	
	public Edge withCustomAction(Function<Map<String, Object>,?> function) {
		this.customAction = function;
		return this;
	}
	
}
