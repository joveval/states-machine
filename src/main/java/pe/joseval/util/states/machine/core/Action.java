package pe.joseval.util.states.machine.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName="Builder",builderMethodName="builder")
@ToString(of= {"actionType","actionToMake","automatic","customAction"})
public class Action {
	
	private ActionType actionType;
	private String actionToMake;
	@Default
	private boolean automatic = false;
	@Default
	private Map<String, Object> customParams = new HashMap<>();
	private Function<Map<String, Object>, ?> customAction;
	
	public static Action action(ActionType actionType) {
		Action action = new Action();
		action.setActionType(actionType!=null?actionType:ActionType.NAMED_ACTION);
		return action;
	}
	
	
	public Action withName(String actionToMake) {
		this.actionToMake = actionToMake;
		return this;
	}
	
	
	public Action automatic(boolean automatic) {
		this.automatic = automatic;
		return this;
	}
	
	public Action withParam(String key,Object value) {
		if(key==null||value==null)return this;
		this.customParams.put(key, value);
		return this;
	}
	
	public Action withCustomAction(Function<Map<String, Object>, ?> customAction) {
		//log.debug("registering custom action {}",this.hashCode());
		log.debug("Custom action {}",customAction);
		this.customAction = customAction;
		return this;
	}
	
}
