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
        .card {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
            border: 1px solid #e9ecef;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<h1>🎓 College Manager System</h1>

<div class="card">
    <h2>System Information</h2>
    <p>Total Students: ${collegeManager.allStudents.size()}</p>
    <p>Total Courses: ${collegeManager.allCourses.size()}</p>
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
        <table>
            <thead>
            <tr>
                <th>Code</th>
                <th>Title</th>
                <th>Credits</th>
                <th>Instructor</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="course" items="${collegeManager.allCourses}">
                <tr>
                    <td>${course.code}</td>
                    <td>${course.title}</td>
                    <td>${course.credits}</td>
                    <td>${course.instructor}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>