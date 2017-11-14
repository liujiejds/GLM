package com.example.admin.glm;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by admin on 17-5-10.
 */

public class MyFragmenPagerAdap extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> title;
    private FragmentManager fm;
    boolean[] fragmentsUpdateFlag = {false, false, false};
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=(Fragment) super.instantiateItem(container, position);
        String Tag=fragment.getTag();
        if (fragmentsUpdateFlag[position%fragmentList.size()]){
            android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();
            ft.remove(fragment);
            fragment=fragmentList.get(position%fragmentList.size());
            ft.add(container.getId(),fragment,Tag);
            ft.attach(fragment);
            ft.commit();
            fragmentsUpdateFlag[position%fragmentList.size()]=false;
            notifyDataSetChanged();
        }
        return fragment;
    }

    public MyFragmenPagerAdap(FragmentManager fm, List<Fragment> fragmentList, List<String> title){
        super(fm);
        this.fm=fm;
        this.fragmentList=fragmentList;
        this.title=title;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
