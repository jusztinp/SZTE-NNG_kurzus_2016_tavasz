package navigation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NavigationTest {

	private Algorithm navigation;
	private PathChecker pathChecker;
	private File inputGraph;

	@Before
	public void setUp() {
		pathChecker = new PathChecker();
		pathChecker.initialize("checker.cfg");
		inputGraph = new File("graph.xml");
	}
	
	@Test
	public void hasPathTest01() {
		Algorithm navigation = createNavigation();
		assertTrue(navigation.hasPath(100, 100));
	}
	
	@Test
	public void hasPathTest02() {
		Algorithm navigation = createNavigation();
		assertTrue(navigation.hasPath(1000, 1054));
	}
	
	@Test
	public void hasPathTest03() {
		Algorithm navigation = createNavigation();
		assertFalse(navigation.hasPath(3492, 38439));
	}
	
	@Test
	public void shortestPathTest01() {
		shortestPathTest(3493, 3494, 10200.0);
	}

	@Test
	public void shortestPathTest02() {
		shortestPathTest(15917, 292, 12821.0);
	}
	
	@Test
	public void shortestPathTest03() {
		shortestPathTest(23430, 34446, 2675.0);
	}
	
	@Test
	public void shortestPathTest04() {
		shortestPathTest(6653, 12152, 903.0);
	}
	
	@Test
	public void fastestPathTest01() {
		fastestPathTest(3493, 3494, 137.0);
	}

	@Test
	public void fastestPathTest02() {
		fastestPathTest(15917, 292, 165.0);
	}
	
	@Test
	public void fastestPathTest03() {
		fastestPathTest(23430, 34446, 50.0);
	}
	
	@Test
	public void fastestPathTest04() {
		fastestPathTest(6653, 12152, 20.0);
	}
	
	private void shortestPathTest(final int startNodeId, final int destinationNodeId,
			final double optimum) {
		navigation = createNavigation();
		DistanceResult result = navigation.findShortestPath(startNodeId,
				destinationNodeId);
		assertShortestPathValid(result, startNodeId, destinationNodeId, optimum);
	}
	
	private void fastestPathTest(final int startNodeId, final int destinationNodeId,
			final double optimum) {
		navigation = createNavigation();
		TimeResult result = navigation.findFastestPath(startNodeId, destinationNodeId);
		assertFastestPathValid(result, startNodeId, destinationNodeId, optimum);
	}

	private Algorithm createNavigation() {
		Graph graph = new GraphImpl();
		graph.initializeFromFile(inputGraph);
		Algorithm navigation = new AlgorithmImpl();
		navigation.preprocess(graph);
		return navigation;
	}

	private void assertShortestPathValid(DistanceResult result,
			int startNodeId, int destinationNodeId, double distanceOptimum) {
		assertNotNull(result);
		assertPathValid(result.getResultPath(), startNodeId, destinationNodeId);
		assertTrue(isValueInInterval(0.95 * distanceOptimum,
				result.getTravelDistanceOfResultPath(), 2.0 * distanceOptimum));
	}

	private void assertFastestPathValid(TimeResult result, int startNodeId,
			int destinationNodeId, double timeOptimum) {
		assertNotNull(result);
		assertPathValid(result.getResultPath(), startNodeId, destinationNodeId);
		assertTrue(isValueInInterval(0.95 * timeOptimum,
				result.getTravelTimeOfResultPath(), 2.0 * timeOptimum));
	}

	private void assertPathValid(List<Integer> path, int startNodeId,
			int destinationNodeId) {
		assertNotNull(path);
		assertTrue(pathChecker.isStartingWith(path, startNodeId)
				&& pathChecker.isEndingWith(path, destinationNodeId)
				&& pathChecker.isContinuous(path));
	}

	private static boolean isValueInInterval(double lowerBound,
			double objectiveFunctionValue, double upperBound) {
		return lowerBound <= objectiveFunctionValue
				&& objectiveFunctionValue <= upperBound;
	}
}
