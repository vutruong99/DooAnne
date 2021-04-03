package com.dooanne.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dooanne.R;
import com.dooanne.TutorialPageAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

public class TutorialFragment extends BaseFragment {

    ViewPager tutorialViewPager;
    LinearLayout dotLayout;
    TutorialPageAdapter tutorialPageAdapter;
    private TextView[] mDots;

    CarouselView customCarouselView;
    int NUMBER_OF_PAGES = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        init(view);


        return view;
    }

    private void init(View view) {

        customCarouselView = (CarouselView) view.findViewById(R.id.customCarousel);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);
        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);
//        tutorialViewPager = view.findViewById(R.id.tutorial_view_pager);
//        dotLayout = view.findViewById(R.id.dot_layout);
//        tutorialPageAdapter = new TutorialPageAdapter(requireActivity());
//        tutorialViewPager.setAdapter(tutorialPageAdapter);
//        tutorialViewPager.addOnPageChangeListener(onPageChangeListener);
//        addDots(0);
    }
//
//    public void addDots(int position) {
//        mDots = new TextView[3];
//        dotLayout.removeAllViews();
//        for (int i = 0; i < mDots.length; i++) {
//            mDots[i] = new TextView(requireActivity());
//            mDots[i].setText(Html.fromHtml("&#8228"));
//            mDots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
//            mDots[i].setTextColor(requireActivity().getResources().getColor(R.color.grey));
//
//            dotLayout.addView(mDots[i]);
//        }
//
//        if (mDots.length > 0) {
//            mDots[position].setTextColor(requireActivity().getResources().getColor(R.color.white));
//        }
//    }
//
//    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            addDots(position);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };

    public String[] slide_headings = {
            "Đầu tiên bạn cầm điện thoại lên",
            "Sau đó mở ứng dụng Đoán Xem",
            "Chơi đê"
    };

    public int[] slide_images = {
            R.drawable.background,
            R.drawable.background_2,
            R.drawable.background_3
    };

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.intro_slide_layout,  null);
            //set view attributes here
            TextView title = customView.findViewById(R.id.slide_title);
            RelativeLayout slideBackground = customView.findViewById(R.id.slideBackground);
            title.setText(slide_headings[position]);
            slideBackground.setBackgroundResource(slide_images[position]);
            return customView;
        }
    };
}