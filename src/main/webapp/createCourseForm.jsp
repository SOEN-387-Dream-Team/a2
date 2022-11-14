<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<div align="center">
    <h1>Employee Register Form</h1>

    <form action="<%= request.getContextPath() %>/create"  method="post">
        <fieldset>
            <h2>Course Creation</h2>
            <hr style="width: 100%; text-align: left; margin-left: 0">
            <p>Enter the information for a new Course.</p>
            <hr style="width: 50%; text-align: left; margin-left: 0">
            <ul>
                <li>
                    <label for="courseCode">Course Code</label><br>
                    <input type="text" name="courseCode" placeholder="Enter a valid Course Code" id="courseCode" maxlength="7" size="7" required onfocusout="validateNewCode()">
                </li>
                <li>
                    <label for="title">Course Title</label><br>
                    <input type="text" name="title" placeholder="Enter a valid Course Title" id="title" size="30" required onfocusout="validateTitle()">
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
                    <label for="time">Course Time</label><br>
                    <input type="time" name="time" id="time" min="08:00" max="21:00" required>
                </li>
                <li>
                    <label for="instructor">Course Instructor</label><br>
                    <input type="text" name="instructor" placeholder="Enter a valid Course Instructor" size="50" id="instructor" required onfocusout="validateInstructor()">
                </li>
                <li>
                    <label for="room">Course Room</label><br>
                    <input type="text" name="room" placeholder="Enter a valid Course Room" maxlength="4" size="20" id="room" required onfocusout="validateRoom()">
                </li>
            </ul>

            <hr style="width: 50%; text-align: left; margin-left: 0">
            <button type="submit" class="form-control-sm" name="create_course_btn" value="Submit">Submit</button>
            <button type="button" class="form-control-sm" onclick="closeNewForm()">Cancel</button>
        </fieldset>
    </form>
</div>
</body>
</html>