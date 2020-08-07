package com.example.t23_pm2020;

public class User {
    public String name;
    public String email;
    public int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isManager() {
        return IsManager;
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public boolean IsManager;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, int points) {
        this.name = username;
        this.email = email;
        this.points = points;
        this.IsManager = false;
    }

}
