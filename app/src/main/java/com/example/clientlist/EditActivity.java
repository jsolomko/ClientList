package com.example.clientlist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientlist.database.AppDataBase;
import com.example.clientlist.database.AppExecutor;
import com.example.clientlist.database.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private EditText edName, edSecName, edTel, edDisc;
    private CheckBox ckImp1, ckImp2, ckImp3, ckSpecial;
    private AppDataBase myDB;
    private int importance = 3;
    private int special = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        init();
        FloatingActionButton fb = findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCh();
                if (!TextUtils.isEmpty(edName.getText().toString()) && !TextUtils.isEmpty(edSecName.getText().toString())
                        && !TextUtils.isEmpty(edTel.getText().toString()) && !TextUtils.isEmpty(edDisc.getText().toString())) {
                    AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                    edTel.getText().toString(), importance, edDisc.getText().toString(), special);
                            myDB.clientDAO().insertClient(client);
                        }
                    });
                }
            }
        });
    }

    public void init() {
        myDB = AppDataBase.getInstance(getApplicationContext());
        edName = findViewById(R.id.edName);
        edSecName = findViewById(R.id.edSecName);
        edTel = findViewById(R.id.edTel);
        edDisc = findViewById(R.id.edDisc);

        ckImp1 = findViewById(R.id.checkBoxImp1);
        ckImp2 = findViewById(R.id.checkBoxImp2);
        ckImp3 = findViewById(R.id.checkBoxImp3);
        ckSpecial = findViewById(R.id.checkBoxSpecial);
    }

    public void onClickCh1(View view) {
        ckImp2.setChecked(false);
        ckImp3.setChecked(false);
    }

    public void onClickCh2(View view) {
        ckImp1.setChecked(false);
        ckImp3.setChecked(false);
    }

    public void onClickCh3(View view) {
        ckImp1.setChecked(false);
        ckImp2.setChecked(false);
    }

    private void getImportanceFromCh() {
        if (ckImp1.isChecked()) {
            importance = 0;
        } else if (ckImp2.isChecked()) {
            importance = 1;
        } else if (ckImp3.isChecked()) {
            importance = 2;
        }
        if (ckSpecial.isChecked()) {
            special = 1;
        }
    }
}
