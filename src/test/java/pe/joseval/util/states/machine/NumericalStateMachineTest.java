package pe.joseval.util.states.machine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pe.joseval.util.rules.manager.core.StaticConditions.Equals;
import static pe.joseval.util.rules.manager.core.StaticConditions.GreaterOrEqualThan;
import static pe.joseval.util.rules.manager.core.StaticConditions.LessOrEqualThan;
import static pe.joseval.util.rules.manager.core.StaticConditions.NotEquals;
import static pe.joseval.util.states.machine.core.StaticMethods.Edge;
import static pe.joseval.util.states.machine.core.StaticMethods.Node;
import static pe.joseval.util.states.machine.core.StaticMethods.SimpleState;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pe.joseval.util.states.machine.core.Node;
import pe.joseval.util.states.machine.core.State;
import pe.joseval.util.states.machine.core.StatesManager;
import pe.joseval.util.states.machine.core.TransitionResponse;

public class NumericalStateMachineTest {

	@Test
	public void testANumericalStateMachine() {
		
		Node node = Node(SimpleState(0))
					
				.withEdge(Edge()
							  .withCondition(LessOrEqualThan("x",10))
							  .withAction("ACTION_01")
							  .toTarget(
									  Node(SimpleState(1))
									  		.withEdge(Edge().withAction("ACTION_01_01")
									  						.withCondition(GreaterOrEqualThan("x",8 ))
									  						.toTarget(Node(SimpleState(3)))
									  				   )
									  		.withEdge(Edge().withAction("ACTION_01_02")
							  						.withCondition(GreaterOrEqualThan("x",5 ))
							  						.toTarget(Node(SimpleState(4)))
							  				   )
									  
							   ))
				.withEdge(Edge()
								.withCondition(Equals("x",0))
									.withAction("ACTION_00")
									.toTarget(Node(SimpleState(2))));
		
		StatesManager sm = new StatesManager();
		
		assertNotNull(sm.getTransitionMatrix());
		
		node.populateStateManager(sm);
		
		Map<String, Object> params = new HashMap<>();
		params.put("x", 9);
		//node.getRoot();
		
		State state = SimpleState(1);
		
		TransitionResponse executeTransition = sm.executeTransition(state, params);
		
		
		assertNotNull(executeTransition);
		assertTrue(executeTransition.getNextState()!=null);
		assertTrue(executeTransition.getNextState().getStateId()==3);
		assertNotNull(executeTransition.getActionToMake());
		assertNotNull(executeTransition.getActionToMake().equals("ACTION_01_01"));
	}
	
	
	
	
	@Test
	public void testBAutomaticNavigation() {
		
		Node node = Node(SimpleState(0))
					
				.withEdge(Edge()
							  .withCondition(LessOrEqualThan("x",10))
							  .withAction("ACTION_01")
							  .automatic(true)
							  .toTarget(
									  Node(SimpleState(1))
									  		.withEdge(Edge().withAction("ACTION_01_01")
									  						.withCondition(GreaterOrEqualThan("x",8 ))
									  						.toTarget(Node(SimpleState(3)))
									  				   )
									  		.withEdge(Edge().withAction("ACTION_01_02")
							  						.withCondition(GreaterOrEqualThan("x",5 ))
							  						.toTarget(Node(SimpleState(4)))
							  				   )
									  
							   ))
				.withEdge(Edge()
								.withCondition(Equals("x",0))
									.withAction("ACTION_00")
									.toTarget(Node(SimpleState(2))));
		
		StatesManager sm = new StatesManager();
		
		assertNotNull(sm.getTransitionMatrix());
		
		node.populateStateManager(sm);
		
		Map<String, Object> params = new HashMap<>();
		params.put("x", 9);
		//node.getRoot();
		
		State state = SimpleState(0);
		
		TransitionResponse executeTransition = sm.executeTransition(state, params);
		
		
		assertNotNull(executeTransition);
		assertTrue(executeTransition.getNextState()!=null);
		assertTrue(executeTransition.getNextState().getStateId()==3);
		assertNotNull(executeTransition.getActionToMake());
		assertNotNull(executeTransition.getActionToMake().equals("ACTION_01_01"));
	}
	
	
	
	
	@Test
	public void testCHighDepth() {
		
		Node node = Node(SimpleState(0))
					
				.withEdge(Edge()
							  .withCondition(LessOrEqualThan("x",10))
							  .withAction("ACTION_01")
							  .automatic(true)
							  .toTarget(
									  Node(SimpleState(1))
									  		.withEdge(Edge().withAction("ACTION_01_01")
									  						.automatic(true)
									  						.withCondition(GreaterOrEqualThan("x",8 ))
									  						.toTarget(
									  								Node(SimpleState(3))
									  								    .withEdge(Edge()
									  								    			.withAction("ACTION_01_01_01")
									  								    			.withCondition(NotEquals("x",9))
									  								    			.toTarget(Node(SimpleState(5)))
									  								    		)
									  								    .withEdge(Edge()
								  								    			.withAction("ACTION_01_01_02")
								  								    			.withCondition(Equals("x",9))
								  								    			.toTarget(Node(SimpleState(6)))
								  								    		)	
									  									)
									  									
									  									
									  				   )
									  		.withEdge(Edge().withAction("ACTION_01_02")
							  						.withCondition(GreaterOrEqualThan("x",5 ))
							  						.toTarget(Node(SimpleState(4)))
							  				   )
									  
							   ))
				.withEdge(Edge()
								.withCondition(Equals("x",0))
									.withAction("ACTION_00")
									.toTarget(Node(SimpleState(2))));
		
		StatesManager sm = new StatesManager();
		
		assertNotNull(sm.getTransitionMatrix());
		
		node.populateStateManager(sm);
		
		Map<String, Object> params = new HashMap<>();
		params.put("x", 9);
		//node.getRoot();
		
		State state = SimpleState(0);
		
		TransitionResponse executeTransition = sm.executeTransition(state, params);
		
		
		assertNotNull(executeTransition);
		assertTrue(executeTransition.getNextState()!=null);
		assertTrue(executeTransition.getNextState().getStateId()==6);
		assertNotNull(executeTransition.getActionToMake());
		assertNotNull(executeTransition.getActionToMake().equals("ACTION_01_01_02"));
	}
	

}
