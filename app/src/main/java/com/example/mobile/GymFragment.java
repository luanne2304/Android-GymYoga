package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GymFragment extends Fragment implements GrAdapter.GrCallback {
    RecyclerView rvListC;
    ArrayList<Gr> lstGr;
    GrAdapter grAdapter;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_gym, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DbHelper(getContext());
        rvListC = view.findViewById(R.id.rvlistgr);
        lstGr =new ArrayList<>();
        grAdapter = new GrAdapter(lstGr,getContext());
        grAdapter.setCallback((GrAdapter.GrCallback)this);
        rvListC.setAdapter(grAdapter);
        rvListC.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        initData();
    }

    void initData(){
        lstGr.clear();
        lstGr.addAll(dbHelper.getAllgrgym());
        grAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        if (dbHelper.getAllgrgym().size() > 0){
            initData();
        }
        super.onResume();
    }

    @Override
    public void onItemClicked(int id) {
        Intent i= new Intent(getContext(), TaskActivity.class);
            i.putExtra("Id",id);
        startActivity(i);
    }

}