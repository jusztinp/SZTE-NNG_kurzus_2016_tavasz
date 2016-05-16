package navigation;

import java.util.*;

/**
 * Created by Peti on 2016. 05. 10..
 */
class Node{

    private Node parent;
    private int id;
    private HashMap<Node, Integer> neighbors;
    private double x;
    private double y;
    private double fs;
    private Object data;

    public Node() {
        neighbors = new HashMap<Node, Integer>();
        data = new Object();
        this.fs = 0;
    }

    public Node(double x, double y) {
        this();
        this.x = x;
        this.y = y;

    }


    public Node(double x, double y, int id) {
        this();
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Node(Node parent) {
        this();
        this.parent = parent;
    }

    public Node(Node parent, double x, double y) {
        this();
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    public HashMap<Node, Integer> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(HashMap<Node, Integer> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Node node, Integer weight) {
        this.neighbors.put(node, weight);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXY(double x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setFs(double f){
        this.fs = f;
    }

    public double getFs(){
        return fs;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Node n) {
        return this.x == n.x && this.y == n.y;
    }

    public String toString(){
        return "Id: " + id + " X: " + x + " Y: " + y;
    }
}
