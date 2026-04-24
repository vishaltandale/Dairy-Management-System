package com.DM.dairyManagement.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to specify required permissions for controller methods.
 * Used in conjunction with AuthorizationService for RBAC.
 * 
 * Example usage:
 * @RequiresPermission("DELETE_BILL")
 * public ResponseEntity<?> deleteBill(@PathVariable Long id) { ... }
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    /**
     * The permission required to access this method
     * e.g., "DELETE_BILL", "CREATE_PAYMENT", "VIEW_EXPENSES"
     */
    String value();
    
    /**
     * Optional message to display when access is denied
     */
    String message() default "You do not have permission to perform this action";
}
