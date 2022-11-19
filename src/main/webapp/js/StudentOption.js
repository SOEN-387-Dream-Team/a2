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

$(document).ready(function() {
    $("#semesterChoice").change(function() {
        const semester = $("#semesterChoice").val();
        $.get('StudentAjax', {addReport: true, chosenSemester : semester}, function(data) {
            $('#addList').html(data);
        });
    });
    $("#dropReport").click(function() {
        $.get('StudentAjax', {addReport: false}, function (data) {
            $('#dropList').html(data);
        });
    });
});
