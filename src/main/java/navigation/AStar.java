package navigation;

import java.util.*;

/**
 * Created by Peti on 2016. 05. 08..
 */
public class AStar {
    private TreeSet<Node> openList;
    private HashSet<Node> closedList;
    HashMap<Node, Integer> gVals;
    HashMap<Node, Integer> fVals;

    public HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
    List<Integer> res = new ArrayList<Integer>();


    public AStar(GraphImpl g) {
        gVals = new HashMap<Node, Integer>();
        fVals = new HashMap<Node, Integer>();
        openList = new TreeSet<Node>();
        closedList = new HashSet<Node>();
        graph = g.getGraph();
    }


    public void astar(int startNodeId, int endNodeId) {
        openList.clear();
        closedList.clear();

        Node start = graph.get(startNodeId);
        Node end = graph.get(endNodeId);

        gVals.put(start, 0);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.first();
            if (current.equals(end)) {
                System.out.println("Reached the end!");
                printPath(current);
                return;
            }
            closedList.add(openList.pollFirst());
            HashMap<Node, Integer> neighbors = current.getNeighbors();
            for (Map.Entry<Node, Integer> neighbor : neighbors.entrySet()) {
                Node tmpNeighbor = neighbor.getKey();
                int gScore = gVals.get(current) + neighbor.getValue();
                int fScore = gScore + h(tmpNeighbor, current);

                if (closedList.contains(tmpNeighbor)) {
                    if (gVals.get(tmpNeighbor) == null) {
                        gVals.put(tmpNeighbor, gScore);
                    }
                    if (fVals.get(tmpNeighbor) == null) {
                        fVals.put(tmpNeighbor, fScore);
                    }

                    if (fScore >= fVals.get(tmpNeighbor)) {
                        continue;
                    }
                }
                if (openList.contains(tmpNeighbor) || fScore < fVals.get(tmpNeighbor)) {
                    tmpNeighbor.setParent(current);
                    gVals.put(tmpNeighbor, gScore);
                    fVals.put(tmpNeighbor, fScore);
                    if (!openList.contains(tmpNeighbor)) {
                        openList.add(tmpNeighbor);
                    }
                }
            }
        }
        System.out.print("Fail");
    }

    public int h(Node i1, Node i2) {
        return 0;
    }

    private void printPath(Node node) {
        System.out.println(node.getData());

        while (node.getParent() != null) {
            node = node.getParent();
            res.add(node.getId());
            System.out.println(node.getData());
        }
    }
}
