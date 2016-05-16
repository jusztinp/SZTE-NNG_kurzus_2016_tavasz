package navigation;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * Created by Peti on 2016. 05. 16..
 */
public class GraphMLHandler extends DefaultHandler {

    private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();

    private static final String NODE = "node";
    private static final String EDGE = "edge";
    private static final String PROPERTY = "property";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String YCOORD = "yCoord";
    private static final String XCOORD = "xCoord";
    private static final String STARTNODE = "startNode";
    private static final String ENDNODE = "endNode";
    private static final String WEIGHT = "averageSpeed";

    private boolean isY;
    private boolean isX;
    private boolean isStart;
    private boolean isEnd;
    private boolean isWeight;

    private int startNodeId;
    private int endNodeId;
    private int weight;

    private Node tmpNode;


    public GraphMLHandler(HashMap<Integer, Node> input) {
        this.graph = input;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(NODE)) {
            tmpNode = new Node();
            int id = Integer.parseInt(attributes.getValue(ID));
            tmpNode.setId(id);
        } else if (qName.equalsIgnoreCase(PROPERTY)) {
            if (attributes.getValue(NAME).equalsIgnoreCase(YCOORD)) {
                isY = true;
            } else if (attributes.getValue(NAME).equalsIgnoreCase(XCOORD)) {
                isX = true;
            } else if (attributes.getValue(NAME).equalsIgnoreCase(STARTNODE)) {
                isStart = true;
            } else if (attributes.getValue(NAME).equalsIgnoreCase(ENDNODE)) {
                isEnd = true;
            } else if (attributes.getValue(NAME).equalsIgnoreCase(WEIGHT)) {
                isWeight = true;
            }
        }else if(qName.equalsIgnoreCase(EDGE)){
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase(NODE)){
            graph.put(tmpNode.getId(),tmpNode);
        }else if(qName.equalsIgnoreCase(EDGE)){
            graph.get(startNodeId).addNeighbor(graph.get(endNodeId), weight);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isY){
            double y = Double.parseDouble(new String(ch, start ,length));
            tmpNode.setY(y);
            isY = false;
        }else if(isX){
            double x = Double.parseDouble(new String(ch, start ,length));
            tmpNode.setX(x);
            isX = false;
        }else if(isStart){
            int id = Integer.parseInt(new String(ch,start,length));
            startNodeId = id;
            isStart = false;
        }else if(isEnd){
            int id = Integer.parseInt(new String(ch,start,length));
            endNodeId = id;
            isEnd = false;
        }else if(isWeight){
            int w = Integer.parseInt(new String(ch,start,length));
            weight = w;
            isWeight = false;
        }
    }
}
