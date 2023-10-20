package com.example.mobile;

public class Task {
    int id;
    String name;
    int sec;
    int kcal;
    String des;
    String image;
    int idgr;
    int fav;

    public Task(int id, String name, int sec, int kcal, String des, String image, int idgr, int fav) {
        this.id = id;
        this.name = name;
        this.sec = sec;
        this.kcal = kcal;
        this.des = des;
        this.image = image;
        this.idgr = idgr;
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdgr() {
        return idgr;
    }

    public void setIdgr(int idgr) {
        this.idgr = idgr;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
}
