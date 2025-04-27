package com.example.studentmanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.studentmanager.adapters.ViewPagerAdapter;
import com.example.studentmanager.databinding.ActivityMainBinding;
import com.example.studentmanager.fragments.StudentsFragment;
import com.example.studentmanager.fragments.SubjectsFragment;
import com.example.studentmanager.viewmodels.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private ViewPagerAdapter adapter;
    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            updateFabBehavior(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding initialization
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ViewModel initialization
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupToolbar();
        setupViewPager();
        setupFloatingActionButton();
        setupObservers();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(this);
        adapter.addFragment(StudentsFragment.newInstance(), getString(R.string.tab_students));
        adapter.addFragment(SubjectsFragment.newInstance(), getString(R.string.tab_subjects));

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position))
        ).attach();
    }

    private void setupFloatingActionButton() {
        binding.fabAdd.setOnClickListener(view -> {
            int currentTab = binding.viewPager.getCurrentItem();
            if (currentTab == 0) {
                viewModel.showAddStudentDialog(this, this::handleOperationResult);
            } else {
                viewModel.showAddSubjectDialog(this, this::handleOperationResult);
            }
        });
    }

    private void handleOperationResult(boolean success) {
        String message = success ? getString(R.string.operation_success) : getString(R.string.operation_failed);
        showSnackbar(message);
    }

    private void updateFabBehavior(int position) {
        if (position == 0) {
            binding.fabAdd.setImageResource(R.drawable.ic_add_student);
            binding.fabAdd.setContentDescription(getString(R.string.add_student));
        } else {
            binding.fabAdd.setImageResource(R.drawable.ic_add_subject);
            binding.fabAdd.setContentDescription(getString(R.string.add_subject));
        }
    }

    private void setupObservers() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.viewPager.setUserInputEnabled(!isLoading);
            binding.fabAdd.setEnabled(!isLoading);
        });

        viewModel.getToastMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                showSnackbar(message);
                viewModel.onToastShown();
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fabAdd)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            viewModel.showSearchDialog(this);
            return true;
//        } else if (item.getItemId() == R.id.action_stats) {
//            viewModel.showStatistics(this);
//            return true;
//        } else if (item.getItemId() == R.id.action_settings) {
//            viewModel.openSettings(this);
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback);
//        binding = null;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (binding.viewPager.getCurrentItem() != 0) {
//            binding.viewPager.setCurrentItem(0);
//        } else {
//            super.onBackPressed();
//        }
//    }
}