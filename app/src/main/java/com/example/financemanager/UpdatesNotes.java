package com.example.financemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class UpdatesNotes extends AppCompatActivity {

    Button addincome, addexp, camera;
    Context context;
    Spinner categories;
    ImageView imgnote;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tvDateResult;
    private Button btDatePicker , save;
    EditText date, description, total;
    String imageFilePath;
    String types = "Income";
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;
    Button back;

    //множество категорий доходов и расходов
    private static final String[] pathIncome = {"Salary", "Bonus", "Allowance", "Petty cash", "Other"};
    private static final String[] pathExpenses = {"Food", "Social Life", "Transportation", "Gift", "Healt", "Other"};
    private final int versi = 1;
    String kat, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        sessionManagement = new SessionManagement(UpdatesNotes.this);
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        dbcenter = new DatabaseHelper(this); //создание объекта из класса datahelper
        addincome = (Button) findViewById(R.id.addincome);
        addexp = (Button) findViewById(R.id.addexp);
        imgnote = (ImageView) findViewById(R.id.imgnote);
        date = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.edtnote);
        total = (EditText) findViewById(R.id.edtjumlah);
        categories = findViewById(R.id.spinnerCategory);
        save = (Button)  findViewById(R.id.btnsave);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //отрегулируйте содержимое текстового представления в соответствии с данными
        date.setText(getIntent().getStringExtra("date"));
        total.setText(getIntent().getStringExtra("total"));
        description.setText(getIntent().getStringExtra("description"));
        kat = getIntent().getStringExtra("categories");
        id = getIntent().getStringExtra("idTran");
        types = getIntent().getStringExtra("types");


        if (types.equals("Income")) {
            types = "Income";
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

            } else {

                //измените цвет кнопки
                addincome.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.black));
                addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                addexp.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.white));
                addexp.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.black));

                //установите содержимое счетчика в соответствии с выбранной категорией
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatesNotes.this,
                        android.R.layout.simple_spinner_item, pathIncome);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(adapter);
                int select = 0;
                for (int i = 0; i < pathIncome.length; i++) {
                    if (pathIncome[i].equals(categories)) {
                        select = i;
                        break;
                    }
                }
                categories.setSelection(select);
            }
        }else{
            types = "Expenses";
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {



            } else {
                addexp.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.black));
                addexp.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                addincome.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.white));
                addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.black));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatesNotes.this,
                        android.R.layout.simple_spinner_item, pathExpenses);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(adapter);
                int select =0;
                for (int i=0 ; i < pathExpenses.length ; i++){
                    if (pathExpenses[i].equals(categories)){
                        select=i;
                        break;
                    }
                }
                categories.setSelection(select);

            }
        }

        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                types = "Income";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                } else {
                    addincome.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                    addexp.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.white));
                    addexp.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.black));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatesNotes.this,
                            android.R.layout.simple_spinner_item, pathIncome);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);

                }
            }
        });
        addexp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                types = "Expenses";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(context, R.color.white));
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.white));


                } else {
                    addexp.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.white));
                    addincome.setBackground(ContextCompat.getDrawable(UpdatesNotes.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(UpdatesNotes.this, R.color.black));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatesNotes.this,
                            android.R.layout.simple_spinner_item, pathExpenses);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//сохранить запись обновления результата обработки данных
                dbcenter.updateTransactions(Integer.parseInt(id),date.getText().toString(), types, categories.getSelectedItem().toString(), Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),
                        Integer.parseInt(total.getText().toString()), description.getText().toString());
                Intent m = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(m);
            }
        });
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tvDateResult = (EditText) findViewById(R.id.date);
        btDatePicker = (Button) findViewById(R.id.calendar);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        camera = (Button) findViewById(R.id.btncamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //страница переместится в камеру
                startActivityForResult(i, versi);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (requestCode == REQUEST_IMAGE_CAPTURE) {
        //не сравнивайте данные с null, они всегда будут иметь значение null, потому что мы указываем URI файла, поэтому загружайте с помощью imageFilePath, который мы получили перед открытием cameraIntent

        // Если вы используете Glide.
        //}
        super.onActivityResult(requestCode, resultCode, data);
        if (this.versi == requestCode && resultCode == RESULT_OK) { //menampilkan hasil foto di imageview
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgnote.setImageBitmap(bitmap);
            imgnote.setMinimumHeight(170);
            imgnote.setMinimumWidth(200);

        }
    }
    private void showDateDialog() {

        /**
         * Календарь для получения текущей даты
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Запуск диалогового окна выбора даты
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Этот метод вызывается, когда мы заканчиваем выбирать дату в DatePicker
                 */

                /**
                 * Установите календарь для хранения выбранной даты
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Обновите TextView на выбранную нами дату
                 */
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Выбор даты в диалоговом окне Tampilkan
         */
        datePickerDialog.show();
    }
}