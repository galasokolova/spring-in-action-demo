package pt.galina.kafkalistener.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import pt.galina.kafkalistener.exception.DeserializationException;

public class JsonPOJODeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> tClass;

    public JsonPOJODeserializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;

        try {
            return objectMapper.readValue(data, tClass);
        } catch (Exception e) {
            throw new DeserializationException("Deserialization of data failed for topic " + topic, e);
        }
    }

    @Override
    public void close() {
        // closing resources
    }
}
