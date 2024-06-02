package com.example.financemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Locale;

public class ViewNotes extends AppCompatActivity {
    DatabaseHelper dbcenter;
    Cursor cursor;
    TextView text1, text2, text3, text4, text5, text6, text7;
    private SessionManagement sessionManagement;
    String idtran ="", types="", jml= "", date="", description="",categories="";
    Button del,upd,back;
    private ImageView imgnote;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_notes);
        sessionManagement = new SessionManagement(ViewNotes.this);
        dbcenter = new DatabaseHelper(this); //создание объектов с помощью datahelper
        text1 = (TextView) findViewById(R.id.lhtСategories);
        text2 = (TextView) findViewById(R.id.cat);
        text3 = (TextView) findViewById(R.id.mon);
        text4 = (TextView) findViewById(R.id.date);
        text5 = (TextView) findViewById(R.id.not);
        imgnote = (ImageView) findViewById(R.id.imgnote);
        del = (Button) findViewById(R.id.delete);
        upd = (Button) findViewById(R.id.update);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        final HashMap<String, String> user = sessionManagement.getUserInformation();

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transactions WHERE id_transactions = '" + getIntent().getStringExtra("id") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) //если результат запроса не является пустым
        {
            //заполните переменную TextView результатами запроса в соответствии с индексом
            cursor.moveToPosition(0);
            idtran = cursor.getString(0).toString();
            date =  cursor.getString(1).toString();
            types = cursor.getString(2).toString();
            categories = cursor.getString(3).toString();
            jml = cursor.getString(5).toString();
            description =  cursor.getString(6).toString();

            text1.setText(cursor.getString(2).toString().toUpperCase(Locale.ROOT));
            text2.setText(cursor.getString(3).toString());
            text3.setText(cursor.getString(5).toString());
            text4.setText(cursor.getString(1).toString());
            text5.setText(cursor.getString(6).toString());

        }

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(), UpdatesNotes.class);
                //пропустить данные из классических заметок об обновлениях
                m.putExtra("idTran",idtran);
                m.putExtra("types",types);
                m.putExtra("categories",categories);
                m.putExtra("total", jml);
                m.putExtra("date", date);
                m.putExtra("description",description);
                startActivity(m);
            }
        });
    }

    //создайте оповещение с подтверждением, если вы хотите удалить заметку
    public void delete(View view){
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(ViewNotes.this);
                alert.setTitle("Delete"); //установите заголовок
                alert.setMessage("Are you sure you want to delete this one?"); //упорядочивание содержимого сообщений
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){ //если вы выберете "Да", данные будут удалены

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                        db.execSQL("delete from transactions where id_transactions = "+idtran);
                        Intent m = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(m);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener(){ //если этот флажок установлен, диалоговое окно закрываться не будет

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog ale = alert.create();
                ale.show();;

            }
        });

    }
}