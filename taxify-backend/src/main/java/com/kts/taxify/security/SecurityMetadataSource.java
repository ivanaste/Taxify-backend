package com.kts.taxify.security;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SecurityMetadataSource extends AbstractMethodSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(final Method method, final Class<?> targetClass) {
        final HasAnyPermission annotation = findAnnotation(method, targetClass);
        if (annotation != null) {
            return Collections.singletonList(new SecurityAttribute(Arrays.asList(annotation.value())));
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private HasAnyPermission findAnnotation(final Method method, final Class<?> targetClass) {
        // The method may be on an interface, but we need attributes from the target
        // class.
        // If the target class is null, the method will be unchanged.
        final Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        HasAnyPermission annotation = AnnotationUtils.findAnnotation(specificMethod, HasAnyPermission.class);
        if (annotation != null) {
            return annotation;
        }

        // Check the original (e.g. interface) method
        if (specificMethod != method) {
            annotation = AnnotationUtils.findAnnotation(method, HasAnyPermission.class);
            if (annotation != null) {
                return annotation;
            }
        }

        // Check the class-level (note declaringClass, not targetClass, which may not
        // actually implement the method)
        annotation = AnnotationUtils.findAnnotation(specificMethod.getDeclaringClass(), HasAnyPermission.class);
        return annotation;
    }
}
