package com.dooanne.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "custom_deck_table")
public class CustomDeck {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "deck_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "imageLink")
    private int imageLink;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo (name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo (name = "cards")
    private ArrayList<String> cards;

    public CustomDeck(int id, String name, String description, int imageLink, String color, boolean isFavorite, ArrayList<String> cards) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.color = color;
        this.isFavorite = isFavorite;
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

    public int getImageLink() {
        return imageLink;
    }

    public void setImageLink(int imageLink) {
        this.imageLink = imageLink;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }
}


