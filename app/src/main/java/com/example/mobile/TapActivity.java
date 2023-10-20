package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TapActivity extends AppCompatActivity {
    TextView tvdemnguocC;
    Boolean isActive=false;
    Button btnGoC;
    CountDownTimer countDownTimer;
    String[] task;
    TextView tvNametap;
    ImageView imImagetap;
    ImageView ivBacktap;
    DbHelper dbHelper;

    public  void rsTimeer(int secdefault){
        int min=secdefault/60;
        int sec=secdefault-(min*60);
        String secStr=Integer.toString(sec);
        if(sec<=0){
            secStr="0"+secStr;
        }
        tvdemnguocC.setText(Integer.toString(min)+":"+secStr);
        countDownTimer.cancel();
        btnGoC.setText("Go!");
        isActive=false;
    }

    public void loadTimeer(int secdefault){
        int min=secdefault/60;
        int sec=secdefault-(min*60);
        String secStr=Integer.toString(sec);
        if(sec<=0){
            secStr="0"+secStr;
        }
        tvdemnguocC.setText(Integer.toString(min)+":"+secStr);
        btnGoC.setText("Go!");
        isActive=false;
    }

    private void btnGOclick(String sec){
        if(isActive){
            rsTimeer(Integer.parseInt(sec));
        }
        else{
            isActive=true;
            btnGoC.setText("STOP!");
            int a= Integer.parseInt(sec);
            countDownTimer=new CountDownTimer(a*1000+100,1000) {
                @Override
                public void onTick(long l) {
                    updatetimer((int)l/1000);
                }

                @Override
                public void onFinish() {
                    dbHelper.updatedatap(Integer.parseInt(task[2]));
                    rsTimeer(Integer.parseInt(sec));
                }
            }.start();
        }
    }

    public void updatetimer(int secleft){
        int min=secleft/60;
        int sec=secleft-(min*60);
        String secStr=Integer.toString(sec);
        if(sec<=0){
            secStr="0"+secStr;
        }
        tvdemnguocC.setText(Integer.toString(min)+":"+secStr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        dbHelper = new DbHelper(this);
        task=getIntent().getStringArrayExtra("task");
        tvdemnguocC=findViewById(R.id.tvDemnguoc);
        btnGoC=findViewById(R.id.btnGo);
        imImagetap=findViewById(R.id.ivTapimage);
        ivBacktap=findViewById(R.id.ivTapback);
        tvNametap=findViewById(R.id.tvTapname);
        tvNametap.setText(task[0]);
        imImagetap.setImageBitmap(Utils.convertToBitmapFromAssets(this,task[3]));
        loadTimeer(Integer.parseInt(task[1]));
        btnGoC.setOnClickListener(v-> btnGOclick(task[1]));
        ivBacktap.setOnClickListener(view -> finish());
    }

}