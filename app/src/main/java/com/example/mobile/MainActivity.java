package com.example.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    GymFragment gymFragment= new GymFragment();
    YogaFragment yogaFragment= new YogaFragment();
    LibraryFragment libraryFragment=new LibraryFragment();
    CalendarFragment calendarFragment= new CalendarFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,gymFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gym:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,gymFragment).commit();
                        return true;
                    case R.id.yoga:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,yogaFragment).commit();
                        return true;
                    case R.id.library:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,libraryFragment).commit();
                        return true;
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,calendarFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}