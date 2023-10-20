package com.example.mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;

public class DrinkActivity extends AppCompatActivity {
    TextView tvCanuongC;
    TextView tvDauongC;
    TextView tvThongbaoC;
    EditText etnhapnuocC;
    Button btnnhapC,btnrs;
    Uongkcal uk;
    DbHelper dbHelper;
    ImageView ivbackdrink;
    ImageView ivshowdialognhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        tvCanuongC=findViewById(R.id.tvcanuong);
        tvDauongC=findViewById(R.id.tvdauong);
        tvThongbaoC=findViewById(R.id.tvnotiuong);
        etnhapnuocC=findViewById(R.id.eduong);
        btnnhapC=findViewById(R.id.btnnhapdrink);
        ivshowdialognhap=findViewById(R.id.ivclickeditdrink);
        ivbackdrink=findViewById(R.id.ivdrinkback);
        btnrs=findViewById(R.id.btnrsdrink);
        btnrs.setOnClickListener(view -> rsdrink());
        ivbackdrink.setOnClickListener(view -> finish());
        dbHelper = new DbHelper(this);
        load();

    }

    void rsdrink(){
        dbHelper.rsdrink();
        load();
    }

    void load(){
        uk=dbHelper.getUongkcal();
        tvCanuongC.setText(Integer.toString(uk.getCanuong()));
        tvDauongC.setText(Integer.toString(uk.getDauong()));
        ivshowdialognhap.setOnClickListener(view -> showdialog(uk));
        btnnhapC.setOnClickListener((view -> nhapml(etnhapnuocC.getText().toString().trim())));
        if(uk.getCanuong()>uk.getDauong()){
            tvThongbaoC.setText("Bạn cần thêm nước\n Ọc Ọc!");
        }
        else {
            tvThongbaoC.setText("Hôm nay bạn uống đủ rồi\n Ọc Ọc!");
        }

    }

    void nhapml(String str ){
        if (isNumeric(str)&& !str.isEmpty() ) {
            if(Integer.parseInt(str)>0)
            {   int i=Integer.parseInt(str);
                dbHelper.updatedauong(i);
                load();
            }
            else{
                Toast.makeText(DrinkActivity.this, "Nhập lớn hơn 0", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(DrinkActivity.this, "Vui lòng nhập số", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    void showdialog(Uongkcal uk){
        //khởi tạo dialog để cập nhật người dùng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DrinkActivity.this);
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutdialogdrink, null);
        alertDialog.setView(dialogView);
        EditText etnhapdrinkdialog = (EditText) dialogView.findViewById(R.id.etnhapdialog);
        //gán dữ liệu
        etnhapdrinkdialog.setText(Integer.toString(uk.getCanuong()));
        //
        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
        String check=etnhapdrinkdialog.getText().toString().trim();
            if (isNumeric(check)&& !check.isEmpty() ) {
                if(Integer.parseInt(etnhapdrinkdialog.getText().toString())>0)
                {   int i=Integer.parseInt(etnhapdrinkdialog.getText().toString());
                    int result = dbHelper.updatecanuong(i);
                    if(result>0){
                        load();
                    }
                    else {
                        Toast.makeText(DrinkActivity.this, "Thất bại.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(DrinkActivity.this, "Nhập lớn hơn 0", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(DrinkActivity.this, "Vui lòng nhập số", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }


}