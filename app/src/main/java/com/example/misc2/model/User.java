package com.example.misc2.model;

public class User {
    private String name;
    private String desc;
    private String state;
    private int age;

    public User(String name, String desc, String state, int age) {
        this.name = name;
        this.desc = desc;
        this.state = state;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age +
                '}';
    }
}
