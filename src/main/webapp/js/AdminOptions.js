function openCourseForm(){
    document.getElementById("coursePopup").style.display = "block";
    document.getElementById("studentPopup").style.display = "none";
    document.getElementById("newPopup").style.display = "none";
}

function openStudentForm(){
    document.getElementById("studentPopup").style.display = "block";
    document.getElementById("coursePopup").style.display = "none";
    document.getElementById("newPopup").style.display = "none";
}

function newCourseForm(){
    document.getElementById("newPopup").style.display = "block";
    document.getElementById("coursePopup").style.display = "none";
    document.getElementById("studentPopup").style.display = "none";
}

function closeCourseForm(){
    document.getElementById("coursePopup").style.display = "none";
}

function closeStudentForm(){
    document.getElementById("studentPopup").style.display = "none";
}

function closeNewForm(){
    document.getElementById("newPopup").style.display = "none";
}

$(document).ready(function() {
    $("#courseReport").click(function() {
        $.get('AdminAjax', {CourseReport: true}, function(data) {
            $('#courseCode').html(data);
        });
    });
    $("#studentReport").click(function() {
        $.get('AdminAjax', {CourseReport: false}, function (data) {
            $('#studentName').html(data);
        });
    });
});

