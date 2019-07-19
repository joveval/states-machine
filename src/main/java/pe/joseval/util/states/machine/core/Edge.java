package pe.joseval.util.states.machine.core;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pe.joseval.util.rules.manager.core.Condition;
import pe.joseval.util.states.machine.core.StateTransition.TransitionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of= {"condition","targetNode"})
public class Edge{

	private Condition condition;
	private Node targetNode;
	private TransitionType transitionType=TransitionType.TO_MYSELF;
	private String goTo;
	private Action action;

	//Safe values getters
	public Optional<Condition> getSafeCondition(){
		return Optional.ofNullable(condition);
	}
	public Optional<Node> getSafeNode(){
		return Optional.ofNullable(targetNode);
	}
	public Optional<TransitionType> getSafeTransitionType(){
		return Optional.of(transitionType);
	}
	public Optional<String> getSafeGoTo(){
		return Optional.ofNullable(goTo);
	}
	public Optional<Action> getSafeAction(){
		return Optional.ofNullable(action);
	}
	
	//After Construct setters
	public Edge goToInit() {
		this.transitionType = TransitionType.TO_INIT;
		return this;
	}
	public Edge goToParent() {
		this.transitionType = TransitionType.TO_PARENT;
		return this;
	}
	public Edge withCondition(Condition condition) {
		this.condition = condition;
		return this;
	}
	public Edge goTo(String nodeTag) {
		this.transitionType = TransitionType.TO_TAG;
		this.goTo = nodeTag;
		return this;
	}
	public Edge toTarget(Node targetNode) {
		this.transitionType = TransitionType.TO_NEXT;
		this.targetNode = targetNode;
		return this;
	}
	
		
	public Edge withAction(Action action){
		this.action = action;
		return this;
	}
	
	
}
