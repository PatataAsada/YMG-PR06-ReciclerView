package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavHost {
    private ActivityMainBinding db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }


    @NonNull
    @Override
    public NavController getNavController() {
        return null;
    }
}
