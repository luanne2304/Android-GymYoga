package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibAdapter extends RecyclerView.Adapter<LibAdapter.LibVH> {
    Context context;
    ArrayList<Lib> lstLib;
    LibCallback libCallback;
    public void setCallback(LibCallback libCallback) {
        this.libCallback = libCallback;
    }

    public LibAdapter(ArrayList<Lib> lstLib,Context context) {
        this.lstLib = lstLib;
        this.context=context;
    }

    @NonNull
    @Override
    public LibVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //nạp layout vào cho View biểu diễn phần tử user
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemgr, parent,false);
        return new LibVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibVH holder, int position) {
        //lấy từng item của dữ liệu
        Lib item =lstLib.get(position);
        //gán vào item của view
        holder.imImage.setImageBitmap(Utils.convertToBitmapFromAssets(context, item.getImage()));
        holder.tvName.setText(item.getName());
        //bat su kien
        holder.itemView.setOnClickListener(view -> libCallback.onItemClicked(item));
    }

    @Override
    public int getItemCount() {
        return lstLib.size();
    }

    class LibVH extends RecyclerView.ViewHolder{
        ImageView imImage;
        TextView tvName;
        public LibVH(@NonNull View itemView) {
            super(itemView);
            imImage=itemView.findViewById(R.id.ivImagegr);
            tvName=itemView.findViewById(R.id.tvNamegr);
        }
    }

    public  interface  LibCallback{
        void onItemClicked(Lib lib);
    }
}
