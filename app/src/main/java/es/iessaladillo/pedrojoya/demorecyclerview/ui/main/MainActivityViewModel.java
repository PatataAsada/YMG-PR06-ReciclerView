package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.profile.ProfileActivity;

import static android.app.Activity.RESULT_OK;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {
    private static final String STUDENT = "STUDENT";
    private static final Object EDIT_USER_REQUEST = 1;
    private final DatabaseStudents databaseStudents;
    private LiveData<List<Student>> students;

    // TODO: Define data needs to be retained during configuration change (state)
    public MainActivityViewModel(DatabaseStudents databaseStudents) {
        this.databaseStudents = databaseStudents;
    }


    public LiveData<List<Student>> getStudents(boolean forceload) {
        if (students == null || forceload) {
            students = databaseStudents.getStudents();
        }
        return students;
    }

    public void deleteStudent(Student item) {
        databaseStudents.deleteStudent(item);
    }

    public void editStudent(Student oldStudent, Student newStudent) {
        students.getValue().set(students.getValue().indexOf(oldStudent),newStudent);
    }

    public void addStudent(Student newStudent) {
        databaseStudents.addStudent(newStudent);
        students = databaseStudents.getStudents();
    }
}
