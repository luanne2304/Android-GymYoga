package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarVH> {

    Context context;
    ArrayList<Datetime> lich;
    LichCallback lichCallback;
    public void setCallback(LichCallback lichCallback) {
        this.lichCallback = lichCallback;
    }
    public CalendarAdapter (ArrayList<Datetime> lich,Context context) {
        this.context = context;
        this.lich = lich;
    }

    @NonNull
    @Override
    public CalendarVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemlich, parent,false);
        return new CalendarVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarVH holder, int position) {
        Datetime item=lich.get(position);
        String a = Integer.toString(item.getDate());
        String year= a.substring(0,4);
        String month= a.substring(4,6);
        String day= a.substring(6,8);
        String b= item.getTime();
        String h= b.substring(0,2);
        String m= b.substring(2,4);
        holder.tvshowdt.setText(year+"-"+month+"-"+day+" | "+h+" : "+m);

        holder.itemView.setOnClickListener(view -> lichCallback.onItemEditClicked(item,position));
        holder.imdelete.setOnClickListener(view -> lichCallback.onItemDeleteClicked(item,position));
    }

    @Override
    public int getItemCount() {
        return lich.size();
    }

    class CalendarVH extends RecyclerView.ViewHolder{
        ImageView imdelete;
        TextView tvshowdt;
        public CalendarVH(@NonNull View itemView) {
            super(itemView);
            imdelete=itemView.findViewById(R.id.ivdeletecalendar);
            tvshowdt=itemView.findViewById(R.id.tvshowcalendar);
        }
    }

    public  interface  LichCallback{
        void onItemEditClicked(Datetime dt,int position);
        void onItemDeleteClicked(Datetime dt, int position);
    }
}

