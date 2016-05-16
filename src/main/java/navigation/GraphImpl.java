package navigation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;


import javax.xml.parsers.*;


/**
 * Implement your graph representation here. This class will be instantiated
 * during the unit tests.
 */
public class GraphImpl implements Graph {
    private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
    public static final String MSG_SUCCESSFUL = "exit parsing";
    public static final String ELEMENT_NAME_1 = "node";
    public static final String ELEMENT_NAME_2 = "edge";

    public HashMap<Integer, Node> getGraph() {
        return graph;
    }

    @Override
    public void initializeFromFile(File inputXmlFile) {
        // TODO Auto-generated method stub
        try {

            //readGraphMLDOM(inputXmlFile);
            readGraphMLSAX(inputXmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(MSG_SUCCESSFUL);
    }

    /*protected void readGraphMLDOM(File inputXmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputXmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Root element: " +
                doc.getDocumentElement().getNodeName());
        seperateEdgesAndNodes(doc);
    }

    protected void seperateEdgesAndNodes(Document doc) {
        NodeList nList = doc.getElementsByTagName(ELEMENT_NAME_1);
        NodeList vList = doc.getElementsByTagName(ELEMENT_NAME_2);
        readNodes(nList);
        readEdges(vList);
    }

    protected void readNodes(NodeList nList) {
        iterateThroughNODENodes(nList);
    }

    protected void iterateThroughNODENodes(NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
//                System.out.println("\nCurrent element: " +
//                        nNode.getNodeName());

            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
//                System.out.println("Node id: " +
//                        eElement.getAttribute("id"));

                readNODENodeElementProperties(eElement);
            }
        }
    }

    protected void readNODENodeElementProperties(Element eElement) {
        int tempId = Integer.parseInt(eElement.getAttribute("id"));

        double xCord = Double.parseDouble(
                eElement.getElementsByTagName("property").item(0).getTextContent());
        double yCord = Double.parseDouble(
                eElement.getElementsByTagName("property").item(1).getTextContent());

        graph.put(tempId, new Node(xCord, yCord, tempId));

    }/**/

    protected void readEdges(NodeList vList) {
        iterateThroughEDGENodes(vList);
    }

    protected void iterateThroughEDGENodes(NodeList vList) {
        for (int temp = 0; temp < vList.getLength(); temp++) {
            org.w3c.dom.Node vNode = vList.item(temp);
//                System.out.println("\nCurrent element: " +
//                        nNode.getNodeName());

            if (vNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) vNode;
//                System.out.println("Node id: " +
//                        eElement.getAttribute("id"));
                readEDGENodeElementProperties(eElement);
            }
        }
    }

    protected void readEDGENodeElementProperties(Element eElement) {
        int start = Integer.parseInt(
                eElement.getElementsByTagName("property").item(0).getTextContent());
        int end = Integer.parseInt(
                eElement.getElementsByTagName("property").item(1).getTextContent());
        int weight = Integer.parseInt(
                eElement.getElementsByTagName("property").item(2).getTextContent());

        graph.get(start).addNeighbor(graph.get(end), weight);

    }/**/

    protected void readGraphMLSAX(File inputXmlFile)throws Exception{
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphMLHandler graphMLHandler = new GraphMLHandler(graph);
            saxParser.parse(inputXmlFile,graphMLHandler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
