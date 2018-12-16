public class Node implements Comparable <Node> {

    private String nodeId;
    private NodeType type;
    private String ipAddress;
    private int port;

    private int virtualNodeCount;

    public Node() {
        // TODO Auto-generated constructor stub
    }

    public Node(String nodeId, NodeType type, String ipAddress, int port) {
        this.nodeId = nodeId;
        this.type = type;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Node(String nodeId, NodeType type, String ipAddress, int port, int virtualNodeCount) {
        this.nodeId = nodeId;
        this.type = type;
        this.ipAddress = ipAddress;
        this.port = port;
        this.virtualNodeCount = virtualNodeCount;
    }

    public Node(String nodeId, String ipAddress) {
        this.nodeId = nodeId;
        this.ipAddress = ipAddress;
    }



    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getVirtualNodeCount() {
        return virtualNodeCount;
    }

    public void setVirtualNodeCount(int virtualNodeCount) {
        this.virtualNodeCount = virtualNodeCount;
    }

    @Override
    public String toString() {
        return this.getNodeId();
    }

    @Override
    public int compareTo(Node o) {
        // TODO Auto-generated method stub
        return virtualNodeCount - o.virtualNodeCount;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return nodeId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        Node node = (Node) obj;

        return this.nodeId.equals(node.nodeId) && this.type.equals(node.getType());
    }
}
