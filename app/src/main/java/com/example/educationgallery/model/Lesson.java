package com.example.educationgallery.model;

public class Lesson {
    private final String type;
    private final String name;
    private final int time;


    public Lesson(String type, String name, int time) {
        this.type = type;
        this.name = name;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public int getTime(){
        return time;
    }

}
