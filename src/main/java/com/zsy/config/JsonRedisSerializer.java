package com.zsy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class JsonRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            throw new SerializationException("Error serializing object to JSON: " + o, e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes,Object.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing JSON: " + new String(bytes), e);
        }
    }
}
