package com.kts.taxify.security;

import com.kts.taxify.model.Permission;
import com.kts.taxify.model.User;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PermissionVoter implements AccessDecisionVoter<MethodInvocation> {
    @Override
    public boolean supports(final ConfigAttribute attribute) {
        return attribute instanceof SecurityAttribute;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return MethodInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(final Authentication authentication, final MethodInvocation object, final Collection<ConfigAttribute> attributes) {
        final Optional<SecurityAttribute> securityAttribute = attributes.stream()
                .filter(attr -> attr instanceof SecurityAttribute).map(SecurityAttribute.class::cast).findFirst();

        if (securityAttribute.isEmpty()) {
            return ACCESS_ABSTAIN;
        }

        final List<Permission> requiredPermissions = Arrays.stream(securityAttribute.get().getAttribute().split(",")).map(Permission::valueOf).toList();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return ACCESS_ABSTAIN;
        }

        final User user = (User) authentication.getPrincipal();

        for (final Permission requiredPermission : requiredPermissions) {
            if (!user.getRole().getPermissions().contains(requiredPermission)) {
                return ACCESS_DENIED;
            }
        }

        return ACCESS_GRANTED;
    }
}
