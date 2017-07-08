package com.zeroisbiggerthanone.pcs.entities;


import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    public final static String USER_ROLE_NAME = "ROLE_USER";
    public final static String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    private String id;
    private String roleName;

    public Role() {
    }

    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "com.zeroisbiggerthanone.pcs.entities.Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public String getAuthority() {
        return roleName;
    }
}
