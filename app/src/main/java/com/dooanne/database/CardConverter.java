package com.dooanne.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardConverter {
    @TypeConverter
    public static ArrayList<String> fromString(String listOfCards) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(listOfCards, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
