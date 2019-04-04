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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new DatabaseStudents())).get(MainActivityViewModel.class);

    }
}
