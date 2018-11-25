package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class MainActivityViewModel extends ViewModel {
    private final DatabaseStudents databaseStudents;
    private LiveData<List<Student>> students;

    // TODO: Define data needs to be retained during configuration change (state)
    public MainActivityViewModel(DatabaseStudents databaseStudents){
        this.databaseStudents = databaseStudents;
    }


    public LiveData<List<Student>> getStudents(boolean forceload) {
        if(students==null||forceload){
            students = databaseStudents.getStudents();
        }
        return students;
    }

    public void deleteStudent(Student item) {
        databaseStudents.deleteStudent(item);
    }
}
