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

    // Talabani qo'shish
    @Insert
    void insert(Student student);

    // Talabani yangilash
    @Update
    void update(Student student);

    // Talabani o'chirish
    @Delete
    void delete(Student student);

    // Barcha talabalarni o'chirish
    @Query("DELETE FROM students")
    void deleteAll();

    // Barcha talabalarni ID bo‘yicha tartiblab olish
    @Query("SELECT * FROM students ORDER BY id ASC")
    LiveData<List<Student>> getAllStudents();

    // Id bo‘yicha bitta talabani olish
    @Query("SELECT * FROM students WHERE id = :studentId")
    LiveData<Student> getStudentById(int studentId);

    // Talabalarni ism yoki email bo‘yicha qidirish
    @Query("SELECT * FROM students WHERE name LIKE '%' || :searchQuery || '%' OR email LIKE '%' || :searchQuery || '%'")
    LiveData<List<Student>> searchStudents(String searchQuery);

    // Umumiy talabalar soni
    @Query("SELECT COUNT(*) FROM students")
    LiveData<Integer> getTotalStudents();

    // Aktiv talabalar soni
    @Query("SELECT COUNT(*) FROM students WHERE isActive = 1")
    LiveData<Integer> getActiveStudents();

    // Noaktiv talabalar soni
    @Query("SELECT COUNT(*) FROM students WHERE isActive = 0")
    LiveData<Integer> getInactiveStudents();
}
