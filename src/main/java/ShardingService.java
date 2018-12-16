
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class ShardingService {
    private ConsistentHash hash = null;

    // public static ShardingService service;
    protected static Logger logger = Logger.getLogger("ShardingService");


    protected static AtomicReference<ShardingService> service = new AtomicReference<ShardingService>();

    private ShardingService() {
        List<Node> list = new ArrayList<>();

        Node n1= new Node("N1",NodeType.PRIMARY,"10.0.0.1",50051,3);
        Node n2= new Node("N2",NodeType.PRIMARY,"10.0.0.2",50052,3);
        Node n3= new Node("N3",NodeType.PRIMARY,"10.0.0.3",50053,3);
        Node n4= new Node("N4",NodeType.PRIMARY,"10.0.0.4",50054,3);
        Node n5= new Node("N5",NodeType.PRIMARY,"10.0.0.5",50055,3);

        list.addAll(Arrays.asList(n1,n2,n3,n4,n5));

        MurmurHash128_Algo_3 m = new MurmurHash128_Algo_3();
        int virtualNodes = Integer.parseInt(YAMLConfigLoader.getProperty(Constants.VIRTUAL_NODES));
        int numberOfReplicas = Integer.parseInt(YAMLConfigLoader.getProperty(Constants.REPLICATION_FACTOR));

        hash = new ConsistentHash(m, numberOfReplicas, list, virtualNodes);
    }

    public static ShardingService getInstance() {
        service.compareAndSet(null, new ShardingService());
        return service.get();

    }

    public static void main(String[] args) {
        ShardingService service = ShardingService.getInstance();
        service.reset();

        List<Node> nodes = service.getNodes(new Message("dsjchskjdgvugdsvks"));


        for (Node node : nodes) {
            System.out.println(node.getIpAddress());
        }

    }

    public List<Node> getNodes(Message message) {
        List<Node> list = new ArrayList<>();
        if (hash != null)
            list = hash.get(message.getUniqueKey());
        return list;
    }

    public void reset() {
        service.set(null);
        getInstance();
    }

}
