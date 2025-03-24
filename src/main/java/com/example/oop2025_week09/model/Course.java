package com.example.oop2025_week09.model;

import java.util.List;
//COURSES: hashCode, override equals()

public class Course{
    private String code;
    private String title;
    private int credits;
    private String instructor;
    Course(String code, String title) {
        this.code = code;
        this.title = title;
        this.credits = 0;
        this.instructor = "";
    }
    Course(String code, String title, int credits, String instructor) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
    }
    //properties
    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
    public int getCredits() {
        return this.credits;
    }

    public String getInstructor() {
        return this.instructor;
    }
    @Override
    public String toString() {
        return "Course: [code =" + code + ", title=" + title + "]";
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    //equals() and hashCode() = ALWAYS NEEDED for sorting and finding features
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // this is the class
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return code.equals(course.code);
    }

    @Override //sorting and finding feature -> equalsn and hashCode()
    public int hashCode() {
        return code.hashCode();
    }
}
