package com.example.lab5_1;

public class Student {
    String Username;
    String Fullname;
    String Email;

    public Student(String username, String fullname, String email) {
        this.Username = username;
        this.Fullname = fullname;
        this.Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }
}
