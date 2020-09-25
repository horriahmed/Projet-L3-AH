package com.example.randonner;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeTitelPupUp extends Dialog {
    // les Attributs
    private String title;
    private String subTitle;
    private Button enregitrer;
    private TextView titleView ,subTitleView;
    private EditText editText;


    public ChangeTitelPupUp(Activity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.activity_popup);
        this.title = "Mon titre";
        //this.subTitle = "Mon sous-titre";
        this.enregitrer = findViewById(R.id.enregistrer);
        this.titleView = findViewById(R.id.titlev);
        this.subTitleView = findViewById(R.id.subtitle);
        this.editText=findViewById(R.id.editText);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Button getEnregitrer() {
        return enregitrer;
    }

    public EditText getEditText() {
        return editText;
    }

    public void build(){
        show();
        this.titleView.setText(title);
        //this.subTitleView.setText(subTitle);

    }
}
