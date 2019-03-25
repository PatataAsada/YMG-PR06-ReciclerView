package es.iessaladillo.pedrojoya.demorecyclerview.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.demorecyclerview.R;

public class TextChangedListener {
    static String invalid_data = "Invalid data";
    private TextChangedListener(){

    }

    public static void is_changed(EditText texto, TextView label, ImageView imagen, int i){
        texto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkText(texto, label, imagen,  i,count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private static void checkText(EditText txt, TextView lbl, ImageView img, int i, int count) {
        switch (i) {
            case 0:
                if (img == null) {
                    if (count == 0) {
                        txt.setError(invalid_data);
                        lbl.setEnabled(false);
                    } else {
                        lbl.setEnabled(true);
                    }
                } else {
                    if (count == 0) {
                        txt.setError(invalid_data);
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
                    txt.setError(invalid_data);
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
            case 2:
                if (!ValidationUtils.isValidPhone(txt.getText().toString())) {
                    txt.setError(invalid_data);
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
            case 3:
                if (!ValidationUtils.isValidUrl(txt.getText().toString())) {
                    txt.setError(invalid_data);
                    lbl.setEnabled(false);
                    img.setEnabled(false);
                } else {
                    lbl.setEnabled(true);
                    img.setEnabled(true);
                }
                break;
        }
    }
}
