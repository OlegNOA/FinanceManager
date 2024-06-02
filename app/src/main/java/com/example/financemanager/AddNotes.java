package com.example.financemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddNotes extends AppCompatActivity {

    private Uri UrlGambar;
    Button addincome, addexp, save, camera;
    Context context;
    Spinner categories;
    ImageView imgnote;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tvDateResult;
    private Button btDatePicker;
    EditText date, description, total;
    String mCurrentPhotoPath;
    String types = "Income";
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;


    private static final String[] pathIncome = {"Salary", "Bonus", "Allowance", "Petty cash", "Other"};
    private static final String[] pathExpenses = {"Food", "Social Life", "Transportation", "Gift", "Healt", "Other"};
    private final int versi = 1;
    private static final int CAMERA = 1;
    private static final int FILE = 2;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        sessionManagement = new SessionManagement(AddNotes.this);
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        dbcenter = new DatabaseHelper(this);
        addincome = (Button) findViewById(R.id.addincome);
        addexp = (Button) findViewById(R.id.addexp);
        imgnote = (ImageView) findViewById(R.id.imgnote);
        date = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.edtnote);
        total = (EditText) findViewById(R.id.edtjumlah);
        categories = findViewById(R.id.spinnerCategory);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                types = "Income";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(context, R.color.white));
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.white));
                } else {
                    addincome.setBackground(ContextCompat.getDrawable(AddNotes.this, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(AddNotes.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(AddNotes.this, R.color.white));
                    addexp.setBackground(ContextCompat.getDrawable(AddNotes.this, R.color.white));
                    addexp.setTextColor(ContextCompat.getColor(AddNotes.this, R.color.black));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNotes.this,
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
                    addexp.setBackground(ContextCompat.getDrawable(AddNotes.this, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(AddNotes.this, R.color.white));
                    addincome.setBackground(ContextCompat.getDrawable(AddNotes.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(AddNotes.this, R.color.black));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNotes.this,
                            android.R.layout.simple_spinner_item, pathExpenses);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);

                }
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


        save = (Button) findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbcenter.addTransactions(date.getText().toString(), types, categories.getSelectedItem().toString(), Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),
                        Integer.parseInt(total.getText().toString()),description.getText().toString());
                Intent m = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(m);

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

        // Если вы используете Glide..
        //}

        super.onActivityResult(requestCode, resultCode, data);
        if (this.versi == requestCode && resultCode == RESULT_OK) { //если
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgnote.setImageBitmap(bitmap);
            imgnote.setMinimumHeight(170);
            imgnote.setMinimumWidth(200);



            //  Glide.with(this).load(imageFilePath).into(mImageView);
        }
    }

    private void showDateDialog() {

        /**
         * Календарь для получения текущей даты
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Инициировать диалоговое окно выбора даты
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Сохранить файл: путь для использования с намерениями ACTION_VIEW
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Убедитесь, что есть действие камеры, позволяющее справиться с намерением
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // При создании файла произошла ошибка

            }
            // Продолжайте только в том случае, если файл был успешно создан
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.malakdianadewi.moneymanager",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        // Получите размеры вида
        int targetW = imgnote.getWidth();
        int targetH = imgnote.getHeight();

        // Получите размеры растрового изображения
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Определите, насколько сильно следует уменьшить изображение
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Расшифруйте файл изображения в растровое изображение такого размера, чтобы оно заполняло весь вид
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgnote.setImageBitmap(bitmap);
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}