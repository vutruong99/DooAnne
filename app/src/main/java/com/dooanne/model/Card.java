package com.dooanne.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {
    @PrimaryKey
    private int card_id;
    private int deck_id;
    private String content;

    public Card(int card_id, int deck_id, String content) {
        this.card_id = card_id;
        this.deck_id = deck_id;
        this.content = content;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(int deck_id) {
        this.deck_id = deck_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
