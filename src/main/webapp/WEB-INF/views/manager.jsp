
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>College Manager</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
            padding: 1rem;
        }
        h1, h2, h3 {
            color: #2c3e50;
            margin-top: 2rem;
        }
        .container {
            display: flex;
            flex-wrap: wrap;
            gap: 2rem;
        }
        .card {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
            border: 1px solid #e9ecef;
            flex: 1;
            min-width: 300px;
        }
        .form-group {
            margin-bottom: 1rem;
        }
        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
        }
        input, select {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 16px;
        }
        button {
            background: #007bff;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background: #0069d9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        th, td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #e9ecef;
        }
        th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
    <h1>🎓 College Manager System</h1>
    <div class="container">
        <!-- Add Course Form -->
        <div class="card">
            <h2>Add New Course 📚</h2>
            <form action="${pageContext.request.contextPath}/manager" method="post">
                <input type="hidden" name="action" value="addCourse">

                <div class="form-group">
                    <label for="courseCodeInput">Course Code:</label>
                    <input type="text" id="courseCodeInput" name="courseCode" required>
                </div>

                <div class="form-group">
                    <label for="courseTitle">Course Title:</label>
                    <input type="text" id="courseTitle" name="courseTitle" required>
                </div>

                <div class="form-group">
                    <label for="courseCredits">Credits:</label>
                    <input type="number" id="courseCredits" name="courseCredits" min="1" max="6" required>
                </div>

                <div class="form-group">
                    <label for="courseInstructor">Instructor:</label>
                    <input type="text" id="courseInstructor" name="courseInstructor" required>
                </div>

                <button type="submit">Add Course</button>
            </form>
        </div>

        <!-- Add Student Form -->
        <div class="card">
            <h2>Add New Student 👨‍🎓</h2>
            <form action="${pageContext.request.contextPath}/manager" method="post">
                <input type="hidden" name="action" value="addStudent">

                <div class="form-group">
                    <label for="studentName">Student Name:</label>
                    <input type="text" id="studentName" name="studentName" required>
                </div>

                <div class="form-group">
                    <label for="studentEmail">Email:</label>
                    <input type="email" id="studentEmail" name="studentEmail" required>
                </div>
                <c:if test="${not empty message}">
                    <div style="background-color: #d4edda; color: #155724; padding: 1rem; border-radius: 4px; margin: 1rem 0;">
                            ${message}
                    </div>
                    <c:remove var="message" scope="session" />
                </c:if>

                <button type="submit">Add Student</button>
            </form>
<%--            example of Flash Messages = remove the message from the session after displaying it so it doesn't show up again on next request--%>
        </div>

        <!-- Register Student for Course Form -->
        <div class="card">
            <h2>Register Student for Course 📝</h2>
            <form action="${pageContext.request.contextPath}/manager" method="post">
                <input type="hidden" name="action" value="registerStudentForCourse">

                <div class="form-group">
                    <label for="studentId">Select Student:</label>
                    <select id="studentId" name="studentId" required>
                        <option value="">-- Select Student --</option>
                        <c:forEach var="student" items="${collegeManager.allStudents}">  <!-- JSTL = <c: > -->
                            <option value="${student.id}">${student.name} (ID: ${student.id})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="courseCodeSelect">Select Course:</label>
                    <select id="courseCodeSelect" name="courseCode" required>
                        <option value="">-- Select Course --</option>
                        <c:forEach var="course" items="${collegeManager.allCourses}">
                            <option value="${course.code}">${course.code}: ${course.title}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:if test="${not empty error}">
                    <div style="background-color: #f8d7da; color: #721c24; padding: 1rem; border-radius: 4px; margin: 1rem 0;">
                            ${error}
                    </div>
                    <c:remove var="error" scope="session" />
                </c:if>
                <button type="submit">Register</button>
            </form>
        </div>
        <!-- Search Student -->
        <div class="card">
            <h2>Search Students 🔍</h2>
            <form action="${pageContext.request.contextPath}/manager" method="get">
                <input type="hidden" name="action" value="searchStudents">

                <div class="form-group">
                    <label for="searchQuery">Search by Name or ID:</label>
                    <input type="text" id="searchQuery" name="q" required>
                </div>

                <button type="submit">Search</button>
            </form>
            <c:if test="${not empty searchResults}">
                <h2>Search Results for "${searchQuery}" 🔍</h2>
                <c:choose>
                    <c:when test="${empty searchResults}">
                        <p>No students found matching your search.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Registered Courses</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="student" items="${searchResults}">
                                <tr>
                                    <td>${student.id}</td>
                                    <td>${student.name}</td>
                                    <td>${student.email}</td>
                                    <td>
                                        <c:forEach var="course" items="${student.registeredCourses}" varStatus="status">
                                            ${course.code}${!status.last ? ', ' : ''}
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
    <!-- Display Students -->
    <h2>Enrolled Students 👨‍🎓👩‍🎓</h2>
    <c:choose>
        <c:when test="${empty collegeManager.allStudents}">
            <p>No students enrolled yet.</p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Registered Courses</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${collegeManager.allStudents}">
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.email}</td>
                        <td>
                         <%--We added a fancy way to display course codes with commas between them! The varStatus="status" gives
                          us access to the loop status, so we can check if we're on the last item! 🧠--%>
                            <c:forEach var="course" items="${student.registeredCourses}" varStatus="status">
                                ${course.code}${!status.last ? ', ' : ''}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <!-- Display Courses -->
    <h2>Available Courses 📚</h2>
    <c:choose>
        <c:when test="${empty collegeManager.allCourses}">
            <p>No courses available yet.</p>
        </c:when>
        <c:otherwise>
<%--        set before table = better readability <c:set var="enrollmentStats" value="${collegeManager.coursesRegistrationStats}" />--%>
            <table>
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Title</th>
                    <th>Credits</th>
                    <th>Instructor</th>
                    <th>Enrollment</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="enrollmentStats" value="${collegeManager.coursesRegistrationStats}" />
                <c:forEach var="course" items="${collegeManager.allCourses}">
                    <tr>
                        <td>${course.code}</td>
                        <td>${course.title}</td>
                        <td>${course.credits}</td>
                        <td>${course.instructor}</td>
                        <td>${enrollmentStats[course.code]} students</td>
                            <%--alternative way to do enrollmentStats[course.code] with count--%><%--VERS1 to count each student in courses
                                <c:set var="count" value="0" />
                                <c:forEach var="student" items="${collegeManager.allStudents}">
                                  <c:if test="${student.isRegisteredFor(course)}">
                                     <c:set var="count" value="${count + 1}" />
                                   </c:if>
                               </c:forEach>
                                    ${count} students--%><%-- CALCULATE STUDENT IN EACH COURSE LOGIC
                                    * Start a counter at 0 using <c:set>
                                    * Loop through all students
                                    * Check if each student is registered for the current course
                                    * Increment the counter for each match
                                    * Display the final count ---%>
                        <td>
                            <a href="${pageContext.request.contextPath}/manager?courseCode=${course.code}">
                                    ${course.code}
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>




    <div class="tip" style="background: #e3f2fd; padding: 1rem; border-radius: 4px; margin: 1rem 0;">
        <strong>Tip:</strong> Use the forms above to add new courses and students, then register students for courses!
    </div>
</body>
</html>

<%--JSP CODE FOR COLLEGE MANAGER PART1 --%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>College Manager</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;--%>
<%--            line-height: 1.6;--%>
<%--            color: #333;--%>
<%--            max-width: 1200px;--%>
<%--            margin: 0 auto;--%>
<%--            padding: 1rem;--%>
<%--        }--%>
<%--        h1, h2, h3 {--%>
<%--            color: #2c3e50;--%>
<%--            margin-top: 2rem;--%>
<%--        }--%>
<%--        table {--%>
<%--            width: 100%;--%>
<%--            border-collapse: collapse;--%>
<%--            margin-top: 1rem;--%>
<%--        }--%>
<%--        th, td {--%>
<%--            padding: 0.75rem;--%>
<%--            text-align: left;--%>
<%--            border-bottom: 1px solid #e9ecef;--%>
<%--        }--%>
<%--        th {--%>
<%--            background-color: #f8f9fa;--%>
<%--        }--%>
<%--        .card {--%>
<%--            background: #f8f9fa;--%>
<%--            border-radius: 8px;--%>
<%--            padding: 1.5rem;--%>
<%--            border: 1px solid #e9ecef;--%>
<%--            margin-bottom: 1rem;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>🎓 College Manager System</h1>--%>

<%--<div class="card">--%>
<%--    <h2>System Information</h2>--%>
<%--    <p>Total Students: ${collegeManager.allStudents.size()}</p>--%>
<%--    <p>Total Courses: ${collegeManager.allCourses.size()}</p>--%>
<%--</div>--%>

<%--<!-- Display Students -->--%>
<%--<h2>Enrolled Students 👨‍🎓👩‍🎓</h2>--%>
<%--<c:choose>--%>
<%--    <c:when test="${empty collegeManager.allStudents}">--%>
<%--        <p>No students enrolled yet.</p>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>ID</th>--%>
<%--                <th>Name</th>--%>
<%--                <th>Email</th>--%>
<%--                <th>Registered Courses</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:forEach var="student" items="${collegeManager.allStudents}">--%>
<%--                <tr>--%>
<%--                    <td>${student.id}</td>--%>
<%--                    <td>${student.name}</td>--%>
<%--                    <td>${student.email}</td>--%>
<%--                    <td>--%>
<%--                        <c:forEach var="course" items="${student.registeredCourses}" varStatus="status">--%>
<%--                            ${course.code}${!status.last ? ', ' : ''}--%>
<%--                        </c:forEach>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>

<%--<!-- Display Courses -->--%>
<%--<h2>Available Courses 📚</h2>--%>
<%--<c:choose>--%>
<%--    <c:when test="${empty collegeManager.allCourses}">--%>
<%--        <p>No courses available yet.</p>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>Code</th>--%>
<%--                <th>Title</th>--%>
<%--                <th>Credits</th>--%>
<%--                <th>Instructor</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:forEach var="course" items="${collegeManager.allCourses}">--%>
<%--                <tr>--%>
<%--                    <td>${course.code}</td>--%>
<%--                    <td>${course.title}</td>--%>
<%--                    <td>${course.credits}</td>--%>
<%--                    <td>${course.instructor}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<%--</body>--%>
<%--</html>--%>
