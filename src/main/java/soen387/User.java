package soen387;
import java.util.*;



public class User
{
    int ID;
    String firstName;
    String lastName;
    String address;
    String email;
    String phoneNum;
    String dateOB;
    Hashtable<String, Course> courses;
    String password;
    Boolean isAdmin;
    int count = 0;

    public User()
    {
        this.ID = count++;
        this.firstName = "default";
        this.firstName = "default";
        this.lastName = "default";
        this.address = "default";
        this.email = "default";
        this.phoneNum = "default";
        this.dateOB = "default";
        this.courses = new Hashtable<String, Course>();
        this.password = "default";
        this.isAdmin = false;

    }

    public User(String fname, String lnam, String addr, String email, String num, String dob, String pass, Boolean admin)
    {
        this.ID = count++;
        this.firstName = fname;
        this.firstName = fname;
        this.lastName = lnam;
        this.address = addr;
        this.email = email;
        this.phoneNum = num;
        this.dateOB = dob;
        this.courses = new Hashtable<String, Course>(5);
        this.password = pass;
        this.isAdmin = admin;

    }


    //Need the name of the course and the course object
    public void addCourse(String courseName, Course courseObj)
    {
        if (courses.size() == 5)
        {
            System.out.println("Cannot enroll in more than 5 courses at a time");
        }
        else
        {
            this.courses.put(courseName, courseObj);
            //**Have to update DATABASE and need to validate**//
        }
    }

    //Need the name of the course
    public void dropCourse(String courseName, Course courseObj)
    {
        if ((courses.size() == 0) || (courses.get(courseName) == null) )
        {
            System.out.println("You are not enrolled in this course");
        }
        else
        {
            courses.remove(courseName);
            //**Have to update DATABASE and need to validate**//
        }
    }

    public void courseReport()
    {
        //generates course report
    }

    public void studentReport()
    {
        //generates student report
    }

    public void createCourse(String courseCode, String title, String semester, String room, String startDate, String endDate, String days, String time,  String instructor)
    {
        //Adds course to database
    }

    //Accessors ----------------------------------------------------------------*
    public int getID()
    {
        return this.ID;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public String getAddress()
    {
        return this.address;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getPhoneNum()
    {
        return this.phoneNum;
    }
    public String getDOB()
    {
        return this.dateOB;
    }

    public String getPass()
    {
        return this.password;
    }

    public Boolean getAdminStatus()
    {
        return this.isAdmin;
    }

    //Mutators  ----------------------------------------------------------------*
    public void setID(int id)
    {
        this.ID = id;
    }
    public void setFirstName(String first)
    {
        this.firstName = first;
    }
    public void setLastName(String last)
    {
        this.lastName = last;
    }
    public void setAddress(String add)
    {
        this.address = add;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPhoneNum(String phone)
    {
        this.phoneNum = phone;
    }
    public void setDOB(String dob)
    {
        this.dateOB = dob;
    }
    public void setPass(String pass)
    {
        this.password = pass;
    }
    public void setAdmin(Boolean admin)
    {
        this.isAdmin = admin;
    }

    public String toString()
    {
        return "ID: " + this.getID() + "\nFirst Name: " + this.getFirstName() + "\nLast Name: " + this.getLastName() + "\nAddress: "
                + this.getAddress() + "\nEmail: " + this.getEmail() + "\nPhone Number: " + this.getPhoneNum() + "\nDate of Birth: "
                + getDOB();

    }
}
