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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {

    Context context;
    ArrayList<Task> lstTask;
    TaskCallback taskCallback;
    public void setCallback(TaskCallback taskCallback) {
        this.taskCallback = taskCallback;
    }
    public TaskAdapter (ArrayList<Task> lstTask,Context context) {
        this.context = context;
        this.lstTask = lstTask;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemtask, parent,false);
        return new TaskVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {
        Task item=lstTask.get(position);

        holder.imImage.setImageBitmap(Utils.convertToBitmapFromAssets(context, item.getImage()));
        holder.tvName.setText(item.getName());
        holder.tvSec.setText(Integer.toString(item.getSec()));
        holder.tvKcal.setText(Integer.toString(item.getKcal()));
        if(item.getFav()==1){
            holder.btnYeuthich.setText("Bỏ yêu thích");
        }
        holder.itemView.setOnClickListener(view -> taskCallback.onItemViewClick(item,position));
        holder.btnYeuthich.setOnClickListener(view -> taskCallback.onItemYeuthichClicked(item,position));
        holder.btnTap.setOnClickListener(view -> taskCallback.onItemTapClicked(item,position));
    }

    @Override
    public int getItemCount() {
        return lstTask.size();
    }

    class TaskVH extends RecyclerView.ViewHolder{
        ImageView imImage;
        TextView tvName;
        TextView tvSec;
        TextView tvKcal;
        Button btnTap;
        Button btnYeuthich;
        public TaskVH(@NonNull View itemView) {
            super(itemView);
            imImage=itemView.findViewById(R.id.ivTask);
            tvName=itemView.findViewById(R.id.tvTenbaitap);
            tvSec=itemView.findViewById(R.id.tvThoigiantap);
            tvKcal=itemView.findViewById(R.id.tvKcaltap);
            btnTap=itemView.findViewById(R.id.btnTap);
            btnYeuthich=itemView.findViewById(R.id.btnYeuthich);
        }
    }

    public  interface  TaskCallback{
        void onItemViewClick(Task task,int position);
        void onItemYeuthichClicked(Task task, int position);
        void onItemTapClicked(Task task, int position);
    }
}
