package es.iessaladillo.pedrojoya.demorecyclerview.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class DatabaseStudents {

    // TODO: Define DatabaseStudents as a singleton.
    public DatabaseStudents() {
        updateStudentsLiveData();
    }

    private ArrayList<Student> students = new ArrayList<>(Arrays.asList(
            new Student(0, "Baldo", 23),
            new Student(1, "Albedo", 23),
            new Student(2, "Pipo", 23),
            new Student(3, "German", 23)
    ));
    private MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();

    public LiveData<List<Student>> getStudents() {
        return studentsLiveData;
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        updateStudentsLiveData();
    }

    private void updateStudentsLiveData() {
        studentsLiveData.setValue(new ArrayList<>(students));
    }

}
