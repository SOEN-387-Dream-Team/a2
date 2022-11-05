package soen387;

public class Course 
{
	String courseCode;
	String title;
	String semester; 
	String room; 
	String startDate;
	String endDate;
	String days; 
	String time; 
	String instructor; 
	
	
	Course()
	{
		this.courseCode = "default";
		this.title = "default";
		this.semester = "default"; 
		this.room = "default"; 
		this.startDate = "default";
		this.endDate = "default";
		this.days = "default"; 
		this.time = "default"; 
		this.instructor = "default"; 
		
	}
	
	Course(String code, String title, String semester, String room, String start, String end, String day, String time, String instructor)
	{
		this.courseCode = code;
		this.title = title;
		this.semester = semester; 
		this.room = room; 
		this.startDate = start;
		this.endDate = end;
		this.days = day; 
		this.time = time; 
		this.instructor = instructor; 
		
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
}
