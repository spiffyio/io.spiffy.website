package io.spiffy.common.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    public static String serialize(final Object value) {
        if (value == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(value);
        } catch (final JsonGenerationException e) {
        } catch (final JsonMappingException e) {
        } catch (final IOException e) {
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(final Class<T> clazz, final String json) {
        if (clazz == null) {
            return null;
        }

        if (clazz.equals(String.class)) {
            return (T) json;
        }

        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (final JsonParseException e) {
        } catch (final JsonMappingException e) {
        } catch (final IOException e) {
        }

        return null;
    }
}
