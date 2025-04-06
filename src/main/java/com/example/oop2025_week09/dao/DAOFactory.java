package com.example.oop2025_week09.dao;
//FACTORY PATTERNS - 5 or 6 important ones
//optional
//centralise it to the factory = control flow for better life cycle of our runtime
//MEMORY MANAGEMENT (ensure RAM memory allocation efficiency)
import com.example.oop2025_week09.dao.implementation.CourseDAOImpl;
import com.example.oop2025_week09.dao.implementation.RegistrationDAOImpl;
import com.example.oop2025_week09.dao.implementation.StudentDAOImpl;

public class DAOFactory {
    private static StudentDAO studentDAO;
    private static CourseDAO courseDAO;
    private static RegistrationDAO registrationDAO;

    /**
     * Get the StudentDAO instance
     * @return the StudentDAO
     */
    public static StudentDAO getStudentDAO() {
        if (studentDAO == null) {
            studentDAO = new StudentDAOImpl();
        }
        return studentDAO;
    }

    /**
     * Get the CourseDAO instance
     * @return the CourseDAO
     */
    public static CourseDAO getCourseDAO() {
        if (courseDAO == null) {
            courseDAO = new CourseDAOImpl();
        }
        return courseDAO;
    }

    /**
     * Get the RegistrationDAO instance
     * @return the RegistrationDAO
     */
    public static RegistrationDAO getRegistrationDAO() {
        if (registrationDAO == null) {
            registrationDAO = new RegistrationDAOImpl();
        }
        return registrationDAO;
    }
}

