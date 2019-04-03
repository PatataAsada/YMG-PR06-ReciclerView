package es.iessaladillo.pedrojoya.demorecyclerview.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class ProfileActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Student student;

    public ProfileActivityViewModelFactory(Student student) {
        this.student = student;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ProfileActivityViewModel(student);
    }
}
