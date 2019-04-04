package es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar;

import android.content.Intent;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;

public class AvatarViewModel extends ViewModel {
    public int chosenAvatar;
    public int lastAvatar;
    public Avatar avatar;

    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    public AvatarViewModel(Intent intent){
        avatar = intent.getParcelableExtra(EXTRA_AVATAR);
        chosenAvatar = (int) avatar.getId();
        lastAvatar = (int) avatar.getId();
    }
}
