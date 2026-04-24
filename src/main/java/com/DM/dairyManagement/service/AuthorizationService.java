package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.User;
import org.springframework.stereotype.Service;

/**
 * Service for handling role-based access control (RBAC) permissions.
 * Defines permission checks for all user roles: OWNER, MANAGER, EMPLOYEE, ACCOUNTANT.
 */
@Service
public class AuthorizationService {

    /**
     * Check if user has permission to perform an action
     * 
     * @param user The user requesting access
     * @param action The action to check (e.g., "DELETE_BILL", "CREATE_PAYMENT")
     * @return true if user has permission, false otherwise
     */
    public boolean hasPermission(User user, String action) {
        if (user == null) return false;
        if (!user.isActive()) return false; // Deactivated users have no access
        
        User.Role role = user.getRole();
        
        switch (action) {
            // DELETE actions - Only OWNER
            case "DELETE_BILL":
            case "DELETE_COMPANY":
            case "DELETE_PRODUCT":
            case "DELETE_CUSTOMER":
            case "DELETE_PAYMENT":
            case "DELETE_USER":
            case "DELETE_EMPLOYEE":
                return role == User.Role.OWNER;
                
            // EDIT actions - OWNER and MANAGER
            case "EDIT_BILL":
            case "EDIT_COMPANY":
            case "EDIT_PRODUCT":
            case "EDIT_STOCK":
            case "EDIT_CUSTOMER":
            case "EDIT_EMPLOYEE":
                return role == User.Role.OWNER || role == User.Role.MANAGER;
                
            // CREATE actions - OWNER, MANAGER, EMPLOYEE
            case "CREATE_BILL":
            case "CREATE_PAYMENT":
            case "CREATE_CUSTOMER":
            case "CREATE_PRODUCT":
            case "CREATE_COMPANY":
                return role == User.Role.OWNER || role == User.Role.MANAGER || role == User.Role.EMPLOYEE;
                
            // VIEW actions - All roles (but some restricted)
            case "VIEW_BILL":
            case "VIEW_STOCK":
            case "VIEW_CUSTOMER":
            case "VIEW_PRODUCT":
            case "VIEW_COMPANY":
                return true;
                
            // FINANCIAL reports - Only OWNER and ACCOUNTANT
            case "VIEW_EXPENSES":
            case "VIEW_FINANCIAL_REPORTS":
            case "VIEW_PAYMENT_HISTORY":
            case "EXPORT_DATA":
            case "VIEW_SALARIES":
                return role == User.Role.OWNER || role == User.Role.ACCOUNTANT;
                
            // MANAGE actions - Role-specific
            case "MANAGE_EMPLOYEES":
            case "MANAGE_USERS":
            case "VIEW_AUDIT_LOGS":
            case "SYSTEM_SETTINGS":
                return role == User.Role.OWNER;
                
            case "MANAGE_EXPENSES":
                return role == User.Role.OWNER;
                
            // PAYMENT actions
            case "RECORD_PAYMENT":
                return role == User.Role.OWNER || role == User.Role.MANAGER || 
                       role == User.Role.EMPLOYEE || role == User.Role.ACCOUNTANT;
                
            default:
                return false;
        }
    }
    
    /**
     * Check if user is owner
     */
    public boolean isOwner(User user) {
        return user != null && user.getRole() == User.Role.OWNER;
    }
    
    /**
     * Check if user is manager
     */
    public boolean isManager(User user) {
        return user != null && user.getRole() == User.Role.MANAGER;
    }
    
    /**
     * Check if user is employee (restricted access)
     */
    public boolean isEmployee(User user) {
        return user != null && user.getRole() == User.Role.EMPLOYEE;
    }
    
    /**
     * Check if user is accountant
     */
    public boolean isAccountant(User user) {
        return user != null && user.getRole() == User.Role.ACCOUNTANT;
    }
    
    /**
     * Get user role name
     */
    public String getUserRoleName(User user) {
        if (user == null) return "UNKNOWN";
        return user.getRole().name();
    }
}
