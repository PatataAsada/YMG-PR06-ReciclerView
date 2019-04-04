package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.MainFragmentBinding;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.profile.ProfileActivity;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private static final String STUDENT = "STUDENT";
    private static final int EDIT_USER_REQUEST = 1;
    private static final int ADD_STUDENT_REQUEST = 2;
    private MainFragmentBinding db;
    private MainActivityAdapter listAdapter;
    public Student oldStudent;

    private MainFragmentViewModel mainFragmentViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = DataBindingUtil.setContentView(getActivity(), R.layout.main_fragment);
        mainFragmentViewModel = ViewModelProviders.of(getActivity()).get(MainFragmentViewModel.class);

        setupViews();
        observeStudents();
    }
    private void observeStudents() {
        mainFragmentViewModel.getStudents(true).observe(this, students -> {
            listAdapter.submitList(students);
            db.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });

    }
    private void setupViews() {
        setupRecyclerView();
        setupFAB();
        setupEmptyView();
    }
    private void setupEmptyView() {
        db.lblEmptyView.setOnClickListener(this::addStudent);
    }

    private void setupFAB() {
        db.fabAdd.setOnClickListener(this::addStudent);
    }

    private void addStudent(View v) {
        Intent student = new Intent(v.getContext(), ProfileActivity.class);
        startActivityForResult(student,ADD_STUDENT_REQUEST);

        onActivityResult(ADD_STUDENT_REQUEST, RESULT_OK, student);
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(position -> editStudent(listAdapter.getItem(position)), position -> deleteStudent(listAdapter.getItem(position)));
        db.lstStudents.setHasFixedSize(true);
        db.lstStudents.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lstStudents_columns)));
        db.lstStudents.setItemAnimator(new DefaultItemAnimator());
        db.lstStudents.setAdapter(listAdapter);
    }

    private void deleteStudent(Student item) {
        mainFragmentViewModel.deleteStudent(item);
    }

    private void editStudent(Student item) {
        Intent student = new Intent(getContext(), ProfileActivity.class);
        student.putExtra(STUDENT, item);
        startActivityForResult(student, EDIT_USER_REQUEST);
        oldStudent = item;
        onActivityResult(EDIT_USER_REQUEST, RESULT_OK, student);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_USER_REQUEST:
                if (resultCode == RESULT_OK) {
                    mainFragmentViewModel.editStudent(oldStudent, data.getParcelableExtra(STUDENT));
                }
                break;
            case ADD_STUDENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    mainFragmentViewModel.addStudent(data.getParcelableExtra(STUDENT));
                }
        }
    }
}
