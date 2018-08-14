package com.example.pratishparija.pos.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "userRoleMapping_table")
public class UserRoleMapping {
    @NonNull
    @PrimaryKey
    private String id;
    private String userId;
    private String roleId;

    public UserRoleMapping() {
    }

    public UserRoleMapping(String roleId, String id) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
