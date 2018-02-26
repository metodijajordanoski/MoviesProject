package prvaaplikacija.moviesproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import prvaaplikacija.moviesproject.fragments.PeoplesFragment;

/**
 * Created by Mende on 12.2.2018.
 */

public class MyPagePeopleAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();

    public MyPagePeopleAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyPagePeopleAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return new PeoplesFragment();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public void removeAllFragments() {
        fragments = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
