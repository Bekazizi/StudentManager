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

    // Fan qo'shish
    @Insert
    void insert(Subject subject);

    // Fan yangilash
    @Update
    void update(Subject subject);

    // Fan o'chirish
    @Delete
    void delete(Subject subject);

    // Barcha fanlarni o'chirish
    @Query("DELETE FROM subjects")
    void deleteAll();

    // Barcha fanlarni nomi bo'yicha tartiblab olish
    @Query("SELECT * FROM subjects ORDER BY subjectName ASC")
    LiveData<List<Subject>> getAllSubjects();

    // Id bo'yicha fan olish
    @Query("SELECT * FROM subjects WHERE id = :subjectId")
    LiveData<Subject> getSubjectById(int subjectId);

    // Fanlarni nomi bo'yicha qidirish
    @Query("SELECT * FROM subjects WHERE subjectName LIKE '%' || :searchQuery || '%'")
    LiveData<List<Subject>> searchSubjects(String searchQuery);

    // Fanlar soni (LiveData ko'rinishida)
    @Query("SELECT COUNT(*) FROM subjects")
    LiveData<Integer> getSubjectsCount();

    // Fanlar soni (oddiy int ko'rinishida, fon amallar uchun)
    @Query("SELECT COUNT(*) FROM subjects")
    int getTotalSubjects();

    // Bitta fandagi o'quvchilar soni
    @Query("SELECT COUNT(*) FROM student_subject WHERE subjectId = :subjectId")
    int getEnrollmentCount(int subjectId);
}
