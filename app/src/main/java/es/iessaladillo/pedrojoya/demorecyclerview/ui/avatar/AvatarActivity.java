package es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.demorecyclerview.utils.ResourcesUtils;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AvatarActivity extends AppCompatActivity {

    private Intent extra_avatar;
    private Avatar avatar;
    private int lastAvatar;
    private int chosenAvatar;
    private TextView lblAvatar1;
    private ImageView imgAvatar1;
    private TextView lblAvatar2;
    private ImageView imgAvatar2;
    private TextView lblAvatar3;
    private ImageView imgAvatar3;
    private TextView lblAvatar4;
    private ImageView imgAvatar4;
    private TextView lblAvatar5;
    private ImageView imgAvatar5;
    private TextView lblAvatar6;
    private ImageView imgAvatar6;

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 2:
                imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 3:
                imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 4:
                imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 5:
                imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
            case 6:
                imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
                break;
        }
    }

    private void deselectImageView(int lastAvatar) {
        switch (lastAvatar) {
            case 1:
                imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 2:
                imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 3:
                imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 4:
                imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 5:
                imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
            case 6:
                imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
                break;
        }
    }

    private void initListeners() {
        lblAvatar1.setOnClickListener(v -> selectedAvatar(1));
        imgAvatar1.setOnClickListener(v -> selectedAvatar(1));
        lblAvatar2.setOnClickListener(v -> selectedAvatar(2));
        imgAvatar2.setOnClickListener(v -> selectedAvatar(2));
        lblAvatar3.setOnClickListener(v -> selectedAvatar(3));
        imgAvatar3.setOnClickListener(v -> selectedAvatar(3));
        lblAvatar4.setOnClickListener(v -> selectedAvatar(4));
        imgAvatar4.setOnClickListener(v -> selectedAvatar(4));
        lblAvatar5.setOnClickListener(v -> selectedAvatar(5));
        imgAvatar5.setOnClickListener(v -> selectedAvatar(5));
        lblAvatar6.setOnClickListener(v -> selectedAvatar(6));
        imgAvatar6.setOnClickListener(v -> selectedAvatar(6));
    }


    private void returnAvatar(long selected) {
        extra_avatar = new Intent();
        extra_avatar.putExtra("EXTRA_AVATAR", Database.getInstance().queryAvatar(selected));
        setResult(RESULT_OK, extra_avatar);
        finish();
    }

    private void initViews() {
        lblAvatar1 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        imgAvatar1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        imgAvatar1.setTag(R.id.imgAvatar1);
        lblAvatar2 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        imgAvatar2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        imgAvatar2.setTag(R.id.imgAvatar2);
        lblAvatar3 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        imgAvatar3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        imgAvatar3.setTag(R.id.imgAvatar3);
        lblAvatar4 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        imgAvatar4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        imgAvatar4.setTag(R.id.imgAvatar4);
        lblAvatar5 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        imgAvatar5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        imgAvatar5.setTag(R.id.imgAvatar5);
        lblAvatar6 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);
        imgAvatar6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);
        imgAvatar6.setTag(R.id.imgAvatar6);
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
