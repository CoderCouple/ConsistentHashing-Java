import java.util.List;

public interface ShardingInterface {

    public List<Node> getNode(Message message);

}