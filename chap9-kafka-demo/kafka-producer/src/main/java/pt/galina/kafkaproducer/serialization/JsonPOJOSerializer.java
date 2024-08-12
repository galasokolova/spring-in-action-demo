package pt.galina.kafkaproducer.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import pt.galina.kafkaproducer.exception.DeserializationException;

public class JsonPOJOSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) return new byte[0];

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new DeserializationException("Serialization of data failed for topic " + topic, e);
        }
    }

    @Override
    public void close() {
        // closing resources
    }
}

