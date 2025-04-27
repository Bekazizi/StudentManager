package com.example.studentmanager.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentmanager.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("DELETE FROM students")
    void deleteAllStudents();

    @Query("SELECT * FROM students ORDER BY id ASC")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM students WHERE id = :studentId")
    LiveData<Student> getStudentById(int studentId);

    @Query("SELECT * FROM students WHERE name LIKE :searchQuery OR email LIKE :searchQuery")
    LiveData<List<Student>> searchStudents(String searchQuery);

    // StudentDao.java
    @Query("SELECT COUNT(*) FROM students")
    int getTotalStudents();

    @Query("SELECT COUNT(*) FROM students WHERE isActive = 1")
    int getActiveStudents();

    @Query("SELECT COUNT(*) FROM students WHERE isActive = 0")
    int getInactiveStudents();


}