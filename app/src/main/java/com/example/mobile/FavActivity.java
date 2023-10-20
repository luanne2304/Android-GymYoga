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

public class FavActivity extends AppCompatActivity implements TaskAdapter.TaskCallback, TaskyAdapter.TaskyCallback {

    ImageView imBackC;
    RecyclerView rvListgymC,rvListyoga;
    ArrayList<Task> lstgymtask,lstyogatask;
    TaskAdapter gymtaskAdapter;
    DbHelper dbHelper;
    TaskyAdapter yogataskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        imBackC=findViewById(R.id.ivbacklistyeuthich);
        imBackC.setOnClickListener(view -> finish());
        dbHelper = new DbHelper(this);
        rvListgymC = findViewById(R.id.rvlstgymyeuthich);
        rvListyoga = findViewById(R.id.rvlstyogayeuthich);
        lstgymtask =new ArrayList<>();
        lstyogatask =new ArrayList<>();

        loadgym();

        loadyoga();
    }

    void loadgym(){
        gymtaskAdapter = new TaskAdapter(lstgymtask,this);
        gymtaskAdapter.setCallback((TaskAdapter.TaskCallback)this);
        rvListgymC.setAdapter(gymtaskAdapter);
        rvListgymC.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initData();
    }

    void loadyoga(){
        yogataskAdapter = new TaskyAdapter(lstyogatask,this);
        yogataskAdapter.setCallback((TaskyAdapter.TaskyCallback)this);
        rvListyoga.setAdapter(yogataskAdapter);
        rvListyoga.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initData2();
    }

    void initData(){
        lstgymtask.clear();
        lstgymtask.addAll(dbHelper.getAllTaskgymFav());
        gymtaskAdapter.notifyDataSetChanged();
    }

    void initData2(){
        lstyogatask.clear();
        lstyogatask.addAll(dbHelper.getAllTaskyogaFav());
        yogataskAdapter.notifyDataSetChanged();
    }

    void showguide(Task task,int position) {
        //khởi tạo dialog để cập nhật người dùng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavActivity.this);
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
            loadgym();
        }
        else{
            dbHelper.updateunlikegym(task.getId());
            loadgym();
        }
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
            loadyoga();
        }
        else{
            dbHelper.updateunlikeyoga(task.getId());
            loadyoga();
        }
    }

}