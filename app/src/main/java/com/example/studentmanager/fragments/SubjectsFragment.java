package com.example.studentmanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.studentmanager.R;

public class SubjectsFragment extends Fragment {

    public static SubjectsFragment newInstance() {
        return new SubjectsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subjects, container, false);
    }
}