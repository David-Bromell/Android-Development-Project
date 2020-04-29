package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    //INT TO HANDLE NUMBER OF TABS (2)
    private int noTabs;
    //PAGE ADAPTER TO HANDLE COMMUNICATION BETWEEN UI AND CLASS
    PageAdapter(FragmentManager fm, int noTabs) {
        super(fm);
        this.noTabs = noTabs;
    }

    @NonNull
    @Override
    //IF FRAGMENT IS CHANGED A SWTICH IS USED TO DETERMINE WHICH TAB SHOULD BE DISPLAYED
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //IF TAB 1 THEN DISPLAYS TAB AT POSITION 0
                return new tab1();
            case 1:
                //IF TAB 2 THEN DISPLAYS TAB AT POSITION 2
                return new tab2();
            default:
                return null;
        }
    }

    @Override
    //USED TO COUNT TABS
    public int getCount() {
        return noTabs;
    }

    @Override
    //USED TO FIND THE ITEMS POSITION
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
