import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;


public class ConsistentHash {

    private final HashAlgorithms hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Long, Node> circle = new TreeMap<Long, Node>();
    protected static Logger logger = Logger.getLogger("ConsistentHash");

    public ConsistentHash(HashAlgorithms hashFunction, int numberOfReplicas, Collection<Node> nodes, int virtualNodes) {

        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        for (Node node : nodes) {
            for (int i = 1; i <= virtualNodes; i++) {

                addNode(node, i);
            }

        }

        logger.info("Formed consistent hashing ring : " + circle);
    }

    public void addNode(Node node, int id) {

        Long hash = hashFunction.hash(node.toString() + id);
        circle.put(hash, node);

    }

    public void remove(Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public List<Node> get(Object key) {
        // SortedSet<RNode> list = new TreeSet<>();

        List<Node> list = new ArrayList<>();
        System.out.println("Circle Size : "+circle.size());
        if (circle.isEmpty()) {
            return null;
        }
        Long hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, Node> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }

        // get next nodes
        SortedMap<Long, Node> tailMap = new TreeMap<>();
        tailMap.putAll(circle.tailMap(hash));

        if (tailMap.size() < numberOfReplicas) {

            tailMap.putAll(circle.tailMap(circle.firstKey()));
        }

        int k = numberOfReplicas;

        for (Long h : tailMap.keySet()) {
            Node rnode = null;
            rnode = tailMap.get(h);
            if (k == numberOfReplicas) {

                rnode.setType(NodeType.PRIMARY);
            } else {
                rnode.setType(NodeType.REPLICA);
            }

            list.add(tailMap.get(h));
            k--;
            if (0 == k) {
                break;
            }

        }

        // return new ArrayList<>(list);
        return list;
    }

}