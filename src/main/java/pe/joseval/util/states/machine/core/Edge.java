package pe.joseval.util.states.machine.core;

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
	private Action action;

	public Edge withCondition(Condition condition) {
		this.condition = condition;
		return this;
	}

	public Edge toTarget(Node targetNode) {
		this.targetNode = targetNode;
		return this;
	}
	
		
	public Edge withAction(Action action){
		this.action = action;
		return this;
	}
	
	
}
