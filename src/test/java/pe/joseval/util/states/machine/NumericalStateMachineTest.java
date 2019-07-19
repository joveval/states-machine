package pe.joseval.util.states.machine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pe.joseval.util.rules.manager.core.StaticConditions.lEquals;
import static pe.joseval.util.rules.manager.core.StaticConditions.greaterOrEqualThan;
import static pe.joseval.util.rules.manager.core.StaticConditions.lessOrEqualThan;
import static pe.joseval.util.rules.manager.core.StaticConditions.lNotEquals;
import static pe.joseval.util.states.machine.core.Action.action;
import static pe.joseval.util.states.machine.core.StaticMethods.edge;
import static pe.joseval.util.states.machine.core.StaticMethods.node;
import static pe.joseval.util.states.machine.core.StaticMethods.root;
import static pe.joseval.util.states.machine.core.StaticMethods.simpleState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.Test;

import pe.joseval.util.states.machine.core.Action;
import pe.joseval.util.states.machine.core.ActionType;
import pe.joseval.util.states.machine.core.Node;
import pe.joseval.util.states.machine.core.State;
import pe.joseval.util.states.machine.core.StatesManager;
import pe.joseval.util.states.machine.core.TransitionResponse;


public class NumericalStateMachineTest {

	@Test
	public void testANumericalStateMachine() {

		Node node = root()

				.withEdge(edge().withCondition(lessOrEqualThan("x", 10))
								.withAction(
											action(ActionType.NAMED_ACTION).withName("ACTION_01")
											
											)
								.toTarget(node()
										.withEdge(edge().withCondition(greaterOrEqualThan("x", 8))
														.withAction(
																	action(ActionType.NAMED_ACTION).withName("ACTION_01_01")
																	)
														.toTarget(node()))
										.withEdge(edge().withAction(
																	action(ActionType.NAMED_ACTION).withName("ACTION_01_02"))
														.withCondition(greaterOrEqualThan("x", 5))
														.toTarget(node()))

						))
				.withEdge(edge().withCondition(greaterOrEqualThan("x", 11))
								.withAction(
											 action(ActionType.NAMED_ACTION).withName("ACTION_00")
											));

		StatesManager<Integer> sm = new StatesManager<>();
		sm.initStatesManager(Optional.of(node));
		assertNotNull(sm.getTransitionMatrix());

		//node.populateStateManager(sm);

		Map<String, Object> params = new HashMap<>();
		params.put("x", 11);
		// node.getRoot();

		UUID initialState = sm.getInitState();

		Optional<TransitionResponse<Integer>> executeTransitionOpt = sm.executeTransition(Optional.of(initialState), params);

		assertTrue(executeTransitionOpt.isPresent());
		assertTrue(executeTransitionOpt.map(TransitionResponse::getSafeNextState).isPresent());
		assertTrue(executeTransitionOpt.map(TransitionResponse::getAction).isPresent());
		assertTrue(executeTransitionOpt.map(TransitionResponse::getAction).map(Action::getActionToMake).orElse("NONE").equals("ACTION_00"));
		//assertTrue(executeTransition.getNextState().getStateId() == 3);
		//assertTrue(executeTransition.getAction());
		//assertTrue(executeTransition.getAction().getActionToMake().equals("ACTION_00"));
	}
	/*
	 * @Test public void testBAutomaticNavigation() {
	 * 
	 * Node node = node(simpleState(0))
	 * 
	 * .withEdge(edge().withCondition(lessOrEqualThan("x", 10)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01") .automatic(true) )
	 * .toTarget(node(simpleState(1))
	 * .withEdge(edge().withCondition(greaterOrEqualThan("x", 8)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01_01") )
	 * .toTarget(node(simpleState(3)))) .withEdge(edge().withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01_02"))
	 * .withCondition(greaterOrEqualThan("x", 5)) .toTarget(node(simpleState(4))))
	 * 
	 * )) .withEdge(edge().withCondition(lEquals("x", 0)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_00") )
	 * .toTarget(node(simpleState(2))));
	 * 
	 * 
	 * 
	 * StatesManager<Integer> sm = new StatesManager<>();
	 * 
	 * assertNotNull(sm.getTransitionMatrix());
	 * 
	 * node.populateStateManager(sm);
	 * 
	 * Map<String, Object> params = new HashMap<>(); params.put("x", 9); //
	 * node.getRoot();
	 * 
	 * State state = simpleState(0);
	 * 
	 * 
	 * TransitionResponse<Integer> executeTransition = sm.executeTransition(state,
	 * params);
	 * 
	 * assertNotNull(executeTransition); assertTrue(executeTransition.getNextState()
	 * != null); assertTrue(executeTransition.getNextState().getStateId() == 3);
	 * assertNotNull(executeTransition.getAction());
	 * assertTrue(executeTransition.getAction().getActionToMake().equals(
	 * "ACTION_01_01")); }
	 * 
	 * @Test public void testCHighDepth() {
	 * 
	 * 
	 * Node node = node(simpleState(0))
	 * 
	 * .withEdge(edge().withCondition(lessOrEqualThan("x", 10)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01") .automatic(true) )
	 * .toTarget(node(simpleState(1))
	 * .withEdge(edge().withCondition(greaterOrEqualThan("x", 8)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01_01") .automatic(true) )
	 * .toTarget(node(simpleState(3)) .withEdge(edge() .withAction(
	 * action(ActionType.NAMED_ACTION) .withName("ACTION_01_01_01"))
	 * .withCondition(lNotEquals("x", 9)).toTarget(node(simpleState(5))))
	 * .withEdge(edge().withAction( action(ActionType.NAMED_ACTION)
	 * .withName("ACTION_01_01_02")) .withCondition(lEquals("x",
	 * 9)).toTarget(node(simpleState(6))))
	 * 
	 * ) ) .withEdge(edge().withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01_02"))
	 * .withCondition(greaterOrEqualThan("x", 5)) .toTarget(node(simpleState(4))))
	 * 
	 * )) .withEdge(edge().withCondition(lEquals("x", 0)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_00") )
	 * .toTarget(node(simpleState(2))));
	 * 
	 * 
	 * StatesManager<Integer> sm = new StatesManager<>();
	 * 
	 * assertNotNull(sm.getTransitionMatrix()); node.populateStateManager(sm);
	 * 
	 * Map<String, Object> params = new HashMap<>(); params.put("x", 9); //
	 * node.getRoot();
	 * 
	 * State state = simpleState(0);
	 * 
	 * 
	 * TransitionResponse<Integer> executeTransition = sm.executeTransition(state,
	 * params);
	 * 
	 * assertNotNull(executeTransition); assertTrue(executeTransition.getNextState()
	 * != null); assertTrue(executeTransition.getNextState().getStateId() == 6);
	 * assertNotNull(executeTransition.getAction());
	 * assertTrue(executeTransition.getAction().getActionToMake().equals(
	 * "ACTION_01_01_02"));
	 * 
	 * }
	 * 
	 * @Test public void testDCustomParams() {
	 * 
	 * 
	 * Node node = node(simpleState(0))
	 * 
	 * .withEdge(edge().withCondition(lessOrEqualThan("x", 10)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01") .automatic(true) )
	 * .toTarget(node(simpleState(1))
	 * .withEdge(edge().withCondition(greaterOrEqualThan("x", 8)) .withAction(
	 * action(ActionType.NAMED_ACTION) .withName("ACTION_01_01") .automatic(true) )
	 * .toTarget(node(simpleState(3)) .withEdge(edge() .withAction(
	 * action(ActionType.NAMED_ACTION) .withName("ACTION_01_01_01") )
	 * .withCondition(lNotEquals("x", 9)).toTarget(node(simpleState(5))))
	 * .withEdge(edge().withAction( action(ActionType.LAMBDA_ACTION)
	 * .withName("ACTION_01_01_02") .withCustomAction(new Function<Map<String,
	 * Object>, Integer>() {
	 * 
	 * @Override public Integer apply( Map<String, Object> t) { // TODO
	 * Auto-generated // method stub Integer i = (Integer)t.get("x");
	 * 
	 * 
	 * return i * i; } })) .withCondition(lEquals("x",
	 * 9)).toTarget(node(simpleState(6))))
	 * 
	 * ) ) .withEdge(edge().withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_01_02"))
	 * .withCondition(greaterOrEqualThan("x", 5)) .toTarget(node(simpleState(4))))
	 * 
	 * )) .withEdge(edge().withCondition(lEquals("x", 0)) .withAction(
	 * action(ActionType.NAMED_ACTION).withName("ACTION_00") )
	 * .toTarget(node(simpleState(2))));
	 * 
	 * 
	 * StatesManager<Integer> sm = new StatesManager<>();
	 * 
	 * assertNotNull(sm.getTransitionMatrix()); node.populateStateManager(sm);
	 * 
	 * Map<String, Object> params = new HashMap<>(); params.put("x", 9); //
	 * node.getRoot();
	 * 
	 * State state = simpleState(0);
	 * 
	 * 
	 * TransitionResponse<Integer> executeTransition = sm.executeTransition(state,
	 * params); assertNotNull(executeTransition.getAction());
	 * assertTrue(executeTransition.getAction().getActionToMake().equals(
	 * "ACTION_01_01_02")); Function<Map<String, Object>, ?> function =
	 * executeTransition.getAction().getCustomAction(); assertNotNull(function);
	 * Integer result = (Integer)function.apply(params);
	 * assertNotNull(executeTransition); assertTrue(executeTransition.getNextState()
	 * != null); assertTrue(executeTransition.getNextState().getStateId() == 6);
	 * assertTrue("Function works correctly",result.equals(81));
	 * 
	 * 
	 * }
	 */

}
