package es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityAvatarBinding;
import es.iessaladillo.pedrojoya.demorecyclerview.utils.ResourcesUtils;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AvatarActivity extends AppCompatActivity {

    private Intent extra_avatar;
    private Avatar avatar;
    private int lastAvatar;
    private int chosenAvatar;
    private ActivityAvatarBinding ab;

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = DataBindingUtil.setContentView(this, R.layout.activity_avatar);
        setContentView(R.layout.activity_avatar);
        initViews();
        initListeners();
        initIntent(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CHOSENAVATAR",chosenAvatar);
    }

    private void initIntent(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            extra_avatar = getIntent();
            avatar = extra_avatar.getParcelableExtra("EXTRA_AVATAR");
            selectedAvatar((int) avatar.getId());
        }else{
            chosenAvatar = savedInstanceState.getInt("CHOSENAVATAR",0);
            selectedAvatar(chosenAvatar);
        }
    }

    private void selectedAvatar(int avatarId) {
        chosenAvatar = avatarId;
        deselectImageView(lastAvatar);
        lastAvatar = chosenAvatar;
        selectAvatar(chosenAvatar);
    }

    private void selectAvatar(int chosenAvatar) {
        switch (chosenAvatar) {
            case 1:
                ab.imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 2:
                ab.imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 3:
                ab.imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 4:
                ab.imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 5:
                ab.imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 6:
                ab.imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
        }
    }

    private void deselectImageView(int lastAvatar) {
        switch (lastAvatar) {
            case 1:
                ab.imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 2:
                ab.imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 3:
                ab.imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 4:
                ab.imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 5:
                ab.imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 6:
                ab.imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
        }
    }

    private void initListeners() {
        ab.lblAvatar1.setOnClickListener(v -> selectedAvatar(1));
        ab.imgAvatar1.setOnClickListener(v -> selectedAvatar(1));
        ab.lblAvatar2.setOnClickListener(v -> selectedAvatar(2));
        ab.imgAvatar2.setOnClickListener(v -> selectedAvatar(2));
        ab.lblAvatar3.setOnClickListener(v -> selectedAvatar(3));
        ab.imgAvatar3.setOnClickListener(v -> selectedAvatar(3));
        ab.lblAvatar4.setOnClickListener(v -> selectedAvatar(4));
        ab.imgAvatar4.setOnClickListener(v -> selectedAvatar(4));
        ab.lblAvatar5.setOnClickListener(v -> selectedAvatar(5));
        ab.imgAvatar5.setOnClickListener(v -> selectedAvatar(5));
        ab.lblAvatar6.setOnClickListener(v -> selectedAvatar(6));
        ab.imgAvatar6.setOnClickListener(v -> selectedAvatar(6));
    }


    private void returnAvatar(long selected) {
        extra_avatar = new Intent();
        extra_avatar.putExtra("EXTRA_AVATAR", Database.getInstance().queryAvatar(selected));
        setResult(RESULT_OK, extra_avatar);
        finish();
    }

    private void initViews() {
        ab.imgAvatar1.setTag(R.id.imgAvatar1);
        ab.imgAvatar2.setTag(R.id.imgAvatar2);
        ab.imgAvatar3.setTag(R.id.imgAvatar3);
        ab.imgAvatar4.setTag(R.id.imgAvatar4);
        ab.imgAvatar5.setTag(R.id.imgAvatar5);
        ab.imgAvatar6.setTag(R.id.imgAvatar6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            returnAvatar(chosenAvatar);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
