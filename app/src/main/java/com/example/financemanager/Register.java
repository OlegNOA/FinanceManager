package com.example.financemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText edtEmail, edtPassword,edtUser, edtPass;
    DatabaseHelper dbcenter;
    Button tonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbcenter = new DatabaseHelper(this); //создание объектов с помощью datahelper
        edtEmail = (EditText) findViewById(R.id.editemail);
        edtPassword = (EditText) findViewById(R.id.editpassword);
        edtPass = (EditText) findViewById(R.id.editkonfpass);
        edtUser =(EditText) findViewById(R.id.edituser);
        tonReg=(Button) findViewById(R.id.buttonregister);
        tonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUser.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String konfPass = edtPass.getText().toString(); //сохранение входных значений в переменных
                if(email.equals("") || email.trim().isEmpty() || password.equals("") || password.trim().isEmpty() ||
                        username.equals("") || username.trim().isEmpty() || konfPass.equals("") || konfPass.trim().isEmpty())//проверьте условие, чтобы столбец не был пустым
                {
                    Toast.makeText(Register.this,"All fields must be filled",Toast.LENGTH_LONG).show();
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){//cek format email
                    Toast.makeText(Register.this,"Enter the correct email format",Toast.LENGTH_LONG).show();

                }else if(!konfPass.equals(password)){//убедитесь, что введенный пароль тот же самый
                    Toast.makeText(Register.this,"Incorrect password confirmation",Toast.LENGTH_LONG).show();
                }else{//если все условия выполнены, то это означает успех

                    dbcenter.addUser(email,username,password); //запустите метод для добавления пользовательских данных
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    Intent mIntent = new Intent(getApplicationContext(),Login.class);
                    startActivity(mIntent);}//перейдите на страницу входа в систему
            }
        });
    }
}