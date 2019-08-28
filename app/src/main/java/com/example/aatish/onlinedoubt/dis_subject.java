package com.example.aatish.onlinedoubt;

public class dis_subject {

    private String id;
    private String subject;

    public dis_subject() {
    }

    public dis_subject(String id, String subject){
        this.id = id;
        this.subject = subject;
    }

    public dis_subject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
