package es.iessaladillo.pedrojoya.demorecyclerview.ui.profile;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class ProfileActivityViewModel extends ViewModel {
    public Student student;
    public Avatar avatar;

    public ProfileActivityViewModel(Student student){
        this.student = new Student();
        if(student!=null){
            this.student.setName(student.getName());
            this.student.setAvatar(student.getAvatar());
            this.student.setEmail(student.getEmail());
            this.student.setPhonenumber(student.getPhonenumber());
            this.student.setAddress(student.getAddress());
            this.student.setWeb(student.getWeb());
            this.avatar = student.getAvatar();
        }
        else{
            avatar = Database.getInstance().queryAvatar(1);
            this.student.setAvatar(avatar);
        }
    }
}
