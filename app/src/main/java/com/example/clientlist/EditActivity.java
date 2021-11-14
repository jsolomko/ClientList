package com.example.clientlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientlist.database.AppDataBase;
import com.example.clientlist.database.AppExecutor;
import com.example.clientlist.database.Client;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private EditText edName, edSecName, edTel, edDisc;
    private CheckBox ckImp1, ckImp2, ckImp3, ckSpecial;
    private AppDataBase myDB;
    private CheckBox[] cbArray = new CheckBox[3];
    private int importance = 3;
    private int special = 0;
    private FloatingActionButton fb;
    private boolean isEdit = false;
    private boolean new_user = false;
    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        init();
        getMyIntent();

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCh();
                if (!TextUtils.isEmpty(edName.getText().toString()) && !TextUtils.isEmpty(edSecName.getText().toString())
                        && !TextUtils.isEmpty(edTel.getText().toString()) && !TextUtils.isEmpty(edDisc.getText().toString())) {
                    AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (isEdit) {
                                Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                        edTel.getText().toString(), importance, edDisc.getText().toString(), special);
                                client.setId(id);
                                myDB.clientDAO().updateClient(client);
                                finish();
                            } else {
                                Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                        edTel.getText().toString(), importance, edDisc.getText().toString(), special);
                                myDB.clientDAO().insertClient(client);
                                finish();

                            }
                        }
                    });
                }
            }
        });
    }

    public void init() {
        fb = findViewById(R.id.fb);
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

    private void getMyIntent() {
        Intent i = getIntent();
        if (i != null) {
            if (i.getStringExtra(Constans.NAME_KEY) != null) {

                setIsEdit(false);
                cbArray[0] = ckImp1;
                cbArray[1] = ckImp2;
                cbArray[2] = ckImp3;
                edName.setText(i.getStringExtra(Constans.NAME_KEY));
                edSecName.setText(i.getStringExtra(Constans.SEC_NAME_KEY));
                edTel.setText(i.getStringExtra(Constans.TEL_KEY));
                edDisc.setText(i.getStringExtra(Constans.DESC_KEY));
                edName.setText(i.getStringExtra(Constans.NAME_KEY));

                cbArray[i.getIntExtra(Constans.IMPORTANCE_KEY, 0)].setChecked(true);
                if (i.getIntExtra(Constans.SP_KEY, 0) == 1) ckSpecial.setChecked(true);
                new_user = false;
                id = i.getIntExtra(Constans.ID_KEY, 0);
            } else {
                new_user = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!new_user) getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_edit) {
            setIsEdit(true);
        } else if (id == R.id.delete) deleteDialog();
        return true;
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

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete);
        builder.setPositiveButton(R.string.уes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                edTel.getText().toString(), importance, edDisc.getText().toString(), special);
                        client.setId(id);
                        myDB.clientDAO().deleteClient(client);
                        finish();
                    }
                });
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void setIsEdit(boolean isEdit) {
        if (isEdit) {
            fb.show();

        } else {
            fb.hide();
        }
        this.isEdit = isEdit;
        ckImp1.setClickable(isEdit);
        ckImp2.setClickable(isEdit);
        ckImp3.setClickable(isEdit);
        ckSpecial.setClickable(isEdit);
        edName.setClickable(isEdit);
        edSecName.setClickable(isEdit);
        edTel.setClickable(isEdit);
        edDisc.setClickable(isEdit);
        edDisc.setClickable(isEdit);
        edName.setFocusable(isEdit);
        edSecName.setFocusable(isEdit);
        edTel.setFocusable(isEdit);
        edDisc.setFocusable(isEdit);

        edName.setFocusableInTouchMode(isEdit);
        edSecName.setFocusableInTouchMode(isEdit);
        edTel.setFocusableInTouchMode(isEdit);
        edDisc.setFocusableInTouchMode(isEdit);
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
