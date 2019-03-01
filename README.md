# Rules manager

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8da2bd3562464002a0da223bc366bcf9)](https://app.codacy.com/app/joveval/states-machine?utm_source=github.com&utm_medium=referral&utm_content=joveval/states-machine&utm_campaign=Badge_Grade_Dashboard)

Rules manager is a java package to define a rule and validate it through the comparison of expected against actual values.

## Installation
Clone this project in your PC. Then install it using Maven:

``` batch
./mvn install
```
## POM reference

``` xml
<dependency>
	<groupId>io.github.joveval.util</groupId>
	<artifactId>StatesMachine</artifactId>
	<version>1.0.1-alpha</version>
</dependency>
```
## Use
You can define a States Machine as a tree of links between nodes:

*	Define States Machine tree:

``` java
Node node = node(SimpleState(0))

				.withEdge(edge().withCondition(lessOrEqualThan("x", 10))
								.withAction(
											action(ActionType.NAMED_ACTION).withName("ACTION_01")
											
											)
								.toTarget(node(SimpleState(1))
										.withEdge(edge().withCondition(greaterOrEqualThan("x", 8))
														.withAction(
																	action(ActionType.NAMED_ACTION).withName("ACTION_01_01")
																	)
														.toTarget(node(SimpleState(3))))
										.withEdge(edge().withAction(
																	action(ActionType.NAMED_ACTION).withName("ACTION_01_02"))
														.withCondition(greaterOrEqualThan("x", 5))
														.toTarget(node(SimpleState(4))))

						))
				.withEdge(edge().withCondition(lEquals("x", 0))
								.withAction(
											 action(ActionType.NAMED_ACTION).withName("ACTION_00")
											)
								.toTarget(node(SimpleState(2))));

StatesManager<Integer> sm = new StatesManager<>();
node.populateStateManager(sm);
```
*	Populate a map of actual values:

``` java
Map<String, Object> params = new HashMap<>();
params.put("x", 9);
```
*	Use states machine as needed:

``` java
TransitionResponse<Integer> executeTransition = sm.executeTransition(state, params);
```

## License
MIT - See [LICENSE](https://github.com/joveval/states-machine/blob/master/LICENSE) for more details.
