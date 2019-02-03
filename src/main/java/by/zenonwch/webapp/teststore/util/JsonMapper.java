package by.zenonwch.webapp.teststore.util;

import by.zenonwch.webapp.teststore.exception.JsonParsingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class JsonMapper {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        mapper.configure(MapperFeature.USE_ANNOTATIONS, true);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
    }

    private JsonMapper() {
    }

    public static String toJson(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (final IOException e) {
            final String msg = "Mapping of the object has failed: " + e.getMessage();
            throw new JsonParsingException(msg, e);
        }
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (final IOException e) {
            final String msg = "JSON parsing has failed: " + e.getMessage();
            throw new JsonParsingException(msg, e);
        }
    }

    public static <K, V> Map<K, V> fromJsonToMap(final String json) {
        try {
            return mapper.readValue(json, new HashMapTypeReference<K, V>());
        } catch (final IOException e) {
            final String msg = "JSON parsing has failed: " + e.getMessage();
            throw new JsonParsingException(msg, e);
        }
    }

    private static class HashMapTypeReference<K, V> extends TypeReference<HashMap<K, V>> {
    }
}