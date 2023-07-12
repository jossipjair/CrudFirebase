package com.hunter.crudfirebase.model;

public class Pet {

    private String name;
    private String age;
    private String colorPet;

    public Pet() {
    }

    public Pet(String name, String age, String colorPet) {
        this.name = name;
        this.age = age;
        this.colorPet = colorPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColorPet() {
        return colorPet;
    }

    public void setColorPet(String colorPet) {
        this.colorPet = colorPet;
    }
}
