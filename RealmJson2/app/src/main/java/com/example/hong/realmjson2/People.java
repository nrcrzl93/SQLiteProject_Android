package com.example.hong.realmjson2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hong on 2017-02-13.
 */

public class People extends RealmObject {

    @PrimaryKey
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
