package com.example.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbHelper {
    String DB_Name = "mobile6.db";
    String tblgymtask="Gymtask";
    String tblgymgr="Gymgr";

    Context context;

    public DbHelper(Context context){
        this.context = context;
    }

    public SQLiteDatabase openDB(){
        File dbFile = context.getDatabasePath(DB_Name);
        if(!dbFile.exists()){
            try{
                copyDatabase(dbFile);
            } catch (Exception e) {
                throw new RuntimeException("had creating source database",e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }

    public void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_Name);
        OutputStream os = new FileOutputStream(dbFile);
        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }
        os.flush();
        os.close();
        is.close();
    }

    //lấy danh sách
    public ArrayList<Gr> getAllgrgym()
    {
        ArrayList<Gr> lstGr = new ArrayList<>();
        SQLiteDatabase db = openDB();
        Cursor cs = db.rawQuery("Select * from "+tblgymgr, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String image = cs.getString(2);
            lstGr.add(new Gr(id, name, image));

        }
        cs.close();
        db.close();
        return lstGr;
    }

    public ArrayList<Gr> getAllgryoga()
    {
        ArrayList<Gr> lstGr = new ArrayList<>();
        SQLiteDatabase db = openDB();
        Cursor cs = db.rawQuery("Select * from Yogagr", null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String image = cs.getString(2);
            lstGr.add(new Gr(id, name, image));
        }
        cs.close();
        db.close();
        return lstGr;
    }

    public ArrayList<Task> getAllTaskgym(int i)
    {
        ArrayList<Task> lstTask = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql ="Select * from Gymtask where idgr= "+i;
        Cursor cs = db.rawQuery(sql, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int sec= cs.getInt(2);
            int kcal=cs.getInt(3);
            String des=cs.getString(4);
            String image =cs.getString(5);
            int idgr=cs.getInt(6);
            int fav=cs.getInt(7);
            lstTask.add(new Task(id,name,sec,kcal,des,image,idgr,fav));
        }
        cs.close();
        db.close();
        return lstTask;
    }

    public ArrayList<Task> getAllTaskyoga(int i)
    {
        ArrayList<Task> lstTask = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql ="Select * from Yogatask where idgr= "+i;
        Cursor cs = db.rawQuery(sql, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int sec= cs.getInt(2);
            int kcal=cs.getInt(3);
            String des=cs.getString(4);
            String image =cs.getString(5);
            int idgr=cs.getInt(6);
            int fav=cs.getInt(7);
            lstTask.add(new Task(id,name,sec,kcal,des,image,idgr,fav));
        }
        cs.close();
        db.close();
        return lstTask;
    }

    public ArrayList<Task> getAllTaskgymFav()
    {
        ArrayList<Task> lstTask = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql ="Select * from Gymtask where Fav=1";
        Cursor cs = db.rawQuery(sql,null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int sec= cs.getInt(2);
            int kcal=cs.getInt(3);
            String des=cs.getString(4);
            String image =cs.getString(5);
            int idgr=cs.getInt(6);
            int fav=cs.getInt(7);
            lstTask.add(new Task(id,name,sec,kcal,des,image,idgr,fav));
        }
        cs.close();
        db.close();
        return lstTask;
    }

    public ArrayList<Task> getAllTaskyogaFav()
    {
        ArrayList<Task> lstTask = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql ="Select * from Yogatask where Fav=1";
        Cursor cs = db.rawQuery(sql,null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int sec= cs.getInt(2);
            int kcal=cs.getInt(3);
            String des=cs.getString(4);
            String image =cs.getString(5);
            int idgr=cs.getInt(6);
            int fav=cs.getInt(7);
            lstTask.add(new Task(id,name,sec,kcal,des,image,idgr,fav));
        }
        cs.close();
        db.close();
        return lstTask;
    }

    public Uongkcal getUongkcal(){
        Uongkcal uk;
        SQLiteDatabase db = openDB();
        String sql ="Select * from Uongkcal";
        Cursor cs = db.rawQuery(sql,null);
        cs.moveToFirst();
            int Canuong = cs.getInt(0);
            int Dauong= cs.getInt(1);
            int Cantap=cs.getInt(2);
            int Datap=cs.getInt(3);
            uk = new Uongkcal(Canuong,Dauong,Cantap,Datap);
        cs.close();
        db.close();
        return uk;
    }

    public int updatecanuong(int canuong)
    {
        SQLiteDatabase db = openDB();
        ContentValues values = new ContentValues();
        values.put("Canuong",canuong);
        int rs = db.update("Uongkcal",values,null, null);
        db.close();
        return (rs);
    }

    public void updatedauong(int vuauong)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Uongkcal set Dauong=Dauong+"+vuauong;
        db.execSQL(sql);
        db.close();
    }

    public int updatecantap(int cantap)
    {
        SQLiteDatabase db = openDB();
        ContentValues values = new ContentValues();
        values.put("Cantap",cantap);
        int rs = db.update("Uongkcal",values,null, null);
        db.close();
        return (rs);
    }

    public void updatedatap(int vuatap)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Uongkcal set Datap=Datap+"+vuatap;
        db.execSQL(sql);
        db.close();
    }

    public void updatelikegym(int like)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Gymtask set Fav=1 where Id="+like;
        db.execSQL(sql);
        db.close();
    }
    public void updateunlikegym(int unlike)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Gymtask set Fav=0 where Id="+unlike;
        db.execSQL(sql);
        db.close();
    }

    public void updatelikeyoga(int like)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Yogatask set Fav=1 where Id="+like;
        db.execSQL(sql);
        db.close();
    }
    public void updateunlikeyoga(int unlike)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Yogatask set Fav=0 where Id="+unlike;
        db.execSQL(sql);
        db.close();
    }

    public void rsdrink()
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Uongkcal set Dauong=0";
        db.execSQL(sql);
        db.close();
    }
    public void rskcal()
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Uongkcal set Datap=0";
        db.execSQL(sql);
        db.close();
    }
    public ArrayList<Datetime> getlich()
    {
        ArrayList<Datetime> lich = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql ="Select * from Lich ORDER BY Date asc";
        Cursor cs = db.rawQuery(sql, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            int date = cs.getInt(1);
            String time = cs.getString(2);
            lich.add(new Datetime(id,date,time));
        }
        cs.close();
        db.close();
        return lich;
    }
    public void addlich(int date,String time)
    {
        SQLiteDatabase db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);
        contentValues.put("Time", time);
        db.insert("Lich",null,contentValues);
        db.close();
    }
    public void deletelich(int id)
    {
        SQLiteDatabase db = openDB();
        String sql ="Delete From Lich where Id="+id;
        db.execSQL(sql);
        db.close();
    }
    public void updatelich(Datetime dt,int a, String b)
    {
        SQLiteDatabase db = openDB();
        String sql ="Update Lich set Date="+a+", Time='"+b+"' where Id="+dt.id;
        db.execSQL(sql);
        db.close();
    }
}
