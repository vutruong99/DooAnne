package com.dooanne.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dooanne.R;
import com.dooanne.ui.fragments.DeckFragment;
import com.dooanne.ui.fragments.ExploreFragment;
import com.dooanne.ui.fragments.MeFragment;
import com.dooanne.ui.fragments.SettingsFragment;
import com.dooanne.ui.fragments.UpgradeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    CardView mCardView;
    TextView mPageTitle;
    ImageView mImageView;

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
        mImageView = findViewById(R.id.search_button);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mCardView = findViewById(R.id.custom_action_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new DeckFragment();
            if (item.getItemId() == R.id.deckFragment) {
                selectedFragment = new DeckFragment();
                mImageView.setVisibility(View.VISIBLE);
                mPageTitle.setText(R.string.decks);
            } else if (item.getItemId() == R.id.exploreFragment) {
                selectedFragment = new ExploreFragment();
                mPageTitle.setText(R.string.explore);
                mImageView.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.meFragment) {
                selectedFragment = new MeFragment();
                mPageTitle.setText(R.string.me);
                mImageView.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.upgradeFragment) {
                selectedFragment = new UpgradeFragment();
                mPageTitle.setText(R.string.upgrade);
                mImageView.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.settingsFragment) {
                selectedFragment = new SettingsFragment();
                mPageTitle.setText(R.string.settings);
                mImageView.setVisibility(View.GONE);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    public void closeNavBarAndAppBar() {
        mBottomNavigationView.setVisibility(View.GONE);
        mCardView.setVisibility(View.GONE);
    }

    public void showNavBarAndAppBar() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.VISIBLE);
    }
}