package com.example.studentmanager.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    public abstract StudentDao studentDao();
    public abstract SubjectDao subjectDao();
    public abstract StudentSubjectDao studentSubjectDao();

    // Background thread uchun ExecutorService
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .addCallback(roomCallback) // Ma'lumot qo‘shish uchun
                            .fallbackToDestructiveMigration() // Versiya o‘zgarsa tozalab yangilash
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Birinchi ochilganda ma'lumot qo‘shish uchun callback
    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Student va Subject qo‘shish
                StudentDao studentDao = INSTANCE.studentDao();
                SubjectDao subjectDao = INSTANCE.subjectDao();

                studentDao.insert(new Student("Ali", "Karimov"));
                studentDao.insert(new Student("Laylo", "Rasulova"));

                subjectDao.insert(new Subject("Matematika"));
                subjectDao.insert(new Subject("Fizika"));
            });
        }
    };
}
