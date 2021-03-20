package com.dooanne;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dooanne.ui.fragments.CustomFragment;
import com.dooanne.ui.fragments.FavoriteFragment;

public class MePagesAdapter extends FragmentStateAdapter {

    public MePagesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MePagesAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MePagesAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FavoriteFragment();
        }
        return new CustomFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
