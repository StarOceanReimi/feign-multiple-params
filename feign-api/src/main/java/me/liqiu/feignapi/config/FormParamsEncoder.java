package me.liqiu.feignapi.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.util.LinkedMultiValueMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormParamsEncoder extends SpringEncoder {

    public FormParamsEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        requestBody = expandRequestBodyIfPossible(requestBody);
        super.encode(requestBody, bodyType, request);
    }

    private Object expandRequestBodyIfPossible(Object requestBody) {
        if(requestBody instanceof LinkedHashMap) {
            LinkedMultiValueMap<String, Object> expandable = new LinkedMultiValueMap<>();
            LinkedHashMap<String, ?> variables = (LinkedHashMap<String, ?>) requestBody;
            for(Map.Entry<String, ?> entry : variables.entrySet()) {
                String name = entry.getKey();
                Object o = entry.getValue();
                try {
                    Object prop = PropertyUtils.getProperty(o, name);
                    expandable.add(name, prop);
                } catch (NoSuchMethodException |
                        InvocationTargetException |
                        IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if(expandable.size() == variables.size()) return expandable;
        }
        return requestBody;
    }
}
