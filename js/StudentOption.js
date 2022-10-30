function openDropCourse() {
    document.getElementById("dropPopup").style.display = "block";
    document.getElementById("addPopup").style.display = "none";
}

function openAddCourse() {
    document.getElementById("addPopup").style.display = "block";
    document.getElementById("dropPopup").style.display = "none";
}

function closeDropCourse() {
    document.getElementById("dropPopup").style.display = "none";
}

function closeAddCourse() {
    document.getElementById("addPopup").style.display = "none";
}

let expanded = false;
function showCheckboxes() {
    const checkboxes = document.getElementById("checkboxes");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}

function limitCheckbox() {
    let checkboxGroup = document.getElementById("checkboxes").getElementsByTagName("input");
    const limit = 5;
    for (let i = 0; i < checkboxGroup.length; i++) {
        checkboxGroup[i].onclick = function () {
            let count = 0;
            for (let i = 0; i < checkboxGroup.length; i++) {
                count += (checkboxGroup[i].checked) ? 1 : 0;
            }
            if (count > limit) {
                alert("You can select a maximum of " + limit + " courses.");
                this.checked = false;
            }
        }
    }

}

function showEnrolledCourses() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("dropList").innerHTML = this.responseText;
        }
    };
    xmlhttp.open("GET", "../php/studentCourses.php", true);
    xmlhttp.send();
}

function showNonEnrolledCourses() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("addList").innerHTML = this.responseText;
        }
    };
    var semesterValue = document.getElementById('semesterChoice').value;
    xmlhttp.open("GET", "../php/nonEnrolledCourseStudent.php?semesterValue=" + semesterValue, true);
    xmlhttp.send();
}
