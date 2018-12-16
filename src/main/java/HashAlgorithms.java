import java.util.Properties;

public interface HashAlgorithms {
    void init(Properties conf);

    Long hash(Object value);
}