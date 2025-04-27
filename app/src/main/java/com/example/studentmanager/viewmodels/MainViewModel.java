package com.example.studentmanager.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.studentmanager.R;
import com.example.studentmanager.db.AppDatabase;
import com.example.studentmanager.db.dao.StudentDao;
import com.example.studentmanager.db.dao.SubjectDao;
import com.example.studentmanager.db.dao.StudentSubjectDao;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.StudentSubject;
import com.example.studentmanager.model.Subject;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // LiveData obyektlari
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private StudentDao studentDao;
    private SubjectDao subjectDao;
    private StudentSubjectDao studentSubjectDao;

    private LiveData<List<Student>> allStudents;
    private LiveData<List<Subject>> allSubjects;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        studentDao = db.studentDao();
        subjectDao = db.subjectDao();
        studentSubjectDao = db.studentSubjectDao();

        allStudents = studentDao.getAllStudents();
        allSubjects = subjectDao.getAllSubjects();
    }

    public interface OperationCallback {
        void onResult(boolean success);
    }

    public void showAddSubjectDialog(Context context, OperationCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add New Subject")
                .setView(R.layout.dialog_add_subject)  // dialog_add_subject.xml faylini yaratishingiz kerak
                .setPositiveButton("Save", (dialog, which) -> {
                    callback.onResult(true);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    callback.onResult(false);
                })
                .show();
    }

    // Student operations
    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void showAddStudentDialog(Context context, OperationCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Student")
                .setView(R.layout.dialog_add_student) // O'zingizning layout faylingiz
                .setPositiveButton("Save", (dialog, which) -> {
                    callback.onResult(true);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    callback.onResult(false);
                })
                .show();
    }

    public void insertStudent(Student student) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            studentDao.insertStudent(student);
        });
    }

    // Subject operations
    public LiveData<List<Subject>> getAllSubjects() {
        return allSubjects;
    }

    public void insertSubject(Subject subject) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            subjectDao.insertSubject(subject);
        });
    }

    // Student-Subject relationship operations
    public void enrollStudentToSubject(int studentId, int subjectId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            studentSubjectDao.insertStudentSubject(new StudentSubject(studentId, subjectId));
        });
    }
    // Loading holati uchun metodlar
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    // Xabar uchun metodlar
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void showToast(String message) {
        toastMessage.setValue(message);
    }

    public void onToastShown() {
        toastMessage.setValue(null);
    }

    public void showSearchDialog(Context context) {
        // Search dialogini ko'rsatish logikasi
        new AlertDialog.Builder(context)
                .setTitle("Search")
                .setView(R.layout.dialog_search)  // dialog_search.xml faylini yaratishingiz kerak
                .setPositiveButton("Search", (dialog, which) -> {
                    // Qidiruv logikasi
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

//    public void showStatistics(Context context) {
//        // Statistika ko'rsatish logikasi
//        Intent intent = new Intent(context, StatisticsActivity.class);  // StatisticsActivity yaratishingiz kerak
//        context.startActivity(intent);
//    }
//
//    public void openSettings(Context context) {
//        // Sozlamalarni ochish logikasi
//        Intent intent = new Intent(context, SettingsActivity.class);  // SettingsActivity yaratishingiz kerak
//        context.startActivity(intent);
//    }
}