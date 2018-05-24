package com.example.w2d3_as1;

public class Person {

    private String name;
    private String email;
    private int age;

    public Person(String inputName, String inputEmail, int inputAge){
        this.name = inputName;
        this.email = inputEmail;
        this.age = inputAge;
    }

    public String toString(){
        return "Name: " + name + "; Email: " + email + "; Age: " + age + "\n";
    }

}
