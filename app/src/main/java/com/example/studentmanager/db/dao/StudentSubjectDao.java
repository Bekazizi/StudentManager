package com.example.studentmanager.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.studentmanager.model.StudentSubject;

import java.util.List;

@Dao
public interface StudentSubjectDao {

    @Insert
    void insertStudentSubject(StudentSubject studentSubject);

    @Delete
    void deleteStudentSubject(StudentSubject studentSubject);

    @Query("DELETE FROM student_subject WHERE studentId = :studentId AND subjectId = :subjectId")
    void deleteStudentSubjectByIds(int studentId, int subjectId);

    @Query("SELECT * FROM student_subject")
    LiveData<List<StudentSubject>> getAllStudentSubjects();

    @Query("SELECT subjectId FROM student_subject WHERE studentId = :studentId")
    LiveData<List<Integer>> getSubjectIdsForStudent(int studentId);

    @Query("SELECT studentId FROM student_subject WHERE subjectId = :subjectId")
    LiveData<List<Integer>> getStudentIdsForSubject(int subjectId);

    @Transaction
    @Query("DELETE FROM student_subject WHERE studentId = :studentId")
    void deleteAllSubjectsForStudent(int studentId);

    @Transaction
    @Query("DELETE FROM student_subject WHERE subjectId = :subjectId")
    void deleteAllStudentsForSubject(int subjectId);

    @Query("SELECT COUNT(*) FROM student_subject WHERE studentId = :studentId AND subjectId = :subjectId")
    LiveData<Integer> checkStudentSubjectRelation(int studentId, int subjectId);

    @Query("SELECT COUNT(*) FROM student_subject WHERE studentId = :studentId")
    LiveData<Integer> getSubjectCountForStudent(int studentId);

    @Query("SELECT COUNT(*) FROM student_subject WHERE subjectId = :subjectId")
    LiveData<Integer> getStudentCountForSubject(int subjectId);
}
