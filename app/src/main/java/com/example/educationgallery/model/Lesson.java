package com.example.educationgallery.model;

class Lesson {
    private final String type;
    private final String name;

    public Lesson(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
