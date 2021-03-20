package com.dooanne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.ColumnInfo;
import androidx.viewpager.widget.PagerAdapter;

public class TutorialPageAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;

    public TutorialPageAdapter(Context context) {
        this.mContext = context;
    }

    public int[] slide_images = {
            R.drawable.suitcase,
            R.drawable.diet,
            R.drawable.sports
    };

    public String[] slide_headings = {
            "Đầu tiên bạn cầm điện thoại lên",
            "Sau đó mở ứng dụng Đoán Xem",
            "Chơi đê"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.intro_slide_layout, container, false);

        TextView textView = view.findViewById(R.id.slide_title);
        textView.setText(slide_headings[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
