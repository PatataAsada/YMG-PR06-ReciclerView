package es.iessaladillo.pedrojoya.demorecyclerview.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;

@SuppressWarnings("ALL")
public class Student implements Parcelable {

    // TODO: Define Student properties.
    private long id;
    private Avatar avatar;
    private String name;
    private String email;
    private int phonenumber;
    private String address;
    private String web;

    public Student(long id, int imageResId, String name, String email, int phonenumber, String address, String web) {
        this.id = id;
        this.avatar = Database.getInstance().queryAvatars().get(imageResId);
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.web = web;
    }

    protected Student(Parcel in) {
        id = in.readLong();
        avatar = in.readParcelable(getClass().getClassLoader());
        name = in.readString();
        email = in.readString();
        phonenumber = in.readInt();
        address = in.readString();
        web = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(avatar,flags);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(phonenumber);
        dest.writeString(address);
        dest.writeString(web);
    }
}
