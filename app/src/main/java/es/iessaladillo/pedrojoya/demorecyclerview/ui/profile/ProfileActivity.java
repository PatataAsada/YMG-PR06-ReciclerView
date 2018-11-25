package es.iessaladillo.pedrojoya.demorecyclerview.ui.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.demorecyclerview.utils.ValidationUtils;

import static es.iessaladillo.pedrojoya.demorecyclerview.utils.KeyboardUtils.hideSoftKeyboard;

@SuppressWarnings("SpellCheckingInspection")
public class ProfileActivity extends AppCompatActivity {

    private static final String STUDENT = "STUDENT";
    private static final String EXTRA_AVATAR = "EXTRA_AVATAR";
    final int PICK_AVATAR_REQUEST = 1;
    private Avatar avatar = Database.getInstance().queryAvatar(1);
    private TextView lblAvatar;
    private ImageView imgAvatar;
    private TextView lblName;
    private EditText txtName;
    private TextView lblEmail;
    private EditText txtEmail;
    private ImageView imgEmail;
    private TextView lblPhonenumber;
    private EditText txtPhonenumber;
    private ImageView imgPhonenumber;
    private TextView lblAddress;
    private EditText txtAddress;
    private ImageView imgAddress;
    private TextView lblWeb;
    private EditText txtWeb;
    private ImageView imgWeb;
    private Intent intention;
    @SuppressWarnings("FieldCanBeLocal")
    private Intent oldStudent;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setupViews(savedInstanceState);
        initListeners();
        initIntent(savedInstanceState);
    }

    @SuppressWarnings("ConstantConditions")
    private void initIntent(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            oldStudent = getIntent();
            student = oldStudent.getParcelableExtra(STUDENT);
            avatar = student.getAvatar();
        }else{
            oldStudent = savedInstanceState.getParcelable(STUDENT);
            student = oldStudent.getParcelableExtra(STUDENT);
            avatar = student.getAvatar();
        }
        setAvatar(avatar);
        txtName.setText(student.getName());
        txtAddress.setText(student.getAddress());
        txtEmail.setText(student.getEmail());
        txtPhonenumber.setText(student.getPhonenumber());
        txtWeb.setText(student.getWeb());
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
        student.setAvatar(avatar);
        student.setName(txtName.toString());
        student.setEmail(txtEmail.toString());
        student.setPhonenumber(Integer.parseInt(txtPhonenumber.toString()));
        student.setAddress(txtAddress.toString());
        student.setWeb(txtWeb.toString());

        outState.putParcelable(STUDENT,student);
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
        imgAvatar.setOnClickListener(this::changeAvatar);
        lblAvatar.setOnClickListener(this::changeAvatar);
    }

    private void changeAvatar(View v) {
        intention = new Intent(v.getContext(), AvatarActivity.class);

        intention.putExtra(EXTRA_AVATAR, avatar);
        startActivityForResult(intention, PICK_AVATAR_REQUEST);

        onActivityResult(PICK_AVATAR_REQUEST, RESULT_OK, intention);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AVATAR_REQUEST) {
            if (resultCode == RESULT_OK) {
                avatar = Database.getInstance().queryAvatar(((Avatar) Objects.requireNonNull(data).getParcelableExtra("EXTRA_AVATAR")).getId());
                setAvatar(avatar);
            }
        }
    }

    private void setAvatar(Avatar extra_avatar) {
        imgAvatar.setTag(extra_avatar.getImageResId());
        imgAvatar.setImageResource(extra_avatar.getImageResId());
        lblAvatar.setText(extra_avatar.getName());
    }

    private void imeListener() {
        //When pressing IME done button.
        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
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
        imgEmail.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_SENDTO);
            intention.setData(Uri.parse("mailto:" + txtEmail.getText()));
            startActivity(intention);
        });

        //PHONENUMBER
        imgPhonenumber.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_DIAL);
            intention.setData(Uri.parse("tel:" + txtPhonenumber.getText()));
            startActivity(intention);
        });

        //ADDRESS
        imgAddress.setOnClickListener(v -> {
            intention = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + txtAddress.getText().toString()));
            intention.setPackage("com.google.android.apps.maps");
            startActivity(intention);
        });

        //WEB
        imgWeb.setOnClickListener(v -> {
            String url = txtWeb.getText().toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
            intention = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intention);
        });
    }

    private void focusListeners() {
        txtName.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, lblName));
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, lblEmail));
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, lblPhonenumber));
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, lblAddress));
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> isFocused(hasFocus, lblWeb));
    }

    private void isFocused(boolean hasFocus, TextView lbl) {
        if (hasFocus) lbl.setTypeface(Typeface.DEFAULT_BOLD);
        else lbl.setTypeface(Typeface.DEFAULT);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupViews(Bundle savedInstanceState) {
        //AVATAR
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        if(savedInstanceState == null){
            setDefault(imgAvatar, lblAvatar);
        }
        else{
            avatar = savedInstanceState.getParcelable("AVATAR");
            setAvatar(avatar);
        }
        //NAME
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        lblName.setTypeface(Typeface.DEFAULT_BOLD);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        //EMAIL
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgEmail.setTag(R.drawable.ic_email_24dp);
        //PHONENUMBER
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        imgPhonenumber.setTag(R.drawable.ic_call_24dp);
        //ADDRESS
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        imgAddress.setTag(R.drawable.ic_map_24dp);
        //WEB
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);
        imgWeb.setTag(R.drawable.ic_web_24dp);
    }

    private void setDefault(ImageView imgAvatar, TextView nameAvatar) {
        Avatar defAvatar = Database.getInstance().getDefaultAvatar();
        imgAvatar.setImageResource(defAvatar.getImageResId());
        imgAvatar.setTag(defAvatar.getImageResId());
        nameAvatar.setText(defAvatar.getName());
    }

    private void textChangedListeners() {
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(txtName, lblName, null, 0, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(txtEmail, lblEmail, imgEmail, 1, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(txtPhonenumber, lblPhonenumber, imgPhonenumber, 2, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(txtAddress, lblAddress, imgAddress, 0, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(txtWeb, lblWeb, imgWeb, 3, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkText(EditText txt, TextView lbl, ImageView img, int i, int count) {
        switch (i) {
            case 0:
                if (img == null) {
                    if (count == 0) {
                        txt.setError(getString(R.string.main_invalid_data));
                        lbl.setEnabled(false);
                    } else {
                        lbl.setEnabled(true);
                    }
                } else {
                    if (count == 0) {
                        txt.setError(getString(R.string.main_invalid_data));
                        lbl.setEnabled(false);
                        img.setEnabled(false);
                    } else {
                        lbl.setEnabled(true);
                        img.setEnabled(true);
                    }
                }
                break;
            case 1:
                if (!ValidationUtils.isValidEmail(txt.getText().toString())) {
                    txt.setError(getString(R.string.main_invalid_data));
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
            case 2:
                if (!ValidationUtils.isValidPhone(txt.getText().toString())) {
                    txt.setError(getString(R.string.main_invalid_data));
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
            case 3:
                if (!ValidationUtils.isValidUrl(txt.getText().toString())) {
                    txt.setError(getString(R.string.main_invalid_data));
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
        }
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
        if (!ValidationUtils.isValidForm(txtEmail.getText().toString(), txtPhonenumber.getText().toString(), txtWeb.getText().toString(),
                txtName.getText().toString(), txtAddress.getText().toString())) {
            Snackbar.make(txtWeb, getString(R.string.main_saved_succesfully), Snackbar.LENGTH_SHORT).show();
        } else{
            showErrors();
            Snackbar.make(txtWeb, getString(R.string.main_error_saving), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void showErrors() {
        if (!ValidationUtils.isValidText(txtName.getText().toString())) txtName.setText("");
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) txtEmail.setText("");
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) txtPhonenumber.setText("");
        if (!ValidationUtils.isValidText(txtAddress.getText().toString())) txtAddress.setText("");
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) txtWeb.setText("");
    }
}
