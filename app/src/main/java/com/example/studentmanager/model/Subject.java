package com.example.studentmanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String teacher;
    private String name;
    private String subjectName;
    private String subjectCode;
    private int creditHours;

    // Constructor
    public Subject(String subjectName, String subjectCode, int creditHours, String teacher) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.creditHours = creditHours;
        this.teacher = teacher;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
}