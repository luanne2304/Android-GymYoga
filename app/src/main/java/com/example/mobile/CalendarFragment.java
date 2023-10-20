package com.example.mobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;


public class CalendarFragment extends Fragment implements CalendarAdapter.LichCallback {
    FloatingActionButton fbAdd;
    CalendarAdapter calendarAdapter;
    DbHelper dbHelper;
    ArrayList<Datetime> lich;
    RecyclerView rvListC;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener setListenertime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fbAdd = view.findViewById(R.id.fbAdd);
        rvListC=view.findViewById(R.id.rvcalendar);
        lich =new ArrayList<>();
        dbHelper = new DbHelper(getContext());
        fbAdd.setOnClickListener(view1 -> addDialog());
        load();
    }

    void load(){
        calendarAdapter = new CalendarAdapter(lich,getContext());
        calendarAdapter.setCallback((CalendarAdapter.LichCallback)this);
        rvListC.setAdapter(calendarAdapter);
        rvListC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        initData();
    }

    void initData(){
        lich.clear();
        lich.addAll(dbHelper.getlich());
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemDeleteClicked(Datetime dt, int position) {
        dbHelper.deletelich(dt.getId());
        load();
    }

    @Override
    public void onItemEditClicked(Datetime dt, int position) {
        updateDialog(dt);
        load();
    }

    void addDialog() {
        //khởi tạo dialog để thêm người dùng

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalendarFragment.this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutdialogadd, null);
        alertDialog.setView(dialogView);
        TextView tvdate = (TextView) dialogView.findViewById(R.id.tvdate);
        TextView tvtime = (TextView) dialogView.findViewById(R.id.tvtime);
        String tempdate =tvdate.getText().toString();
        String temptime =tvtime.getText().toString();
        Calendar calendar =Calendar.getInstance();
        final int year =calendar.get(Calendar.YEAR);
        final int month =calendar.get(Calendar.MONTH);
        final int day =calendar.get(Calendar.DAY_OF_MONTH);
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(CalendarFragment.this.getContext(),
                        R.style.DatePickerTheme_luan,setListener,year,month,day);
                datePickerDialog.show();
            }
        });
        tvtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(CalendarFragment.this.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,setListenertime,0,0,true);
                timePickerDialog.show();
            }
        });

        setListener =new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                month=month+1;
                if(month<10){
                    if (dayofmonth<10){
                        String date=year+"/0"+month+"/0"+dayofmonth;
                        tvdate.setText(date);
                    }
                    else{
                        String date=year+"/0"+month+"/"+dayofmonth;
                        tvdate.setText(date);
                    }
                }
                else{
                    if (dayofmonth<10){
                        String date=year+"/"+month+"/0"+dayofmonth;
                        tvdate.setText(date);
                    }
                    else{
                        String date=year+"/"+month+"/"+dayofmonth;
                        tvdate.setText(date);
                    }
                }
            }
        };

        setListenertime =new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if(h<10){
                    if(m<10){
                        String time="0"+h+" : "+"0"+m;
                        tvtime.setText(time);
                    }
                    else {
                        String time="0"+h+" : "+m;
                        tvtime.setText(time);
                    }
                }
                else{
                    if(m<10){
                        String time=+h+" : "+"0"+m;
                        tvtime.setText(time);
                    }
                    else {
                        String time=h+" : "+m;
                        tvtime.setText(time);
                    }
                }
            }
        };
        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            if (tvdate.getText().toString() ==tempdate) {
                Toast.makeText(CalendarFragment.this.getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();}
            else if (tvtime.getText().toString()==temptime) {
                Toast.makeText(CalendarFragment.this.getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();}
            else {
                int a= Integer.parseInt(tvdate.getText().toString().replace("/",""));
                String b= tvtime.getText().toString().replace(" : ","");
                dbHelper.addlich(a,b);
                load();
                dialog.dismiss();
                }
            })
            ;
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void updateDialog(Datetime dt) {
        //khởi tạo dialog để thêm người dùng

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalendarFragment.this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutdialogadd, null);
        alertDialog.setView(dialogView);
        TextView tvdate = (TextView) dialogView.findViewById(R.id.tvdate);
        TextView tvtime = (TextView) dialogView.findViewById(R.id.tvtime);
        String ye= Integer.toString(dt.date).substring(0,4);
        String mo= Integer.toString(dt.date).substring(4,6);
        String da= Integer.toString(dt.date).substring(6,8);
        tvdate.setText(ye+"-"+mo+"-"+da);
        String h= dt.time.substring(0,2);
        String m= dt.time.substring(2,4);
        tvtime.setText(h+" : "+m);
        Calendar calendar =Calendar.getInstance();
        final int year =calendar.get(Calendar.YEAR);
        final int month =calendar.get(Calendar.MONTH);
        final int day =calendar.get(Calendar.DAY_OF_MONTH);
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(CalendarFragment.this.getContext(),
                        R.style.DatePickerTheme_luan,setListener,year,month,day);
                datePickerDialog.show();
            }
        });
        tvtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(CalendarFragment.this.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,setListenertime,0,0,true);
                timePickerDialog.show();
            }
        });

        setListener =new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                month=month+1;
                if(month<10){
                    if (dayofmonth<10){
                        String date=year+"-0"+month+"-0"+dayofmonth;
                        tvdate.setText(date);
                    }
                    else{
                        String date=year+"-0"+month+"-"+dayofmonth;
                        tvdate.setText(date);
                    }
                }
                else{
                    if (dayofmonth<10){
                        String date=year+"-"+month+"-0"+dayofmonth;
                        tvdate.setText(date);
                    }
                    else{
                        String date=year+"-"+month+"-"+dayofmonth;
                        tvdate.setText(date);
                    }
                }
            }
        };

        setListenertime =new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if(h<10){
                    if(m<10){
                        String time="0"+h+" : "+"0"+m;
                        tvtime.setText(time);
                    }
                    else {
                        String time="0"+h+" : "+m;
                        tvtime.setText(time);
                    }
                }
                else{
                    if(m<10){
                        String time=+h+" : "+"0"+m;
                        tvtime.setText(time);
                    }
                    else {
                        String time=h+" : "+m;
                        tvtime.setText(time);
                    }
                }
            }
        };
        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
                int a= Integer.parseInt(tvdate.getText().toString().replace("-",""));
                String b= tvtime.getText().toString().replace(" : ","");
                dbHelper.updatelich(dt,a,b);
                load();
                dialog.dismiss();

        })
        ;
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }
}