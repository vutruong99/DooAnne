package com.dooanne.ui.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.R;
import com.dooanne.adapters.WordAdapter;
import com.dooanne.model.Word;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class AddWordsFragment extends Fragment implements Step {

    private ArrayList<Word> wordList;
    TextInputEditText addWordET;
    TextInputLayout textInputLayout;
    RecyclerView mRecyclerView;
    WordAdapter mWordAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_words, container, false);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView(view);
        return view;

    }

    private void initView(View view) {
        addWordET = view.findViewById(R.id.addWordET_sub);
        textInputLayout = view.findViewById(R.id.addWordET);
        addWordET.addTextChangedListener(textWatcher);
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(addWordET.getText()).toString().isEmpty()) {
                    insertWord(Objects.requireNonNull(addWordET.getText()).toString());
                    addWordET.getText().clear();
                    mRecyclerView.smoothScrollToPosition(wordList.size()-1);
                }
            }
        });

        mRecyclerView = view.findViewById(R.id.wordListRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.setHasFixedSize(true);
        mWordAdapter = new WordAdapter(requireActivity());
        mWordAdapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                deleteWord(position );
            }
        });
        wordList = new ArrayList<>();
        mWordAdapter.setWords(wordList);
        mRecyclerView.setAdapter(mWordAdapter);
    }

    public void insertWord(String word) {
        wordList.add(new Word(word));
        mWordAdapter.notifyDataSetChanged();
    }

    public void deleteWord(int position) {
        wordList.remove(position);
        mWordAdapter.notifyItemRemoved(position);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }



    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                textInputLayout.setEndIconTintList(ColorStateList.valueOf(requireActivity().getResources().getColor(R.color.lightGrey)));
            } else {
                textInputLayout.setEndIconTintList(ColorStateList.valueOf(requireActivity().getResources().getColor(R.color.colorPrimary)));
            }
        }
    };
}