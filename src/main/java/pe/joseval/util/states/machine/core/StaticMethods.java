package pe.joseval.util.states.machine.core;

public class StaticMethods {

	public static Node Node(State rootState) {
		return Node.builder().root(rootState).build();
	}

	public static Edge Edge() {
		return new Edge();
	}

	public static State SimpleState(int stateId) {

		return new State.Builder().stateId(stateId).build();
	}
	
	

}
