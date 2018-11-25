package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;

// TODO: Make class implements ViewModelProvider.Factory
@SuppressWarnings("WeakerAccess")
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final DatabaseStudents databaseStudents;

    public MainActivityViewModelFactory(DatabaseStudents databaseStudents) {
        this.databaseStudents = databaseStudents;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(databaseStudents);
    }
}
