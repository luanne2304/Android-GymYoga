package com.example.mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskyActivity extends AppCompatActivity implements TaskyAdapter.TaskyCallback {

    ImageView imBackC;
    RecyclerView rvListC;
    ArrayList<Task> lsttask;
    TaskyAdapter taskyAdapter;
    DbHelper dbHelper;
    int id;
    public TaskyActivity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasky);

        id = getIntent().getIntExtra("Id",0);

        imBackC=findViewById(R.id.ivtaskbacky);
        rvListC = findViewById(R.id.rvlisttasky);
        imBackC.setOnClickListener(view -> finish());
        dbHelper = new DbHelper(this);
        lsttask =new ArrayList<>();
        load();
    }
    void load(){
        taskyAdapter = new TaskyAdapter(lsttask,this);
        taskyAdapter.setCallback((TaskyAdapter.TaskyCallback)this);
        rvListC.setAdapter(taskyAdapter);
        rvListC.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initData(id);
    }
    void initData(int i){
        lsttask.clear();
        lsttask.addAll(dbHelper.getAllTaskyoga(i));
        taskyAdapter.notifyDataSetChanged();
    }

    void showguide(Task task,int position) {
        //khởi tạo dialog để cập nhật người dùng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TaskyActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutdialogguide, null);
        alertDialog.setView(dialogView);
        TextView Name = (TextView) dialogView.findViewById(R.id.tvguidetaskname);
        TextView Guide = (TextView) dialogView.findViewById(R.id.tvguide);
        //gán dữ liệu
        Name.setText(task.getName());
        Guide.setText(task.getDes());
        //
        alertDialog.setNegativeButton("Đóng", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();

    }



    @Override
    public void onItemTapClickedy(Task task, int position) {
        Intent i= new Intent(this, TapActivity.class);
        String[] a= {task.getName(),Integer.toString(task.getSec()),Integer.toString(task.getKcal()),task.getImage()};
        i.putExtra("task",a);
        startActivity(i);
    }

    @Override
    public void onItemViewClicky(Task task, int position) {
        showguide(task,position);
    }
    @Override
    public void onItemYeuthichClickedy(Task task, int position) {

        if(task.getFav()==0){
            dbHelper.updatelikeyoga(task.getId());
            load();
        }
        else{
            dbHelper.updateunlikeyoga(task.getId());
            load();
        }
    }

    //    @Override
//    public void onResume() {
//        if (dbHelper.getAllTaskgym(id).size() > 0){
//            initData(id);
//        }
//        super.onResume();
//    }


}