package com.dooanne.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dooanne.R;
import com.dooanne.ui.activities.BaseActivity;


public class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"ĐÁLSADL",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity) requireActivity()).closeNavBarAndAppBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((BaseActivity) requireActivity()).showNavBarAndAppBar();
    }
}