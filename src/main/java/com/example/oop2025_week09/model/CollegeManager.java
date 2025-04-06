package com.example.oop2025_week09.model;

import java.util.*;

//College class will consume Student and Courses
//CollegeManager: findCourseByCode(), findStudentById() -> IN EXAM
public class CollegeManager {
    private String collegeName;
    private List<Student> students;
    private List<Course> courses;
    private int nextStudentId;

    public CollegeManager() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.nextStudentId = 1000;

    }

    public String getCollegeName() {
        return this.collegeName;
    }

/// if no constructor -> addStudent() content
///    public void addStudent(Student student)
///    {
///        if (this.students == null) {
///            this.students = new ArrayList<Student>();
///        }
///        students.add(student);
///    }
///
///    //Method Overloading addStudent
///    public void addStudent(String id, String name) // if there is no constructor
///    {
///        if (this.students == null) {
///            this.students = new ArrayList<Student>();
///        }
///        students.add(new Student(id, name));
///    }

    public Student addStudent(String name, String email) {
        Student student = new Student(nextStudentId++, name, email);
        students.add(student);
        return student;
    }

//    public void addCourse(String id, String courseName) {
//        if (this.courses == null) {
//            this.courses = new ArrayList<Course>();
//        }
//        this.courses.add(new Course(id, courseName));
//    }

    public Course addCourse(String code, String title, int credits, String instructor) {
        Course course = new Course(code, title, credits, instructor);
        courses.add(course);
        return course;
    }
    public boolean registerStudenForCourse(int studId, String courseCode){
        Student student = findStudentById(studId);
        Course course = findCourseByCode(courseCode);

        if (student == null || course == null) {
            return false;
        }
        student.registerForCourse(course);
        return true;
    }
    //IN EXAM
    public Student findStudentById(int studId) {
        for (Student student : students) {
            if (student.getId() == studId) {
                return student;
            }
        }
        return null;
    }
    //IN EXAM
    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if(course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // new ->
    }
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Student student : students) {
            str.append(student.toString());
            str.append("\n");
        }
        for (Course course : courses) {
            str.append(course.toString());
            str.append("\n");
        }
        return str.toString();
    }
    public Map<String, Integer> getCoursesRegistrationStats() {
        Map<String, Integer> stats = new HashMap<>();

        for (Course course : courses) {
            int count = 0;
            for (Student student : students) {
                if (student.isRegisteredFor(course)) {
                    count++;
                }
            }
            stats.put(course.getCode(), count);
        }

        return stats;
    }
    public void setAllStudents(List<Student> students)
    {
        this.students = students;
    }
    public void setAllCourses(List<Course> courses)
    {
        this.courses = courses;
    }
}
