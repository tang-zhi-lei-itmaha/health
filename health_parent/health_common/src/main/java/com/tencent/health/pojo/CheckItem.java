package com.tencent.health.pojo;

import java.io.Serializable;

/**
 * 检查项实体类
 *
 * @author tttt
 * @date 2019/11/18
 */
public class CheckItem implements Serializable {
    private Integer id;//检查项id
    private String code;//项目编码
    private String name;//项目名称
    private String sex;//适用的性别
    private String age;//适用的年龄
    private Float price;//价格
    private String type;//检查项类型，分为检查与检验两种
    private String attention;//注意事项
    private String remark;//项目说明

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", attention='" + attention + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
