package com.wyett.fastjson;

/**
 * @author : wyettLei
 * @date : Created in 2020/7/6 10:43
 * @description: TODO
 */

public class Dog {
    private Integer age;
    private String name;
    private String color;
    private String owner;

    public Dog(Integer age, String name, String color, String owner) {
        this.age = age;
        this.name = name;
        this.color = color;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
