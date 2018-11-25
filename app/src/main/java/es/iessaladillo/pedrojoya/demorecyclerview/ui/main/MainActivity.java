package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding db;
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new DatabaseStudents())).get(MainActivityViewModel.class);


        // TODO: Give viewModel to binding.
        // TODO: Give lifecycle to binding.
        setupViews();
        observeStudents();

        // TODO: Observe data from viewModel, giving them to adapter
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
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(position -> deleteStudent(listAdapter.getItem(position)));
        // TODO: Set listeners of adapter.
        db.lstStudents.setHasFixedSize(true);
        db.lstStudents.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstStudents_columns)));
        db.lstStudents.setItemAnimator(new DefaultItemAnimator());
        db.lstStudents.setAdapter(listAdapter);
    }

    private void deleteStudent(Student item) {
        viewModel.deleteStudent(item);
    }

}
