package pe.joseval.util.states.machine.core;

public class StaticMethods {

	public static Node node(State rootState) {
		return Node.builder().root(rootState).build();
	}

	public static Edge edge() {
		return new Edge();
	}

	public static State simpleState(int stateId) {

		return new State.Builder().stateId(stateId).build();
	}
	
	

}
