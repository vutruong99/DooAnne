package com.dooanne;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.model.Deck;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {


    private List<Deck> mDecks;
    private final LayoutInflater mInflater;

    public DeckAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    public static class DeckViewHolder extends RecyclerView.ViewHolder {
        TextView deckName;
        ImageView deckImage;
        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            deckName = itemView.findViewById(R.id.deckName);
            deckImage = itemView.findViewById(R.id.deckImage);
        }
    }


    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.deck_item, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        if (mDecks != null) {
            Deck deck = mDecks.get(position);
            holder.deckName.setText(deck.getName());
            Picasso.with(holder.deckImage.getContext()).load(deck.getImageLink()).into(holder.deckImage);
        } else {
            Log.i("DooAnne: Cards", "No Decks");
        }


    }

    public void setDecks(List<Deck> decks) {
        mDecks = decks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDecks != null)
            return mDecks.size();
        else return 0;
    }
}
