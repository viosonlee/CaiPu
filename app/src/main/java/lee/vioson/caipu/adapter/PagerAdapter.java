package lee.vioson.caipu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import lee.vioson.caipu.fragment.ListFragment;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> types;

    public PagerAdapter(FragmentManager fm, ArrayList<String> types, ArrayList<String> titles) {
        super(fm);
        this.types = types;
        this.titles = titles;
        fragments = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            ListFragment fragment = ListFragment.newInstance(types.get(i));
            fragments.add(fragment);
        }
    }

    private ArrayList<String> titles;

    private ArrayList<Fragment> fragments;

    @Override
    public Fragment getItem(int position) {
        if (fragments == null)
            return null;
        else {
            if (fragments.size() == 0)
                return null;
            else return fragments.get(position);
        }
    }

    @Override
    public int getCount() {
        return types == null ? 0 : types.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
