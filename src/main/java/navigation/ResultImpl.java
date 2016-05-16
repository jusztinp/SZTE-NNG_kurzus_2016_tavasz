package navigation;

import sun.rmi.server.InactiveGroupException;

import java.sql.Time;
import java.util.*;

/**
 * Created by Peti on 2016. 05. 13..
 */
public class ResultImpl implements DistanceResult, TimeResult, Result {
    private double travelDistanceOfResultPath;
    private double travelTimeOfResultPath;
    private List<Integer> resultPath;
    HashMap<Integer, Node> graph;
    private Node start;
    private Node end;

    public TreeSet<Node> openSet;
    public HashSet<Node> closedSet;
    public HashMap<Node, Double> gVals;
    public HashMap<Node, Double> fVals;

    public boolean hasPath = false;


    public ResultImpl(int startNodeId, int endNodeId,GraphImpl g) {
        this.travelDistanceOfResultPath = 0.0f;
        this.travelTimeOfResultPath = 0.0f;
        this.resultPath = new LinkedList<Integer>();
        this.graph = g.getGraph();
        this.start = graph.get(startNodeId);
        this.end = graph.get(endNodeId);

        openSet = new TreeSet<Node>(new NodeComparator());
        closedSet = new HashSet<Node>();
        gVals = new HashMap<Node, Double>();
        fVals = new HashMap<Node, Double>();
        setFVals();
        astar();
    }

    private void setFVals(){
        Iterator it = graph.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Node> pair = (Map.Entry)it.next();
            pair.getValue().setFs(calcDistance(pair.getValue(),end));
            fVals.put(pair.getValue(),Double.POSITIVE_INFINITY);
        }
    }

    private void astar(){
        closedSet.clear();
        openSet.clear();

        gVals.put(start,0.0);
        openSet.add(start);

        while(!openSet.isEmpty()){
            Node current = openSet.first();
            if(current.equals(end)){
                System.out.println("success");
                printPath(current);
                return;
            }

            closedSet.add(openSet.pollFirst());
            HashMap<Node, Integer> neighbors = current.getNeighbors();

            for(Map.Entry<Node, Integer> neighbor : neighbors.entrySet()){
                Node tmpNeighbor = neighbor.getKey();
                double gScore = gVals.get(current) + calcDistance(current,tmpNeighbor);
                double fScore = gScore + heuristicFunction(tmpNeighbor,end);

                if(closedSet.contains(tmpNeighbor)){
                    if(gVals.get(tmpNeighbor)==null){ gVals.put(tmpNeighbor,gScore);}
                    if(fVals.get(tmpNeighbor)==null){ fVals.put(tmpNeighbor,fScore);}
                    if(fScore >= fVals.get(tmpNeighbor)){ continue; }
                }

                if((openSet.contains(tmpNeighbor) || fScore < fVals.get(tmpNeighbor))&&!closedSet.contains(tmpNeighbor)){
                    tmpNeighbor.setParent(current);
                    gVals.put(tmpNeighbor,gScore);
                    fVals.put(tmpNeighbor,fScore);
                    if(!openSet.contains(tmpNeighbor)){
                        openSet.add(tmpNeighbor);
                    }
                }
            }
        }
        System.out.println("fail");
    }

    private double heuristicFunction(Node i1, Node i2){
        //return calcDistance(i1,i2);
        return 0;
    }

    private void printPath(Node node) {
        hasPath = true;
        resultPath.add(node.getId());
        //System.out.println(node.toString());
        while (node.getParent() != null) {
            travelDistanceOfResultPath+=calcDistance(node,node.getParent());
            travelTimeOfResultPath+=calcDistance(node,node.getParent())/(node.getParent().getNeighbors().get(node));
            node = node.getParent();
            //System.out.println(node.toString());
            resultPath.add(node.getId());
        }


        Collections.reverse(resultPath);
        System.out.println("Start: "+resultPath.get(0)+"\nEnd: "+resultPath.get(resultPath.size()-1)+"\nDistance: "+travelDistanceOfResultPath+  "\nTime: "+ travelTimeOfResultPath);
    }

    private double calcDistance(Node i1, Node i2) {
        double x = i1.getX() - i2.getX();
        double y = i1.getY() - i2.getY();
        return Math.sqrt((x * x) + (y * y));
    }

    @Override
    public double getTravelDistanceOfResultPath() {
        return travelDistanceOfResultPath;
    }

    @Override
    public List<Integer> getResultPath() {
        return resultPath;
    }

    @Override
    public double getTravelTimeOfResultPath() {
        return travelTimeOfResultPath;
    }
}

class NodeComparator implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getFs()>o2.getFs()) return 1;
        else return -1;
    }
}
