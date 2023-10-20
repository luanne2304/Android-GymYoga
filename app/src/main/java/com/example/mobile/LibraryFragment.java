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

import java.util.ArrayList;

public class LibraryFragment extends Fragment implements LibAdapter.LibCallback {

    RecyclerView rvListC;
    ArrayList<Lib> lstFav;
    LibAdapter libAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvListC = view.findViewById(R.id.rvlib);
        lstFav =new ArrayList<>();
        load();
        libAdapter = new LibAdapter(lstFav,getContext());
        libAdapter.setCallback((LibAdapter.LibCallback)this);
        rvListC.setAdapter(libAdapter);
        rvListC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    void load(){
        lstFav.add(new Lib(1,"","favwall.png"));
        lstFav.add(new Lib(2,"Drink","waterwall.png"));
        lstFav.add(new Lib(3,"Kcal","kcalwall.png"));
    }

    @Override
    public void onItemClicked(Lib lib) {
        if(lib.getId() == 1){
            Intent intent = new Intent(getContext(),FavActivity.class);
            startActivity(intent);
        }
        if(lib.getId() == 2){
            Intent intent = new Intent(getContext(),DrinkActivity.class);
            startActivity(intent);
        }
        if(lib.getId() == 3){
            Intent intent = new Intent(getContext(),KcalActivity.class);
            startActivity(intent);
        }
    }
}