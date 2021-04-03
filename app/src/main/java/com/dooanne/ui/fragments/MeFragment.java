package com.dooanne.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.dooanne.R;
import com.dooanne.MePagesAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MeFragment extends Fragment {

//    ViewPager2 viewPager;
//    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);
//        viewPager = view.findViewById(R.id.viewPager);
//        tabLayout = view.findViewById(R.id.tabLayout);
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        setupViewPager(viewPager);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
////        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
////                View.SYSTEM_UI_FLAG_FULLSCREEN
////                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//
//
//    }

//    private void setupViewPager(ViewPager2 viewPager) {
//        MePagesAdapter adapter = new MePagesAdapter(requireActivity());
//
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(2);
//
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
//                tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                if (position == 0) {
//                    tab.setText("Yêu thích");
//                } else {
//                    tab.setText("Sáng tạo");
//                }
//            }
//        });
//
//        tabLayoutMediator.attach();
//
//    }
}