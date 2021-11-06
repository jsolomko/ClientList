package com.example.clientlist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_list")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "sec_name")
    private String sec_name;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "importance")
    private int importance;

    @ColumnInfo(name = "discription")
    private String discription;

    @ColumnInfo(name = "special")
    private int special;


    public Client(int id, String name, String sec_name, String number, int importance, String discription, int special) {
        this.id = id;
        this.name = name;
        this.sec_name = sec_name;
        this.number = number;
        this.importance = importance;
        this.discription = discription;
        this.special = special;
    }

    @Ignore
    public Client(String name, String sec_name, String number, int importance, String discription, int special) {
        this.name = name;
        this.sec_name = sec_name;
        this.number = number;
        this.importance = importance;
        this.discription = discription;
        this.special = special;
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

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
