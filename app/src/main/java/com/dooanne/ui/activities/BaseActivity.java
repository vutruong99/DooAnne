package com.dooanne.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dooanne.R;
import com.dooanne.ScrollToTop;
import com.dooanne.ui.fragments.CustomFragment;
import com.dooanne.ui.fragments.DeckFragment;
import com.dooanne.ui.fragments.FavoriteFragment;
import com.dooanne.ui.fragments.MeFragment;
import com.dooanne.ui.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    RelativeLayout mPad;
    TextView mPageTitle;
    ImageView mSearchButton;
    ImageView mHelpButton;
    ScrollToTop scrollToTopInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
//        Full screen for devices with notches
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        init();


    }

    private void init() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeckFragment()).commit();
        mPageTitle = findViewById(R.id.title);
        mSearchButton = findViewById(R.id.search_button);
        mHelpButton = findViewById(R.id.help);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mPad = findViewById(R.id.custom_action_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        mBottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);
    }

    public void setListener(ScrollToTop listener)
    {
        this.scrollToTopInterface = listener ;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new DeckFragment();
            if (item.getItemId() == R.id.deckFragment) {
                selectedFragment = new DeckFragment();
                mSearchButton.setVisibility(View.VISIBLE);
                mHelpButton.setVisibility(View.VISIBLE);
                mPageTitle.setText(R.string.decks);
            } else if (item.getItemId() == R.id.favoriteFragment) {
                selectedFragment = new FavoriteFragment();
                mPageTitle.setText(R.string.favorite);
                mSearchButton.setVisibility(View.GONE);
                mHelpButton.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.createFragment) {
                selectedFragment = new CustomFragment();
                mPageTitle.setText(R.string.create);
                mSearchButton.setVisibility(View.GONE);
                mHelpButton.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.meFragment) {
                selectedFragment = new MeFragment();
                mPageTitle.setText(R.string.me);
                mSearchButton.setVisibility(View.GONE);
                mHelpButton.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.settingsFragment) {
                selectedFragment = new SettingsFragment();
                mPageTitle.setText(R.string.settings);
                mSearchButton.setVisibility(View.GONE);
                mHelpButton.setVisibility(View.GONE);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    private final BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            scrollToTopInterface.scrollToTop();
        }
    };

    public void closeNavBarAndAppBar() {
        mBottomNavigationView.setVisibility(View.INVISIBLE);
        mPad.setVisibility(View.INVISIBLE);
    }

    public void showNavBarAndAppBar() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mPad.setVisibility(View.VISIBLE);
    }


}