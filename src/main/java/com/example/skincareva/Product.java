package com.example.skincareva;

public class Product {
    private int id;
    private String name;
    private String type;
    private String description;
    private String skin_type;
    private String gender;
    private int min_age;

    public int getId() {return id;}

    public Product() {}

    public Product(String name, String type, String description, String skin_type, String gender, int min_age){
        this.name = name;
        this.type = type;
        this.description = description;
        this.skin_type = skin_type;
        this.gender = gender;
        this.min_age = min_age;
    }

    public String getName() {return name;}
    public String getType() {return type;}
    public String getDescription() {return description;}
    public String getSkinType() {return  skin_type;}
    public String getGender() {return gender;}
    public int getMinAge() {return min_age;}
}
