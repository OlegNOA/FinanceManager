package com.example.financemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    TextView tvEmail;
    Button buttonLogin, buttonRegis;
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle   savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagement = new SessionManagement(Login.this); //создайте объект из SessionManagememnt
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnlogin);
        buttonRegis = findViewById(R.id.signup);
        dbcenter = new DatabaseHelper(this);
        if(sessionManagement.isLoggedIn()){ //вход в систему jika sudah, вход в систему maka menjalankan, переход к действию
            goToActivity();;
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(email.equals("") || email.trim().isEmpty() || password.equals("") || password.trim().isEmpty()  ) {
                    //убедитесь, что письмо не пустое
                    Toast.makeText(Login.this,"Username Password must be filled",Toast.LENGTH_LONG).show();
                }else if(!(email.contains("@"))){
                    //убедитесь, что электронное письмо отправлено в правильном формате
                    Toast.makeText(Login.this,"Enter the correct email format",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SQLiteDatabase db = dbcenter.getReadableDatabase();
                    cursor = db.rawQuery("SELECT * FROM user WHERE email = '" + email + "' AND password = '"+ password+ "'",null);
                    cursor.moveToFirst();
                    if(cursor.getCount()>0){
                        //создайте сеанс
                        sessionManagement.createLoginSession(cursor.getString(0).toString(),cursor.getString(1).toString(),cursor.getString(2).toString(), password);
                        goToActivity();
                    }else {
                        //создайте предупреждение, если пользовательские данные отсутствуют в базе данных
                        Toast.makeText(Login.this, "Username Password must exist in the database", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(),Register.class);
                startActivity(mIntent);
            }
        });
    }
    private void goToActivity(){
        Intent mIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mIntent);
    }

}