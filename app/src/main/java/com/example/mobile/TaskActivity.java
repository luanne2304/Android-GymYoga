package com.example.mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity implements TaskAdapter.TaskCallback {
    TextView tvDetailC;
    ImageView imBackC;
    RecyclerView rvListC;
    ArrayList<Task> lsttask;
    TaskAdapter taskAdapter;
    DbHelper dbHelper;
    int id;

    public TaskActivity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        id = getIntent().getIntExtra("Id",0);
        imBackC=findViewById(R.id.ivtaskback);
        rvListC = findViewById(R.id.rvlisttask);
        imBackC.setOnClickListener(view -> finish());
        dbHelper = new DbHelper(this);
        lsttask =new ArrayList<>();
        load();
    }

    void load(){
        taskAdapter = new TaskAdapter(lsttask,this);
        taskAdapter.setCallback((TaskAdapter.TaskCallback)this);
        rvListC.setAdapter(taskAdapter);
        rvListC.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initData(id);
    }

    void initData(int i){
        lsttask.clear();
        lsttask.addAll(dbHelper.getAllTaskgym(i));
        taskAdapter.notifyDataSetChanged();
    }

    void showguide(Task task,int position) {
        //khởi tạo dialog để cập nhật người dùng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TaskActivity.this);
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
    public void onItemTapClicked(Task task, int position) {
        Intent i= new Intent(this, TapActivity.class);
        String[] a= {task.getName(),Integer.toString(task.getSec()),Integer.toString(task.getKcal()),task.getImage()};
        i.putExtra("task",a);
        startActivity(i);
    }

    @Override
    public void onItemViewClick(Task task, int position) {
        showguide(task,position);
    }
    @Override
    public void onItemYeuthichClicked(Task task, int position) {

        if(task.getFav()==0){
            dbHelper.updatelikegym(task.getId());
            load();
        }
        else{
            dbHelper.updateunlikegym(task.getId());
            load();
        }
    }

//        @Override
//    public void onResume() {
//        if (dbHelper.getAllTaskgym(id).size() > 0){
//            initData(id);
//        }
//        super.onResume();
//    }


}