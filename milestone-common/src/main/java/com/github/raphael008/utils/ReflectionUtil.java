package com.github.raphael008.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtil {
    public static <T> void copySameField(T source, T target) {
        if (ObjectUtils.allNotNull(source, target)) {
            Class<?> sourceClass = source.getClass();
            Class<?> targetClass = target.getClass();

            Field[] sourceFields = sourceClass.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getClass().getDeclaredFields();

            List<String> sourceFieldNameList = Arrays.stream(sourceFields)
                    .map(Field::getName)
                    .collect(Collectors.toList());
            List<String> targetFieldNameList = Arrays.stream(targetFields)
                    .map(Field::getName)
                    .collect(Collectors.toList());

            Collection<String> sameFieldNameList = CollectionUtils.retainAll(sourceFieldNameList, targetFieldNameList);

            for (String sameFieldName : sameFieldNameList) {
                try {
                    Field sourceField = sourceClass.getDeclaredField(sameFieldName);
                    Field targetField = targetClass.getDeclaredField(sameFieldName);

                    Type sourceFieldType = sourceField.getGenericType();
                    Type targetFieldType = targetField.getGenericType();

                    if (sourceFieldType == targetFieldType) {
                        Object sourceFieldValue = sourceField.get(source);
                        targetField.set(target, sourceFieldValue);
                    }
                } catch (NoSuchFieldException ignored) {
                    // Ignored
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Map source = new HashMap();
        List target = new ArrayList();
        copySameField(source, target);
    }
}
