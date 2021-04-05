package com.dooanne.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.R;
import com.dooanne.model.CustomColor;
import com.dooanne.model.Icon;
import com.mikhaellopez.circleview.CircleView;

import java.util.List;


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private Context context;
    private List<CustomColor> mColors;
    private final LayoutInflater mInflater;

    private int checkedPosition = 5;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public ColorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        CircleView color;
        ImageView checkMark;
        public ColorViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            color = itemView.findViewById(R.id.circleView);
            checkMark = itemView.findViewById(R.id.checkMark);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if (listener != null) {
                        checkMark.setVisibility(View.VISIBLE);
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }

                        if (checkedPosition != position) {
                            notifyItemChanged(checkedPosition);
                            checkedPosition = getAdapterPosition();
                        }
                    }
               }
           });
        }

        public void bind(final CustomColor color) {
            if (checkedPosition == -1) {
                checkMark.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkMark.setVisibility(View.VISIBLE);
                } else {
                    checkMark.setVisibility(View.GONE);
                }
            }
        }
    }


    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.color_item, parent, false);
        return new ColorViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        if (mColors != null) {
            CustomColor color = mColors.get(position);
            holder.color.setCircleColor(Color.parseColor("#" + color.getColorCode()));
            holder.color.setShadowColor(Color.parseColor("#" + color.getColorCode()));
            holder.bind(color);
//            holder.color.setCircleColor(color.getColorId());
//            holder.color.setShadowColor(color.getColorId());

        } else {
            Log.i("DooAnne: Colors", "No Colors");
        }


    }

    public void setColors(List<CustomColor> colors) {
        mColors = colors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mColors != null)
            return mColors.size();
        else return 0;
    }
}
