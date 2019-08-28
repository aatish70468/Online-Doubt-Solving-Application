package com.example.aatish.onlinedoubt;

public class User {
    public String name;
    public String enrollment;
    public String email;
    public String pass;
    public String cnfpass;
    public String enrollment2;
    public String subject;
    public String subject_name;
    public String question;

    public User(){}

    //Add User
    public User(String name,String enrollment, String email, String pass, String cnfpass){
        this.name = name;
        this.enrollment = enrollment;
        this.email = email;
        this.pass = pass;
        this.cnfpass = cnfpass;
    }

    //Add Subject
    public  User(String subject){
        this.subject = subject;
    }

//    public User(String enrollment2)
//    {
//        this.enrollment2 = enrollment2;
//    }

    public String getName(){
        return name;
    }

    public String getEnrollment(){
        return enrollment;
    }

    public String getEmail(){
        return email;
    }

    public String getPass(){
        return pass;
    }

    public String getEnrollment2() { return enrollment2; }

    public String getSubject() { return subject; }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
