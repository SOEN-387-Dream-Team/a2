<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Options</title>
    <style>
        #checkboxes label {
            display: block;
        }
        #checkboxes label:hover {
            background-color: #1e90ff;
        }
    </style>
    <link rel="stylesheet" href="../css/StudentPageStyle.css">
    <script type="text/javascript" src="../js/StudentOption.js"></script>
    <script type="text/javascript" src="../js/Validation.js"></script>
</head>
<?php
if(!isset($_SESSION)) {
  session_start();
}
include '../php/header.php';
include '../php/navbar.php';
if ($_SESSION['user']['isAdmin'] === 1 or $_SESSION['loggedIn'] === false) {
  header("Location: ../php/error.php");
}
?>

<body>
<div class="box">
    <div class="inner">
        <h1>Student Page</h1>
        <hr style="width: 100%; text-align: left; margin-left: 0">
        <p>Welcome!</p>
        <div>
            <p>Add or Drop courses from a chosen semester.</p>
            <hr style="width: 80%; text-align: left; margin-left: 0">
            <div class="button-group">
                        <span class="inline">
                            <form id="addReport" onclick="openAddCourse();" style="width:100%">
                                <input type="button" value="Add Courses"/>
                            </form>
                        </span>
                <span class="inline">
                            <form id="dropReport" onclick="openDropCourse();showEnrolledCourses()" style="width:100%">
                                <input type="button" value="Drop Courses"/>
                            </form>
                        </span>
            </div>
        </div>
            <!--</form>-->
        </div>
    <!--drop course popup-->
    <div class="form-popup" id="dropPopup">
        <form action="../php/dropCourse.php" class="form-container" onsubmit="return validateDropCode()" method="post">
            <h2>Drop a Class</h2>
            <p>Choose an enrolled course to drop.</p>
            <label for="dropList">List of Enrolled classes</label>
            <select id="dropList" name="dropCode">--list of enrolled courses--</select>

            <p></p>
            <button name = "drop_course_btn" class="form-control-sm" type="submit" value="Submit">Submit</button>
            <button type="button" class="form-control-sm cancel" onclick="closeDropCourse()">Cancel</button>
        </form>
    </div>
    <!--add course popup-->
    <div class="form-popup" id="addPopup">
        <form action="../php/registerCourse.php" class="form-container" onsubmit="return validateAddCode()" method="post">
            <h2>Add a Class</h2>
            <p>Select a semester & then choose a course to enroll to.</p>
            <p>A maximum of 5 courses can be enrolled at the same time in the same semester.</p>

            <label for="semesterChoice">Select a Semester</label>
            <select name= "semesterChoice" id = "semesterChoice" onchange="showNonEnrolledCourses()">
                <option disabled selected value> -- select a semester -- </option>
                <option value="FALL-2022">Fall 2022</option>
                <option value="WINTER-2023">Winter 2023</option>
                <option value="SUMMER1-2023">Summer 1 2023</option>
                <option value="SUMMER2-2023">Summer 2 2023</option>
            </select>

            <p></p>

            <label for="addList">List of Un-enrolled classes</label>
            <select id="addList" name="courseCode">--list of un-enrolled courses--</select>

            <p></p>
            <button name = "addCourse" class="form-control-sm" type="submit" value="Submit">Submit</button>
            <button type="button" class="form-control-sm cancel" onclick="closeAddCourse()">Cancel</button>
        </form>
    </div>
</div>
</body>
</html>
