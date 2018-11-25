package es.iessaladillo.pedrojoya.demorecyclerview.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Avatar implements Parcelable {
    private static long id_setter = 1;
    private long id;
    private int imageResId;
    private String name;

    public Avatar(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
        id = id_setter;
        id_setter++;
    }

    protected Avatar(Parcel in) {
        id = in.readLong();
        imageResId = in.readInt();
        name = in.readString();
    }

    public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
        @Override
        public Avatar createFromParcel(Parcel in) {
            return new Avatar(in);
        }

        @Override
        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(imageResId);
        dest.writeString(name);
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getName(){
        return name;
    }

    public long getId() {
        return id;
    }
}

