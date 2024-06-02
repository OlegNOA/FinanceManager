package com.example.financemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph extends AppCompatActivity {
    DatabaseHelper dbcenter;
    SessionManagement sessionManagement;
    Button back;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        back = findViewById(R.id.back);
        PieChart pieChart = findViewById(R.id.pieChart);
        dbcenter = new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {//подайте команду, если нажать кнопку "Назад", она перейдет на страницу меню
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class); //перейти на страницу меню
                startActivity(i);

            }
        });

        ArrayList<PieEntry> visitors = new ArrayList<>();
        sessionManagement = new SessionManagement(Graph.this);
        if (sessionManagement.isLoggedIn()) {
            final HashMap<String, String> user = sessionManagement.getUserInformation();

            SQLiteDatabase db = dbcenter.getReadableDatabase();
            cursor = db.rawQuery("SELECT SUM(total) as total, categories FROM transactions WHERE id_user = '" + user.get(sessionManagement.KEY_ID_USER) + "' and types = 'Income' group by categories", null);
            cursor.moveToFirst();

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                int total = Integer.parseInt(cursor.getString(0).toString());
                String categories = cursor.getString(1).toString();
                //чтобы сделать цвета разными, создайте if else таким образом, чтобы он имел разный цвет для каждого индекса курсора
                if(cc == 0){
                    visitors.add(new PieEntry(total, categories));
                }else if (cc == 1){
                    visitors.add(new PieEntry(total, categories));
                }else if(cc == 2){
                    visitors.add(new PieEntry(total, categories));
                }else if(cc == 3){
                    visitors.add(new PieEntry(total, categories));
                }else if(cc == 4){
                    visitors.add(new PieEntry(total, categories));
                }
            }
        }

        PieDataSet pieDataSet = new PieDataSet(visitors, "INCOME GRAPHIC");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("INCOME GRAPHIC BAR");
        pieChart.animate();
    }
}