package com.dooanne.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "deck_table")
public class Deck {

    @PrimaryKey
    @ColumnInfo(name= "deck_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name= "eng_name")
    private String engName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "eng_description")
    private String engDescription;

    @ColumnInfo(name = "imageName")
    private String imageName;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo (name = "isPremium")
    private boolean isPremium;

    @ColumnInfo (name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo (name = "isVietnamese")
    private boolean isVietnamese;

    @ColumnInfo (name = "cards")
    private ArrayList<String> cards;

    public Deck(int id, String name, String engName, String description, String engDescription, String imageName,
                String color, boolean isPremium, boolean isFavorite, boolean isVietnamese, ArrayList<String> cards) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.description = description;
        this.engDescription = engDescription;
        this.imageName = imageName;
        this.color = color;
        this.isPremium = isPremium;
        this.isFavorite = isFavorite;
        this.isVietnamese = isVietnamese;
        this.cards = cards;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isVietnamese() {
        return isVietnamese;
    }

    public void setVietnamese(boolean vietnamese) {
        isVietnamese = vietnamese;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngDescription() {
        return engDescription;
    }

    public void setEngDescription(String engDescription) {
        this.engDescription = engDescription;
    }
}
