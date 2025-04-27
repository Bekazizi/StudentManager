package com.example.studentmanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "student_subject",
        primaryKeys = {"studentId", "subjectId"},
        foreignKeys = {
                @ForeignKey(entity = Student.class,
                        parentColumns = "id",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Subject.class,
                        parentColumns = "id",
                        childColumns = "subjectId",
                        onDelete = ForeignKey.CASCADE)
        })
public class StudentSubject {
    private int grade;


    private int studentId;
    private int subjectId;

    // Constructor
    public StudentSubject(int studentId, int subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    // Getters and Setters
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {  // <- Xatoni to'g'rilash uchun qo'shildi
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }
}