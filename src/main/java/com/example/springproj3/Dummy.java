package com.example.springproj3;

public class Dummy {
    public String name;
    public String city;
    public int age;

    public Dummy(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    public Dummy() {}

    @Override
    public String toString(){
        return String.format("{city=%s, name=%s, age=%s}", city, name, age);
    }
}
