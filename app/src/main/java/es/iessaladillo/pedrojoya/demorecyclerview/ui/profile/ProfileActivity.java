package es.iessaladillo.pedrojoya.demorecyclerview.ui.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityStudentBinding;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.demorecyclerview.utils.TextChangedListener;
import es.iessaladillo.pedrojoya.demorecyclerview.utils.ValidationUtils;

import static es.iessaladillo.pedrojoya.demorecyclerview.utils.KeyboardUtils.hideSoftKeyboard;

@SuppressWarnings("SpellCheckingInspection")
public class ProfileActivity extends AppCompatActivity {

    private static final String STUDENT = "STUDENT";
    private static final String EXTRA_AVATAR = "EXTRA_AVATAR";
    private ActivityStudentBinding sb;
    final int PICK_AVATAR_REQUEST = 1;
    private Intent intention;
    @SuppressWarnings("FieldCanBeLocal")
    private ProfileActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ProfileActivityViewModelFactory(getIntent().getParcelableExtra(STUDENT))).get(ProfileActivityViewModel.class);
        sb = DataBindingUtil.setContentView(this, R.layout.activity_student);
        setupViews(savedInstanceState);
        initListeners();
        fillViewsWithViewModel();
    }

    @SuppressWarnings("ConstantConditions")
    private void fillViewsWithViewModel() {

        if (viewModel.student != null) {
            setAvatar(viewModel.avatar);
            sb.layoutForm.txtName.setText(viewModel.student.getName());
            sb.layoutForm.txtAddress.setText(viewModel.student.getAddress());
            sb.layoutForm.txtEmail.setText(viewModel.student.getEmail());
            if(!(viewModel.student.getPhonenumber()<0)){
                sb.layoutForm.txtPhonenumber.setText(String.valueOf(viewModel.student.getPhonenumber()));
            }
            sb.layoutForm.txtWeb.setText(viewModel.student.getWeb());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Text change Listeners
        textChangedListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.student.setAvatar(viewModel.avatar);
        viewModel.student.setName(sb.layoutForm.txtName.toString());
        viewModel.student.setEmail(sb.layoutForm.txtEmail.toString());
        if(sb.layoutForm.txtPhonenumber.toString().isEmpty()){
            viewModel.student.setPhonenumber(Integer.parseInt(sb.layoutForm.txtPhonenumber.getText().toString()));
        }
        viewModel.student.setAddress(sb.layoutForm.txtAddress.toString());
        viewModel.student.setWeb(sb.layoutForm.txtWeb.toString());
    }

    private void initListeners() {
        //Focus Listeners
        focusListeners();

        //Icon press listeners.
        iconListeners();

        //IME Option
        imeListener();

        //When pressing avatar image or name.
        avatarListeners();
    }

    private void avatarListeners() {
        sb.layoutAvatar.imgAvatar.setOnClickListener(this::changeAvatar);
        sb.layoutAvatar.lblAvatar.setOnClickListener(this::changeAvatar);
    }

    private void changeAvatar(View v) {
        intention = new Intent(v.getContext(), AvatarActivity.class);

        intention.putExtra(EXTRA_AVATAR, viewModel.avatar);
        startActivityForResult(intention, PICK_AVATAR_REQUEST);

        onActivityResult(PICK_AVATAR_REQUEST, RESULT_OK, intention);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AVATAR_REQUEST) {
            if (resultCode == RESULT_OK) {
                viewModel.avatar = Database.getInstance().queryAvatar(((Avatar) Objects.requireNonNull(data).getParcelableExtra("EXTRA_AVATAR")).getId());
                viewModel.student.setAvatar(viewModel.avatar);
                setAvatar(viewModel.avatar);
            }
        }
    }

    private void setAvatar(Avatar extra_avatar) {
        sb.layoutAvatar.imgAvatar.setTag(extra_avatar.getImageResId());
        sb.layoutAvatar.imgAvatar.setImageResource(extra_avatar.getImageResId());
        sb.layoutAvatar.lblAvatar.setText(extra_avatar.getName());
    }

    private void imeListener() {
        //When pressing IME done button.
        sb.layoutForm.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                hideSoftKeyboard(this);
                return true;
            }
            return false;
        });
    }

    private void iconListeners() {
        //EMAIL
        sb.layoutForm.imgEmail.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_SENDTO);
            intention.setData(Uri.parse("mailto:" + sb.layoutForm.txtEmail.getText()));
            startActivity(intention);
        });

        //PHONENUMBER
        sb.layoutForm.imgPhonenumber.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_DIAL);
            intention.setData(Uri.parse("tel:" + sb.layoutForm.txtPhonenumber.getText()));
            startActivity(intention);
        });

        //ADDRESS
        sb.layoutForm.imgAddress.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + sb.layoutForm.txtAddress.getText().toString()));
            intention.setPackage("com.google.android.apps.maps");
            startActivity(intention);
        });

        //WEB
        sb.layoutForm.imgWeb.setOnClickListener(v -> {
            String url = sb.layoutForm.txtWeb.getText().toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
            intention = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intention);
        });
    }

    private void focusListeners() {
        sb.layoutForm.txtName.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, sb.layoutForm.lblName));
        sb.layoutForm.txtEmail.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, sb.layoutForm.lblEmail));
        sb.layoutForm.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, sb.layoutForm.lblPhonenumber));
        sb.layoutForm.txtAddress.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, sb.layoutForm.lblAddress));
        sb.layoutForm.txtWeb.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, sb.layoutForm.lblWeb));
    }

    private void isFocused(boolean hasFocus, TextView lbl) {
        if (hasFocus) lbl.setTypeface(Typeface.DEFAULT_BOLD);
        else lbl.setTypeface(Typeface.DEFAULT);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupViews(Bundle savedInstanceState) {
        //AVATAR
        setAvatar(viewModel.avatar);
        //NAME
        sb.layoutForm.lblName.setTypeface(Typeface.DEFAULT_BOLD);
        //EMAIL
        sb.layoutForm.imgEmail.setTag(R.drawable.ic_email_24dp);
        //PHONENUMBER
        sb.layoutForm.imgPhonenumber.setTag(R.drawable.ic_call_24dp);
        //ADDRESS
        sb.layoutForm.imgAddress.setTag(R.drawable.ic_map_24dp);
        //WEB
        sb.layoutForm.imgWeb.setTag(R.drawable.ic_web_24dp);
    }

    private void setDefault(ImageView imgAvatar, TextView nameAvatar) {
        Avatar defAvatar = Database.getInstance().getDefaultAvatar();
        imgAvatar.setImageResource(defAvatar.getImageResId());
        imgAvatar.setTag(defAvatar.getImageResId());
        nameAvatar.setText(defAvatar.getName());
    }

    private void textChangedListeners() {
        TextChangedListener.is_changed(sb.layoutForm.txtName, sb.layoutForm.lblName, null, 0);
        TextChangedListener.is_changed(sb.layoutForm.txtEmail, sb.layoutForm.lblEmail, sb.layoutForm.imgEmail, 1);
        TextChangedListener.is_changed(sb.layoutForm.txtPhonenumber, sb.layoutForm.lblPhonenumber, sb.layoutForm.imgPhonenumber, 2);
        TextChangedListener.is_changed(sb.layoutForm.txtAddress, sb.layoutForm.lblAddress, sb.layoutForm.imgAddress, 4);
        TextChangedListener.is_changed(sb.layoutForm.txtWeb, sb.layoutForm.lblWeb, sb.layoutForm.imgWeb, 3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (!ValidationUtils.isValidForm(sb.layoutForm.txtEmail.getText().toString(), sb.layoutForm.txtPhonenumber.getText().toString(), sb.layoutForm.txtWeb.getText().toString(),
                sb.layoutForm.txtName.getText().toString(), sb.layoutForm.txtAddress.getText().toString())) {
            Snackbar.make(sb.layoutForm.txtWeb, getString(R.string.main_saved_succesfully), Snackbar.LENGTH_SHORT).show();
            sendStudent();
        } else {
            showErrors();
            Snackbar.make(sb.layoutForm.txtWeb, getString(R.string.main_error_saving), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void sendStudent() {
        viewModel.student.setAvatar(viewModel.avatar);
        viewModel.student.setName(sb.layoutForm.txtName.getText().toString());
        viewModel.student.setEmail(sb.layoutForm.txtEmail.getText().toString());
        viewModel.student.setPhonenumber(Integer.parseInt(sb.layoutForm.txtPhonenumber.getText().toString()));
        viewModel.student.setAddress(sb.layoutForm.txtAddress.getText().toString());
        viewModel.student.setWeb(sb.layoutForm.txtWeb.getText().toString());
        intention = new Intent();
        intention.putExtra(STUDENT, viewModel.student);
        setResult(RESULT_OK, intention);
        finish();
    }

    private void showErrors() {
        if (!ValidationUtils.isValidText(sb.layoutForm.txtName.getText().toString()))
            sb.layoutForm.txtName.setText("");
        if (!ValidationUtils.isValidEmail(sb.layoutForm.txtEmail.getText().toString()))
            sb.layoutForm.txtEmail.setText("");
        if (!ValidationUtils.isValidPhone(sb.layoutForm.txtPhonenumber.getText().toString()))
            sb.layoutForm.txtPhonenumber.setText("");
        if (!ValidationUtils.isValidText(sb.layoutForm.txtAddress.getText().toString()))
            sb.layoutForm.txtAddress.setText("");
        if (!ValidationUtils.isValidUrl(sb.layoutForm.txtWeb.getText().toString()))
            sb.layoutForm.txtWeb.setText("");
    }
}
