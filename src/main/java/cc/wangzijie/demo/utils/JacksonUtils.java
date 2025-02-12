package cc.wangzijie.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Map;

@Slf4j
public class JacksonUtils {

    /**
     * 把JavaBean转换为json字符串
     */
    public static String toJSONString(Object object) {
        try {
            return toJsonThrows(object);
        } catch (Exception e) {
            log.error("toJSONString error.", e);
        }
        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     */
    public static String toJSONStringPretty(Object object) {
        try {
            return toJsonThrowsPretty(object);
        } catch (Exception e) {
            log.error("toJSONString error.", e);
        }
        return null;
    }

    /**
     * 把json字符串转换为JavaBean
     */
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return parseThrows(json, tClass);
        } catch (Exception e) {
            log.error("JSONString parse error.", e);
        }
        return null;
    }

    /**
     * 把json字符串转换为JavaBean,支持泛型
     */
    public static <T> T parse(String json, TypeReference<T> valueTypeRef) {
        try {
            return parseThrows(json, valueTypeRef);
        } catch (Exception e) {
            log.error("JSONString parse error.", e);
        }
        return null;
    }

    /**
     * 把JavaBean转换为json字符串，抛出异常
     */
    public static String toJsonThrows(Object object) throws Exception {
        if (null == object) {
            return null;
        }
        return getObjectMapper().writeValueAsString(object);
    }

    /**
     * 把JavaBean转换为json字符串，抛出异常
     */
    public static String toJsonThrowsPretty(Object object) throws Exception {
        if (null == object) {
            return null;
        }
        return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }


    /**
     * 把json字符串转换为JavaBean，抛出异常
     */
    public static <T> T parseThrows(String json, Class<T> tClass) throws Exception {
        return getObjectMapper().readValue(json, tClass);
    }

    /**
     * 把json字符串转换为JavaBean，支持泛型，抛出异常
     */
    public static <T> T parseThrows(String json, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return getObjectMapper().readValue(json, valueTypeRef);
    }


    /**
     * 获取 ObjectMapper 实例
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
    }

    private static class JacksonObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 4288193147502386170L;

        JacksonObjectMapper() {
            super(jsonFactory());
            super.setLocale(Locale.CHINA);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            super.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 忽略 transient 关键词属性
            super.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
            // Long 转为 String 防止 js 丢失精度
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            super.registerModule(simpleModule);
            super.registerModule(new JavaTimeModule());
            super.findAndRegisterModules();
        }

        JacksonObjectMapper(ObjectMapper src) {
            super(src);
        }

        private static JsonFactory jsonFactory() {
            return JsonFactory.builder()
                    // 可解析反斜杠引用的所有字符
                    .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                    // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
                    .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true)
                    .build();
        }

        @Override
        public ObjectMapper copy() {
            return new JacksonObjectMapper(this);
        }
    }


    /**
     * 对象转换为 Map
     *
     * @return Map
     */
    public static Map<String, Object> objectToMap(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {});
    }

}
