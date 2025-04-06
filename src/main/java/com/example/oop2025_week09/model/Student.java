package com.example.oop2025_week09.model;

import java.util.ArrayList;
import java.util.List;
//Courses: Lists -> .contains(), .remove(), .add()
public class Student {
    private int id;
    private String name;
    private String email;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.email = "";
        this.registeredCourses = new ArrayList<Course>();
    }
    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registeredCourses = new ArrayList<>();
    }
    public Student(int id, String name, List<Course> registeredCourses) {
        this.id = id;
        this.name = name;
        this.registeredCourses = registeredCourses;
    }
    //properties
    public int getId(){return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public List<Course> getregisteredCourses() {return registeredCourses;}

    //Methods
    public void registerForCourse(Course course){
        this.registeredCourses.add(course);
    }
    public void dropCourse(Course course){
        this.registeredCourses.remove(course);
    }
    public boolean isRegisteredFor(Course course){
        return this.registeredCourses.contains(course);
    }
    @Override
    public String toString() {
        return "Student: [id =" + id + ", name=" + name + "]";
    }
}

