package com.tencent.health.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: Tang Zhilei
 * @Date: Create in 18:39 2019/11/28
 */
public class Permission implements Serializable {
    private Integer id;
    private String name;
    private String keyword;
    private String description;
    private Set<Role> roleSet;

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

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }
}
