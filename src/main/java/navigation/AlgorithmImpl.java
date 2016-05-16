package navigation;

/**
 * Implement your navigation algorithm here. This class will be instantiated
 * during the unit tests.
 */
public class AlgorithmImpl implements Algorithm {
    private GraphImpl g;

    @Override
    public void preprocess(Graph graph) {
        g = (GraphImpl) graph;
    }

    @Override
    public DistanceResult findShortestPath(int startNodeId,
                                           int destinationNodeId) {
        ResultImpl distanceResult = new ResultImpl(startNodeId, destinationNodeId, g);

        return distanceResult;
    }

    @Override
    public TimeResult findFastestPath(int startNodeId, int destinationNodeId) {
        ResultImpl timeResult = new ResultImpl(startNodeId, destinationNodeId, g);

        return timeResult;
    }

    @Override
    public boolean hasPath(int startNodeId, int destinationNodeId) {
        ResultImpl isLegit = new ResultImpl(startNodeId,destinationNodeId,g);
        return isLegit.hasPath;
    }

}
