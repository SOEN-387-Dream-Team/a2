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

function showCourses() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("courseCode").innerHTML = this.responseText;
      }
    };
    xmlhttp.open("GET","../php/allCourses.php",true);
    xmlhttp.send();
}

function showStudents() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("studentName").innerHTML = this.responseText;
      }
    };
    xmlhttp.open("GET","../php/allStudents.php",true);
    xmlhttp.send();
}
