package com.example.studentmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.model.StudentSubject;
import com.example.studentmanager.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> subjects;
    private List<StudentSubject> studentSubjects;
    private OnSubjectClickListener listener;

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject);
        void onTeacherClick(String teacherName);
        void onStatsClick(Subject subject);
    }

    public SubjectAdapter(List<Subject> subjects,
                          List<StudentSubject> studentSubjects,
                          OnSubjectClickListener listener) {
        this.subjects = subjects;
        this.studentSubjects = studentSubjects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject currentSubject = subjects.get(position);
        holder.bind(currentSubject);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void updateSubjects(List<Subject> newSubjects) {
        subjects.clear();
        subjects.addAll(newSubjects);
        notifyDataSetChanged();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView teacherTextView;
        TextView statsTextView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewSubjectName);
            teacherTextView = itemView.findViewById(R.id.textViewTeacher);
            statsTextView = itemView.findViewById(R.id.textViewStats);
        }

        public void bind(Subject subject) {
            nameTextView.setText(subject.getName());
            teacherTextView.setText(subject.getTeacher());

            // Statistika hisoblash
            int studentCount = 0;
            float totalGrade = 0;

            for (StudentSubject ss : studentSubjects) {
                if (ss.getSubjectId() == subject.getId()) {
                    studentCount++;
                    totalGrade += ss.getGrade();
                }
            }

            if (studentCount > 0) {
                float averageGrade = totalGrade / studentCount;
                statsTextView.setText(String.format("%d talaba, o'rtacha: %.1f",
                        studentCount, averageGrade));
            } else {
                statsTextView.setText("Hali baho yo'q");
            }

            // Click listenerlar
            nameTextView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSubjectClick(subject);
                }
            });

            teacherTextView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTeacherClick(subject.getTeacher());
                }
            });

            statsTextView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onStatsClick(subject);
                }
            });
        }
    }
}