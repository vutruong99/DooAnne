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
import com.dooanne.model.Icon;
import com.mikhaellopez.circleview.CircleView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private Context context;
    private List<Icon> mIcons;
    private final LayoutInflater mInflater;
    private int checkedPosition = 5;



    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public IconAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class IconViewHolder extends RecyclerView.ViewHolder {
        CircleImageView icon;
        ImageView checkMark;
        public IconViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.circleImageView);
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

         public void bind(final Icon icon) {
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
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.icon_item, parent, false);
        return new IconViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        if (mIcons != null) {
            Icon icon = mIcons.get(position);
            holder.icon.setImageResource(icon.getIconCode());
            holder.bind(icon);
//            holder.color.setCircleColor(color.getColorId());
//            holder.color.setShadowColor(color.getColorId());

        } else {
            Log.i("DooAnne: Colors", "No Colors");
        }


    }

    public void setIcons(List<Icon> icons) {
        mIcons = icons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIcons != null)
            return mIcons.size();
        else return 0;
    }
}
