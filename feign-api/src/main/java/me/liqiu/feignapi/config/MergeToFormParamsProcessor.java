package me.liqiu.feignapi.config;

import feign.MethodMetadata;
import lombok.val;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import static feign.Util.checkState;

public class MergeToFormParamsProcessor implements org.springframework.cloud.openfeign.AnnotatedParameterProcessor {

    private static final Class<MergeToFormParams> ANNOTATION_CLASS = MergeToFormParams.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION_CLASS;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context, Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        Class<?> parameterType = method.getParameterTypes()[parameterIndex];
        MethodMetadata data = context.getMethodMetadata();

        if (Map.class.isAssignableFrom(parameterType)) {
            checkState(data.queryMapIndex() == null,
                    "Query map can only be present once.");
            data.queryMapIndex(parameterIndex);
            return true;
        }

        val propsDesc = PropertyUtils.getPropertyDescriptors(parameterType);

        for(val desc : propsDesc) {
            if(desc.getReadMethod() != null && !desc.getName().equals("class")) {
                String name = desc.getName();
                context.setParameterName(name);
                data.formParams().add(name);
            }
        }

        return true;
    }
}
