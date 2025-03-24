package com.example.oop2025_week09.model;
import java.util.*;

public class Main {
//    public static void main(String[] args) {
//
//        CollegeManager cm = new CollegeManager("Mel's College");
//        cm.addStudent("2231346", "Mel Tran");
//        cm.addStudent("2439280", "Alice Smith");
//
//        cm.addCourse("420-KBS-AS", "Java EE");
//        cm.addCourse("420-ASP-SS", "Web Appllication");
//
//        System.out.println(cm);
//    }
    public static void main(String[] args) {
        // Create our college manager
        CollegeManager manager = new CollegeManager();

        // Add some students
        Student alice = manager.addStudent("Alice Smith", "alice@example.com");
        Student bob = manager.addStudent("Bob Johnson", "bob@example.com");

        // Add some courses
        Course javaCourse = manager.addCourse("CS101", "Introduction to Java", 3, "Dr. Java");
        Course webDev = manager.addCourse("CS201", "Web Development", 4, "Prof. Web");

        // Register students for courses
        manager.registerStudenForCourse(alice.getId(), javaCourse.getCode());
        manager.registerStudenForCourse(alice.getId(), webDev.getCode());
        manager.registerStudenForCourse(bob.getId(), javaCourse.getCode());

        // Print out student registrations
        System.out.println("Alice's courses:");
        for (Course course : alice.getregisteredCourses()) {
            System.out.println("- " + course);
        }

        System.out.println("\nBob's courses:");
        for (Course course : bob.getregisteredCourses()) {
            System.out.println("- " + course);
        }
    }
}

