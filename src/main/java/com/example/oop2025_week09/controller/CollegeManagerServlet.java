package com.example.oop2025_week09.controller;
import  com.example.oop2025_week09.model.*;

import com.example.oop2025_week09.model.CollegeManager;
import com.example.oop2025_week09.model.Course;
import com.example.oop2025_week09.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet("/manager")
public class CollegeManagerServlet extends HttpServlet {


///    doGet() for WEEK09-PART1 (final)
///    protected void doGet(HttpServletRequest request, HttpServletResponse response)
///            throws ServletException, IOException {
///
///        // Get or create a session for this user
///        HttpSession session = request.getSession();
///        // Get our CollegeManager from the session (or create a new one)
///        CollegeManager manager = (CollegeManager) session.getAttribute("collegeManager");
///
///        if (manager == null) {
///            // First time visiting - create a new manager with sample data
///            manager = new CollegeManager();
///
///            // Add some sample students
///            Student alice = manager.addStudent("Alice Smith", "alice@example.com");
///            Student bob = manager.addStudent("Bob Johnson", "bob@example.com");
///
///            // Add some sample courses
///            Course javaCourse = manager.addCourse("CS101", "Introduction to Java", 3, "Dr. Java");
///            Course webDev = manager.addCourse("CS201", "Web Development", 4, "Prof. Web");
///            Course math = manager.addCourse("MATH101", "Calculus I", 4, "Dr. Calculus");
///
///            // Register some students for courses
///            manager.registerStudenForCourse(alice.getId(), javaCourse.getCode());
///            manager.registerStudenForCourse(bob.getId(), javaCourse.getCode());
///            manager.registerStudenForCourse(bob.getId(), math.getCode());
///            manager.registerStudenForCourse(alice.getId(), webDev.getCode());
///
///            // Save the manager in the session
///            session.setAttribute("collegeManager", manager);
///        }
///
///        // Add the manager to the request for the JSP to access
///        request.setAttribute("collegeManager", manager);
///
///        // Forward to the JSP page
///        request.getRequestDispatcher("/WEB-INF/views/manager.jsp").forward(request, response);
///    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); //creates a token that is specific and unique to the user
        CollegeManager manager = getCollegeManager(session);

        // Check if we're viewing a specific course
        String courseCode = request.getParameter("courseCode");
        if (courseCode != null && !courseCode.isEmpty()) {
            Course course = manager.findCourseByCode(courseCode);
            if (course != null) {
                request.setAttribute("course", course);

                // Find all students registered for this course
                List<Student> enrolledStudents = new ArrayList<>();
                for (Student student : manager.getAllStudents()) {
                    if (student.isRegisteredFor(course)) {
                        enrolledStudents.add(student);
                    }
                }
                request.setAttribute("enrolledStudents", enrolledStudents);

                request.getRequestDispatcher("/WEB-INF/views/courseDetails.jsp").forward(request, response);
                return;
            }
        }

        String action = request.getParameter("action");
        if ("searchStudents".equals(action)) {
            String query = request.getParameter("q");
            if (query != null && !query.trim().isEmpty()) {
                List<Student> searchResults = new ArrayList<>();

                // Check if query is numeric (student ID)
                try {
                    int studentId = Integer.parseInt(query);
                    Student student = manager.findStudentById(studentId);
                    if (student != null) {
                        searchResults.add(student);
                    }
                } catch (NumberFormatException e) {
                    // Not a numeric ID, search by name
                    for (Student student : manager.getAllStudents()) {
                        if (student.getName().toLowerCase().contains(query.toLowerCase())) {
                            searchResults.add(student);
                        }
                    }
                }

                request.setAttribute("searchResults", searchResults);
                request.setAttribute("searchQuery", query);
            }
        }

        // If no course code specified, show the main page
        request.setAttribute("collegeManager", manager);
        request.getRequestDispatcher("/WEB-INF/views/manager.jsp").forward(request, response);
    }
    ///WEEK09- PART2
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session and college manager
        HttpSession session = request.getSession();
        CollegeManager manager = (CollegeManager) session.getAttribute("collegeManager");

        // If somehow we don't have a manager, create one
        if (manager == null) {
            manager = new CollegeManager();
            session.setAttribute("collegeManager", manager);
        }

        // Get the action parameter to determine what operation to perform
        String action = request.getParameter("action");

        if ("addStudent".equals(action)) {
            addStudent(request, manager);
        } else if ("addCourse".equals(action)) {
            addCourse(request, manager);
        } else if ("registerStudentForCourse".equals(action)) {
            registerStudentForCourse(request, manager);
        }
        else if ("dropCourse".equals(action)) {
            dropCourse(request, manager);
        }

        // Redirect back to the manager page to see the updated data
        response.sendRedirect(request.getContextPath() + "/manager");
    }
/// addStudent() in WEEK09-PART1
///    private void addStudent(HttpServletRequest request, CollegeManager manager) {
///        String name = request.getParameter("studentName");
///        String email = request.getParameter("studentEmail");
///
///        // Validate the input
///        if (name != null && !name.trim().isEmpty() &&
///                email != null && !email.trim().isEmpty()) {
///            manager.addStudent(name, email);
///        }
///    }
    private void addStudent(HttpServletRequest request, CollegeManager manager) {
        String name = request.getParameter("studentName");
        String email = request.getParameter("studentEmail");

        if (name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty()) {
            Student student = manager.addStudent(name, email);

            // Add a success message to the session
            HttpSession session = request.getSession();
            session.setAttribute("message", "Student " + student.getName() + " added successfully!");
        }
    }

    private void addCourse(HttpServletRequest request, CollegeManager manager) {
        String code = request.getParameter("courseCode");
        String title = request.getParameter("courseTitle");
        String creditsStr = request.getParameter("courseCredits");
        String instructor = request.getParameter("courseInstructor");

        // Validate the input
        if (code != null && !code.trim().isEmpty() &&
                title != null && !title.trim().isEmpty() &&
                creditsStr != null && !creditsStr.trim().isEmpty() &&
                instructor != null && !instructor.trim().isEmpty()) {

            try {
                int credits = Integer.parseInt(creditsStr);
                manager.addCourse(code, title, credits, instructor);
            } catch (NumberFormatException e) {
                // Handle invalid credit number
                // In a real app, we'd add an error message here
            }
        }
    }
    // This was not added by CHATGPT to counter the undefined getCollegeManager() in doGet()
    private CollegeManager getCollegeManager(HttpSession session) {
        CollegeManager manager = (CollegeManager) session.getAttribute("collegeManager");
        if (manager == null) {
            manager = new CollegeManager(); // Ensure CollegeManager exists

            Student alice = manager.addStudent("Alice Smith", "alice@example.com");
            Student bob = manager.addStudent("Bob Johnson", "bob@example.com");

            // Add some courses
            Course javaCourse = manager.addCourse("CS101", "Introduction to Java", 3, "Dr. Java");
            Course webDev = manager.addCourse("CS201", "Web Development", 4, "Prof. Web");

            // Register students for courses
            manager.registerStudenForCourse(alice.getId(), javaCourse.getCode());
            manager.registerStudenForCourse(alice.getId(), webDev.getCode());
            manager.registerStudenForCourse(bob.getId(), javaCourse.getCode());

            session.setAttribute("collegeManager", manager);
        }
        return manager;
    }

///   registerStudentForCourse() structure in WEEK09-PART1
///    private void registerStudentForCourse(HttpServletRequest request, CollegeManager manager) {
///        String studentIdStr = request.getParameter("studentId");
///        String courseCode = request.getParameter("courseCode");
///
///        // Validate the input
///        if (studentIdStr != null && !studentIdStr.trim().isEmpty() &&
///                courseCode != null && !courseCode.trim().isEmpty()) {
///
///            try {
///                int studentId = Integer.parseInt(studentIdStr);
///                manager.registerStudenForCourse(studentId, courseCode);
///            } catch (NumberFormatException e) {
///                // Handle invalid student ID
///            }
///        }
///    }
    private void registerStudentForCourse(HttpServletRequest request, CollegeManager manager) {
        String studentIdStr = request.getParameter("studentId");
        String courseCode = request.getParameter("courseCode");
        HttpSession session = request.getSession();

        if (studentIdStr != null && !studentIdStr.trim().isEmpty() &&
                courseCode != null && !courseCode.trim().isEmpty()) {

            try {
                int studentId = Integer.parseInt(studentIdStr);

                // Check if student is already registered for this course
                Student student = manager.findStudentById(studentId);
                Course course = manager.findCourseByCode(courseCode);

                if (student != null && course != null) {
                    if (student.isRegisteredFor(course)) {
                        session.setAttribute("error", "Student " + student.getName() +
                                " is already registered for " + course.getCode() + "!");
                    } else {
                        manager.registerStudenForCourse(studentId, courseCode);
                        session.setAttribute("message", "Student " + student.getName() +
                                " registered for " + course.getCode() + " successfully!");
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid student ID format!");
            }
        }
    }
    private void dropCourse(HttpServletRequest request, CollegeManager manager) {
        String studentIdStr = request.getParameter("studentId");
        String courseCode = request.getParameter("courseCode");
        HttpSession session = request.getSession();

        if (studentIdStr != null && courseCode != null) {
            try {
                int studentId = Integer.parseInt(studentIdStr);
                Student student = manager.findStudentById(studentId);
                Course course = manager.findCourseByCode(courseCode);

                if (student != null && course != null) {
                    if (student.isRegisteredFor(course)) {
                        student.dropCourse(course);
                        session.setAttribute("message",
                                "Student " + student.getName() + " dropped " + course.getCode() + " successfully!");
                    } else {
                        session.setAttribute("error",
                                "Student " + student.getName() + " is not registered for " + course.getCode() + "!");
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid student ID format!");
            }
        }
    }
}
