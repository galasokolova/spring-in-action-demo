package pt.galina.kafka_listener.deserialization;

import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            throw new RuntimeException("Deserialization of data failed for topic " + topic, e);
        }
    }

    @Override
    public void close() {
        // Закрыть ресурсы, если это необходимо
    }
}

