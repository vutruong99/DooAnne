package com.dooanne.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.dooanne.R;
import com.dooanne.model.Deck;
import com.dooanne.viewmodel.CardsViewModel;
import com.dooanne.adapters.DeckAdapter;
import com.dooanne.viewmodel.DeckViewModel;

import java.util.List;

public class SearchFragment extends BaseFragment {

    EditText mKeyWordET;
    ImageView mCloseSearchButton;
    DeckViewModel mDeckViewModel;
    private RecyclerView mRecyclerView;
    private DeckAdapter mDeckAdapter;
    private CardsViewModel mCardsViewModel;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        initView(view);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        mDeckViewModel = ViewModelProviders.of(requireActivity()).get(DeckViewModel.class);
        mCardsViewModel = ViewModelProviders.of(requireActivity()).get(CardsViewModel.class);
        mDeckAdapter = new DeckAdapter(getContext(), mItemClickListener);
        mRecyclerView = view.findViewById(R.id.recycler_view_deck);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 60;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });

        mRecyclerView.setAdapter(mDeckAdapter);
    }

    private void initView(View view) {
        mKeyWordET = view.findViewById(R.id.keyword);
        mKeyWordET.addTextChangedListener(textWatcher);
        mKeyWordET.requestFocus();
        showKeyboard(requireContext());
        mCloseSearchButton = view.findViewById(R.id.close_search_button);
        mCloseSearchButton.setOnClickListener(closeButtonListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private final DeckAdapter.ItemClickListener mItemClickListener = new DeckAdapter.ItemClickListener() {
        @Override
        public void onClick(Deck deck) {
            hideKeyboardFrom(requireContext(), view);
            DeckInfoFragment deckInfoFragment = new DeckInfoFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.big_fragment_container, deckInfoFragment)
                    .addToBackStack(null)
                    .commit();
            mCardsViewModel.setCurrentDeck(deck);

        }
    };

    private final View.OnClickListener closeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboardFrom(requireContext(), view);
            getParentFragmentManager().popBackStack();
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mDeckViewModel.getSearchedDecks(s.toString()).observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
                @Override
                public void onChanged(List<Deck> decks) {
                    mDeckAdapter.setDecks(decks);
                    mDeckAdapter.notifyDataSetChanged();
                    mRecyclerView.scheduleLayoutAnimation();
                }
            });
        }
    };

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context) {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(!hasFocus) {
                InputMethodManager imm =  (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}