package org.kainos.ea.cli;

/**
 * Represents a role the user can can have.
 */
public enum UserRole {
    /**
     * User role.
     */
    User(1),

    /**
     * Admin role.
     */
    Admin(2);

    /**
     * The integer of the role.
     */
    private final int userRole;

    /**
     * The string representations of the role.
     */
    private static final String[] ROLE_NAMES = new String[] {"User", "Admin"};

    /**
     * Private constructor to create a new role.
     * @param userRole the role to use
     */
    UserRole(final int userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets the int associated with the enum.
     * @return role id
     */
    public int getUserRole() {
        return this.userRole;
    }

    /**
     * Gets the enum from the role id.
     * @param id the id to check
     * @return the role
     */
    public static UserRole getUserRoleFromId(final int id) {
        switch (id) {
            case 1: return UserRole.User;
            case 2: return UserRole.Admin;
            default: return null;
        }
    }

    /**
     * Gets the string representation of the current role.
     * @return the role name as a string
     */
    @Override
    public String toString() {
        return ROLE_NAMES[this.userRole - 1];
    }
}
