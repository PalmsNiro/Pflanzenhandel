package com.example.pflanzenhandel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
/**
 * Configures method security for the application and implements a custom PermissionEvaluator.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration implements PermissionEvaluator {

    /**
     * Creates a custom MethodSecurityExpressionHandler that uses this instance as the PermissionEvaluator.
     *
     * @return a MethodSecurityExpressionHandler with the custom PermissionEvaluator
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(this);
        return expressionHandler;
    }
    /**
     * Evaluates whether the user has the given permission on the target domain object.
     *
     * @param auth the authentication object representing the user
     * @param targetDomainObject the target domain object
     * @param permission the permission to check
     * @return true if the user has the permission, false otherwise
     */
    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    /**
     * Evaluates whether the user has the given permission on the target domain object identified by its ID.
     *
     * @param auth the authentication object representing the user
     * @param targetId the ID of the target domain object
     * @param targetType the type of the target domain object
     * @param permission the permission to check
     * @return true if the user has the permission, false otherwise
     */
    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }

    /**
     * Checks if the user has the specified privilege on the target type.
     *
     * @param auth the authentication object representing the user
     * @param targetType the type of the target domain object
     * @param permission the permission to check
     * @return true if the user has the privilege, false otherwise
     */
    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().startsWith(targetType) &&
                    grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
