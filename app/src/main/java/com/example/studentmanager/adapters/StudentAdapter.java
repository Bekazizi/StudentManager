package com.example.studentmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentmanager.R;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.StudentSubject;
import com.example.studentmanager.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAdapter extends ListAdapter<Student, StudentAdapter.StudentViewHolder> {

    private List<Subject> subjects;
    private List<StudentSubject> studentSubjects;
    private OnStudentClickListener listener;
    private Map<Integer, Subject> subjectMap = new HashMap<>();
    private Map<Integer, List<StudentSubject>> studentSubjectsMap = new HashMap<>();

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
        void onSubjectClick(Subject subject);
        void onGradeClick(StudentSubject studentSubject);
    }

    public StudentAdapter(List<Subject> subjects,
                          List<StudentSubject> studentSubjects,
                          OnStudentClickListener listener) {
        super(DIFF_CALLBACK);
        this.subjects = subjects;
        this.studentSubjects = studentSubjects;
        this.listener = listener;
        prepareMaps();
    }

    private void prepareMaps() {
        // Create subject map for quick lookup
        for (Subject subject : subjects) {
            subjectMap.put(subject.getId(), subject);
        }

        // Create student-subjects map
        for (StudentSubject ss : studentSubjects) {
            studentSubjectsMap.computeIfAbsent(ss.getStudentId(), k -> new ArrayList<>()).add(ss);
        }
    }

    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = getItem(position);
        holder.bind(currentStudent);
    }

    public void updateData(List<Student> newStudents,
                           List<Subject> newSubjects,
                           List<StudentSubject> newStudentSubjects) {
        this.subjects = newSubjects;
        this.studentSubjects = newStudentSubjects;
        prepareMaps();
        submitList(newStudents);
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView subjectsTextView;
        private final TextView averageGradeTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            subjectsTextView = itemView.findViewById(R.id.textViewSubjects);
            averageGradeTextView = itemView.findViewById(R.id.textViewAverageGrade);
        }

        public void bind(Student student) {
            nameTextView.setText(student.getName());

            List<StudentSubject> studentSubs = studentSubjectsMap.get(student.getId());
            if (studentSubs == null || studentSubs.isEmpty()) {
                subjectsTextView.setText("No subjects assigned");
                averageGradeTextView.setText("Average: -");
                return;
            }

            StringBuilder subjectsText = new StringBuilder();
            float totalGrade = 0;
            int count = 0;

            for (StudentSubject ss : studentSubs) {
                Subject subject = subjectMap.get(ss.getSubjectId());
                if (subject != null) {
                    subjectsText.append(subject.getName())
                            .append(": ")
                            .append(ss.getGrade())
                            .append("\n");

                    totalGrade += ss.getGrade();
                    count++;
                }
            }

            subjectsTextView.setText(subjectsText.toString());
            averageGradeTextView.setText(count > 0 ?
                    String.format("Average: %.1f", totalGrade / count) : "Average: -");

            // Click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onStudentClick(student);
            });

            subjectsTextView.setOnClickListener(v -> {
                if (listener != null && !studentSubs.isEmpty()) {
                    Subject firstSubject = subjectMap.get(studentSubs.get(0).getSubjectId());
                    if (firstSubject != null) listener.onSubjectClick(firstSubject);
                }
            });

            averageGradeTextView.setOnClickListener(v -> {
                if (listener != null && !studentSubs.isEmpty()) {
                    listener.onGradeClick(studentSubs.get(0));
                }
            });
        }
    }
}