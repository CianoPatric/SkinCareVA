package com.example.skincareva;

public class UserSession {
    public static String id;
    public static String email;
    public static String gender;
    public static int age;

    public static void clear(){
        id = null; email = null; gender = null; age = 0;
    }
}
