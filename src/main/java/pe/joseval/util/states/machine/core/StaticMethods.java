package pe.joseval.util.states.machine.core;

import java.util.UUID;

public class StaticMethods {

	public static Node root() {
		return Node.builder().stateId(simpleState()).root(Boolean.TRUE).build();
	}
	public static Node node() {
		return Node.builder().stateId(simpleState()).build();
	}
	public static Node node(UUID stateId) {
		return Node.builder().stateId(stateId).build();
	}

	public static Edge edge() {
		return new Edge();
	}

	public static UUID simpleState() {

		return UUID.randomUUID();
	}
	
	

}
