package com.cloud.coretest.beans;

import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/28
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

@Table(name = "test")
public class TestBean {

    @Column(name = "id",isId = true,isIndex = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
