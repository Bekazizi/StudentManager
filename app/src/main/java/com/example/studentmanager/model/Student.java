package com.example.studentmanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studentmanager.utils.Converters;

import java.util.Date;
import java.util.List;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String registrationDate;
    private boolean isActive;

    @TypeConverters(Converters.class)
    private Date birthDate;

    @TypeConverters(Converters.class)
    private List<String> hobbies;

    // Full constructor (birthDate va hobbies ham kiritiladi)
    public Student(String name, String email, String phone, String registrationDate, boolean isActive, Date birthDate, List<String> hobbies) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.birthDate = birthDate;
        this.hobbies = hobbies;
    }

    // Empty constructor (Room uchun kerak bo'lishi mumkin)
    public Student() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
