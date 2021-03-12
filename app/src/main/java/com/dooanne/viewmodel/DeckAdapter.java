package com.dooanne.viewmodel;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.R;
import com.dooanne.model.Deck;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {


    private List<Deck> mDecks;
    private final LayoutInflater mInflater;


    private ItemClickListener mItemClickListener;
    public interface ItemClickListener {
        void onClick(Deck deck);
    }


    public DeckAdapter(Context context, ItemClickListener itemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mItemClickListener = itemClickListener;
    }

    class DeckViewHolder extends RecyclerView.ViewHolder {
        TextView deckName;
        ImageView deckImage;
        LinearLayout deckView;
        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            deckName = itemView.findViewById(R.id.deckName);
            deckImage = itemView.findViewById(R.id.deckImage);
            deckView = itemView.findViewById(R.id.deck_view);

            View.OnClickListener itemViewClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onClick(mDecks.get(getAdapterPosition()));
                }
            };
            itemView.setOnClickListener(itemViewClick);
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
            holder.deckView.setBackgroundColor(Color.parseColor(deck.getColor()));
            Picasso.get().load(deck.getImageLink()).into(holder.deckImage);
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
