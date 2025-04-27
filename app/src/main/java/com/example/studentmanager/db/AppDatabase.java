package com.example.studentmanager.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.studentmanager.db.dao.StudentDao;
import com.example.studentmanager.db.dao.StudentSubjectDao;
import com.example.studentmanager.db.dao.SubjectDao;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.StudentSubject;
import com.example.studentmanager.model.Subject;
import com.example.studentmanager.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Student.class, Subject.class, StudentSubject.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "student_db";

    // ExecutorService for database operations
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public abstract StudentDao studentDao();
    public abstract SubjectDao subjectDao();
    public abstract StudentSubjectDao studentSubjectDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}