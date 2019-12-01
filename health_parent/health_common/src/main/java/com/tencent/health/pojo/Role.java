package com.tencent.health.pojo;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色实体类
 *
 * @Author: Tang Zhilei
 * @Date: Create in 18:34 2019/11/28
 */
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String keyword;
    private String description;
    private Set<User> userSet;
    private LinkedHashSet<Menu> menuSet;
    private Set<Permission> permissionSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public LinkedHashSet<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(LinkedHashSet<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
