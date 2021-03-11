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

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "imageLink")
    private int imageLink;

    @ColumnInfo (name = "isPremium")
    private boolean isPremium;

    @ColumnInfo (name = "downloads")
    private int downloads;

    @ColumnInfo (name = "cards")
    private ArrayList<String> cards;

    public Deck(int id, String name, String description, int imageLink, boolean isPremium, int downloads, ArrayList<String> cards) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.isPremium = isPremium;
        this.downloads = downloads;
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

    public void setImageLink(int image) {
        this.imageLink = image;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }
}
