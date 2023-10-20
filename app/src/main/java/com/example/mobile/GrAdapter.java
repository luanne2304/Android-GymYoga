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

public class GrAdapter extends RecyclerView.Adapter<GrAdapter.GrVH> {
    Context context;
    ArrayList<Gr> lstGr;
    GrCallback grCallback;
    public void setCallback(GrCallback grCallback) {
        this.grCallback = grCallback;
    }

    public GrAdapter(ArrayList<Gr> lstGr,Context context) {
        this.lstGr = lstGr;
        this.context=context;
    }

    @NonNull
    @Override
    public GrVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //nạp layout vào cho View biểu diễn phần tử user
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemgr, parent,false);
        return new GrVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrVH holder, int position) {
        //lấy từng item của dữ liệu
        Gr item =lstGr.get(position);
        //gán vào item của view
        holder.imImage.setImageBitmap(Utils.convertToBitmapFromAssets(context, item.getImage()));
        holder.tvName.setText(item.getName());
        //bat su kien
        holder.itemView.setOnClickListener(view -> grCallback.onItemClicked(item.getId()));
    }

    @Override
    public int getItemCount() {
        return lstGr.size();
    }

    class GrVH extends RecyclerView.ViewHolder{
        ImageView imImage;
        TextView tvName;
        public GrVH(@NonNull View itemView) {
            super(itemView);
            imImage=itemView.findViewById(R.id.ivImagegr);
            tvName=itemView.findViewById(R.id.tvNamegr);
        }
    }

    public  interface  GrCallback{
        void onItemClicked(int i);
    }
}
