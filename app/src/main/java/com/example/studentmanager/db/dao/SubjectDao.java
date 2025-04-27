package com.example.studentmanager.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentmanager.model.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insertSubject(Subject subject);

    @Update
    void updateSubject(Subject subject);

    @Delete
    void deleteSubject(Subject subject);

    @Query("DELETE FROM subjects")
    void deleteAllSubjects();

    @Query("SELECT * FROM subjects ORDER BY subjectName ASC")
    LiveData<List<Subject>> getAllSubjects();

    @Query("SELECT * FROM subjects WHERE id = :subjectId")
    LiveData<Subject> getSubjectById(int subjectId);

    @Query("SELECT * FROM subjects WHERE subjectName LIKE :searchQuery")
    LiveData<List<Subject>> searchSubjects(String searchQuery);

    @Query("SELECT COUNT(*) FROM subjects")
    LiveData<Integer> getSubjectsCount();

    @Query("SELECT COUNT(*) FROM subjects")
    int getTotalSubjects();

    @Query("SELECT COUNT(*) FROM student_subject WHERE subjectId = :subjectId")
    int getEnrollmentCount(int subjectId);
}