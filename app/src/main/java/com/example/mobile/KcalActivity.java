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

public class KcalActivity extends AppCompatActivity {

    TextView tvCangiamC;
    TextView tvDagiamC;
    TextView tvThongbaoC;
    Uongkcal uk;
    DbHelper dbHelper;
    ImageView ivback;
    ImageView ivshowdialognhap;
    Button btnrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcal);

        tvCangiamC=findViewById(R.id.tvcantap);
        tvDagiamC=findViewById(R.id.tvdatap);
        tvThongbaoC=findViewById(R.id.tvnotikcal);
        ivshowdialognhap=findViewById(R.id.ivclickeditkcal);
        ivback=findViewById(R.id.ivKcalback);
        btnrs=findViewById(R.id.btnrskcal);
        ivback.setOnClickListener(view -> finish());
        btnrs.setOnClickListener(view -> rskcal());
        dbHelper = new DbHelper(this);
        load();
    }

    void rskcal(){
        dbHelper.rskcal();
        load();
    }

    void load(){
        uk=dbHelper.getUongkcal();
        tvCangiamC.setText(Integer.toString(uk.getCantap()));
        tvDagiamC.setText(Integer.toString(uk.getDatap()));
        ivshowdialognhap.setOnClickListener(view -> showdialog(uk));
        if(uk.getCantap()>uk.getDatap()){
            tvThongbaoC.setText("Bạn cần tập luyện thêm\n Fighting!");
        }
        else {
            tvThongbaoC.setText("Hôm nay bạn tập đủ rồi\n Nghỉ ngơi thoi!");
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(KcalActivity.this);
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutdialogkcal, null);
        alertDialog.setView(dialogView);
        EditText etnhapkcaldialog = (EditText) dialogView.findViewById(R.id.etnhapdialogkcal);
        //gán dữ liệu
        etnhapkcaldialog.setText(Integer.toString(uk.getCantap()));
        //
        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String check=etnhapkcaldialog.getText().toString().trim();
            if (isNumeric(check)&& !check.isEmpty() ) {
                if(Integer.parseInt(etnhapkcaldialog.getText().toString())>0)
                {   int i=Integer.parseInt(etnhapkcaldialog.getText().toString());
                    int result = dbHelper.updatecantap(i);
                    if(result>0){
                        load();
                    }
                    else {
                        Toast.makeText(KcalActivity.this, "Thất bại.", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(KcalActivity.this, "Nhập lớn hơn 0", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(KcalActivity.this, "Vui lòng nhập số", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

}