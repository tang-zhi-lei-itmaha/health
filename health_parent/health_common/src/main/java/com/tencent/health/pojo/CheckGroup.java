package com.tencent.health.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 *
 * @author tttt
 * @date 2019/11/18
 */
public class CheckGroup implements Serializable {
    private Integer id;//检查组id
    private String code;//检查组编码
    private String name;//组名称
    private String helpCode;//助记码
    private String sex;//适用性别
    private String remark;//项目说明
    private String attention;//注意事项
    private List<CheckItem> checkItemList;//检查组中所包含的检查项

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<CheckItem> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckItem> checkItemList) {
        this.checkItemList = checkItemList;
    }

    @Override
    public String toString() {
        return "CheckGroup{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", helpCode='" + helpCode + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", attention='" + attention + '\'' +
                ", checkItemList=" + checkItemList +
                '}';
    }
}
