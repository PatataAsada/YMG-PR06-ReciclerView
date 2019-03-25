package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private static final String STUDENT = "STUDENT";
    private static final int EDIT_USER_REQUEST = 1;
    private static final int ADD_STUDENT_REQUEST = 2;
    private ActivityMainBinding db;
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    public Student newStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new DatabaseStudents())).get(MainActivityViewModel.class);


        // TODO: Give viewModel to binding.
        // TODO: Give lifecycle to binding.
        setupViews();
        observeStudents();
        
        // TODO: Observe emptyView visibility state.
    }

    private void observeStudents() {
        viewModel.getStudents(true).observe(this, students -> {
            listAdapter.submitList(students);
            db.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });

    }

    private void setupViews() {
        setupRecyclerView();
        setupFAB();
    }

    private void setupFAB() {
        db.fabAdd.setOnClickListener(v -> {
            Intent student = new Intent(v.getContext(), ProfileActivity.class);
            startActivityForResult(student, ADD_STUDENT_REQUEST);
            onActivityResult(ADD_STUDENT_REQUEST, RESULT_OK, student);
            viewModel.addStudent(newStudent);
        });
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(position -> editStudent(listAdapter.getItem(position)), position -> deleteStudent(listAdapter.getItem(position)));
        db.lstStudents.setHasFixedSize(true);
        db.lstStudents.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstStudents_columns)));
        db.lstStudents.setItemAnimator(new DefaultItemAnimator());
        db.lstStudents.setAdapter(listAdapter);
    }

    private void deleteStudent(Student item) {
        viewModel.deleteStudent(item);
    }

    private void editStudent(Student item) {
        Intent student = new Intent(this,ProfileActivity.class);
        student.putExtra(STUDENT, item);
        startActivityForResult(student, EDIT_USER_REQUEST);
        onActivityResult(EDIT_USER_REQUEST, RESULT_OK, student);
        viewModel.editStudent(item,newStudent);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDIT_USER_REQUEST:
                if (resultCode == RESULT_OK) {
                    setNewStudent(data.getParcelableExtra(STUDENT));
                }
                break;
            case ADD_STUDENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    setNewStudent(data.getParcelableExtra(STUDENT));
                }
        }
    }

    private void setNewStudent(Student student) {
        newStudent = student;
    }
}
