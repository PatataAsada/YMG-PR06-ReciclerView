package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.DatabaseStudents;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class MainFragmentViewModel extends ViewModel {
    private final DatabaseStudents databaseStudents;
    private LiveData<List<Student>> students;

    // TODO: Define data needs to be retained during configuration change (state)
    public MainFragmentViewModel(DatabaseStudents databaseStudents) {
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
        int oldstudentindex = students.getValue().indexOf(oldStudent);
        databaseStudents.editStudent(oldstudentindex,newStudent);
    }

    public void addStudent(Student newStudent) {
        databaseStudents.addStudent(newStudent);
    }
}
