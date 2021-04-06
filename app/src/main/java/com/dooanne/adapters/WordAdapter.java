package com.dooanne.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.R;
import com.dooanne.model.Word;
import com.mikhaellopez.circleview.CircleView;

import java.util.List;


public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private Context context;
    private List<Word> mWords;
    private final LayoutInflater mInflater;


    private int checkedPosition = 5;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public WordAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        ImageView deleteWordButton;
        public WordViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            word = itemView.findViewById(R.id.wordContent);
            deleteWordButton = itemView.findViewById(R.id.deleteWord);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
               }
           });

           deleteWordButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener != null) {
                       int position = getAdapterPosition();
                       if (position != RecyclerView.NO_POSITION) {
                           listener.onDeleteClick(position);
                       }
                   }
               }
           });
        }

    }


    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word word = mWords.get(position);

            holder.word.setText(word.getContent());

        } else {
            Log.i("DooAnne: Colors", "No Colors");
        }


    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}
