<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.logging.Logger" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Administrator Options</title>
    <link rel="stylesheet" href="css/AdminPageStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript" src="js/AdminOptions.js"></script>
    <script type="text/javascript" src="js/Validation.js"></script>
</head>
<%@ include file="navbar.jsp" %>
<!--Navigation bar with bootstrap header -->
<%
    System.out.println("User ID is: ");
    System.out.println(session.getAttribute("id"));
    if (session.getAttribute("id") == null || session.getAttribute("isAdmin").equals("false"))
    {
        session.invalidate();
        response.sendRedirect("MainPage.jsp");
    }
%>
<body>

<!--main page-->
<div class="box">
    <div class="inner">
        <h1>Administrator Information</h1>
        <hr style="width: 100%; text-align: left; margin-left: 0">
        <p>Welcome!</p>
        <hr style="width: 50%; text-align: left; margin-left: 0">
        <!--main buttons-->
        <div class="button-group">
            <span class="inline">
                <form id="courseReport" onclick="openCourseForm()" style="width:100%">
                    <input type="button" value="Course Report"/>
                </form>
            </span>
            <span class="inline">
                <form id="studentReport" onclick="openStudentForm()" style="width:100%">
                    <input type="button" value="Student Report"/>
                </form>
            </span>
            <span class="inline">
                <form id="newCourse" onclick="newCourseForm()" style="width:100%">
                    <input type="button" value="Create New Course"/>
                </form>
            </span>
        </div>
    </div>

<!--Course Report Popup-->
    <div class="form-popup" id="coursePopup">
        <form action="<%= request.getContextPath() %>/submitCourseReport" onsubmit="return validateCode()" method="post">
            <fieldset>
                <h2>Course Report</h2>
                <hr style="width: 100%; text-align: left; margin-left: 0">
                <p>Enter a Course Code to view all Students registered to this course.</p>
                <hr style="width: 50%; text-align: left; margin-left: 0">
                <ul>
                    <li>
                        <label for="courseCode">Course Code</label>
                        <select id="courseCode" name="courseCode"> </select>
                    </li>
                </ul>
            </fieldset>

            <hr style="width: 50%; text-align: left; margin-left: 0">
            <button name="courseReport" class="form-control-sm" type="submit" value="Submit">Submit</button>
            <button type="button" class="form-control-sm cancel" onclick="closeCourseForm()">Cancel</button>
        </form>
    </div>

<!--Student Report Popup-->
    <div class="form-popup" id="studentPopup">
        <form action="<%= request.getContextPath() %>/submitStudentReport" onsubmit="return validateStudent()" method="post">
            <fieldset>
                <h2>Student's Course Report</h2>
                <hr style="width: 100%; text-align: left; margin-left: 0">
                <p>Enter a Student's name (First & Last Name) to view all Courses the Student is registered in.</p>
                <hr style="width: 50%; text-align: left; margin-left: 0">
                <ul>
                    <li>
                        <label for="studentName">Student's Full Name</label>
                        <select id="studentName" name="studentName"></select>
                    </li>
                </ul>
            </fieldset>

            <hr style="width: 50%; text-align: left; margin-left: 0">
            <button name ="studentReport" class="form-control-sm" type="submit" value="Submit">Submit</button>
            <button type="button"  class="form-control-sm cancel" onclick="closeStudentForm()">Cancel</button>
        </form>
    </div>

<!--Create New Course -->
    <div class="form-popup" id="newPopup">
        <form action="<%= request.getContextPath() %>/create"  method="post">
            <fieldset>
                <h2>Course Creation</h2>
                <hr style="width: 100%; text-align: left; margin-left: 0">
                <p>Enter the information for a new Course.</p>
                <hr style="width: 50%; text-align: left; margin-left: 0">
                <ul>
                    <li>
                        <label for="newCode">Course Code</label><br>
                        <input type="text" name="courseCode" placeholder="Enter a valid Course Code" id="newCode" maxlength="7" size="7" required onfocusout="validateNewCode()">
                    </li>
                    <li>
                        <label for="newTitle">Course Title</label><br>
                        <input type="text" name="title" placeholder="Enter a valid Course Title" id="newTitle" size="30" required onfocusout="validateTitle()">
                    </li>
                    <li>
                        <label>Course Semester</label><br>
                            <label for="fall">
                                <input type="radio" name="semester" value="FALL-2022" id="fall">Fall
                            </label><br>
                            <label for="winter">
                                <input type="radio" name="semester" value="WINTER-2023" id="winter">Winter
                            </label><br>
                            <label for="summer1">
                                <input type="radio" name="semester" value="SUMMER1-2023" id="summer1">Summer 1
                            </label><br>
                            <label for="summer2">
                                <input type="radio" name="semester" value="SUMMER2-2023" id="summer2">Summer 2
                            </label>
                    </li>
                    <li>
                        <label>Course Day</label><br>
                            <label for="monday">
                                <input type="radio" name="days" value="Monday" id="monday" required>Monday
                            </label><br>
                            <label for="tuesday">
                                <input type="radio" name="days" value="Tuesday" id="tuesday">Tuesday
                            </label><br>
                            <label for="wednesday">
                                <input type="radio" name="days" value="Wednesday" id="wednesday">Wednesday
                            </label><br>
                            <label for="thursday">
                                <input type="radio" name="days" value="Thursday" id="thursday">Thursday
                            </label><br>
                            <label for="friday">
                                <input type="radio" name="days" value="Friday" id="friday">Friday
                            </label>
                    </li>
                    <li>
                        <label for="newTime">Course Time</label><br>
                        <input type="time" name="time" id="newTime" min="08:00" max="21:00" required>
                    </li>
                    <li>
                        <label for="newInstructor">Course Instructor</label><br>
                        <input type="text" name="instructor" placeholder="Enter a valid Course Instructor" size="50" id="newInstructor" required onfocusout="validateInstructor()">
                    </li>
                    <li>
                        <label for="newRoom">Course Room</label><br>
                        <input type="text" name="room" placeholder="Enter a valid Course Room" maxlength="4" size="20" id="newRoom" required onfocusout="validateRoom()">
                    </li>
                </ul>

                <hr style="width: 50%; text-align: left; margin-left: 0">
                <button type="submit" class="form-control-sm" name="create_course_btn" value="Submit">Submit</button>
                <button type="button" class="form-control-sm" onclick="closeNewForm()">Cancel</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
