package com.example.financemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> rvId, rvTgl , rvKet,rvJml,rvJns;
    private Object Text;

    public MyAdapter(ArrayList<String> inputId,ArrayList<String> inputDate,ArrayList<String> inputDescription,ArrayList<String> inputTotal,ArrayList<String> inputTypes) {
        rvId = inputId;
        rvTgl = inputDate;
        rvKet = inputDescription;
        rvJml=inputTotal;
        rvJns=inputTypes;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvSubtitle;
        public  TextView tvJum;
        public  TextView tvJns;
        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.txttgl);
            tvSubtitle = (TextView) v.findViewById(R.id.txtket);
            tvJum = (TextView) v.findViewById(R.id.txtjml);
            tvJns = (TextView) v.findViewById(R.id.txtjns);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// создайте новый вид
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent,
                false);
// устанавливает размер представления, поля, отступы и другие параметры макета
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
// - извлекать элементы из набора данных (ArrayList) в определенном положении
// - устанавливает содержимое представления с элементами из этого набора данных
        final String name = rvTgl.get(position);
        holder.tvTitle.setText(rvTgl.get(position));
        holder.tvSubtitle.setText(rvKet.get(position));
        holder.tvJum.setText(rvJml.get(position));
        //types
        String Expenses = rvJns.get(position);
        holder.tvJns.setText(Expenses);
        String Hasil = "Expenses";
        if (Hasil.equals(Expenses)){
            holder.tvTitle.setBackgroundResource(R.drawable.tanggalan);
        }else{
            holder.tvTitle.setBackgroundResource(R.drawable.side_nav_bar);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),ViewNotes.class);
                i.putExtra("id",rvId.get(position));
                view.getContext().startActivity(i);
            }
        });
        //int[] i=new int[10];
    }
    @Override
    public int getItemCount() {
// вычисляет размер набора данных / объем данных, отображаемых в Recycler
        return rvTgl.size();
    }
}