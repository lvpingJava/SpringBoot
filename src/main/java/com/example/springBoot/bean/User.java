package com.example.springBoot.bean;

import java.io.Serializable;

/**
 * @author lvping
 * @version 1.0.0
 * @date 17/8/15 下午1:58.
 * @blog http://blog.didispace.com
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
