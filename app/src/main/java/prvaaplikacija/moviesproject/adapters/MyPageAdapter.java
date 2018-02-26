package prvaaplikacija.moviesproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import prvaaplikacija.moviesproject.fragments.PeoplesFragment;

/**
 * Created by Mende on 08.2.2018.
 */

public class MyPageAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    ArrayList<String> titles = new ArrayList<String>();


    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        titles.add(title);
        fragments.add(fragment);
    }





    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }

    public void removeAllFragments() {
        fragments = new ArrayList<>();
        notifyDataSetChanged();
    }


}
