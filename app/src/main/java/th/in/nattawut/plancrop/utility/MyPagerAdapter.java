package th.in.nattawut.plancrop.utility;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import th.in.nattawut.plancrop.fragment.TabPlanFragment;
import th.in.nattawut.plancrop.fragment.TabPlantFragment;
import th.in.nattawut.plancrop.fragment.TabPlantPicFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private int anInt;

    public MyPagerAdapter(FragmentManager fragmentManager,
                          int anInt) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.anInt = anInt;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabPlanFragment tabPlanFragment = new TabPlanFragment();
                return tabPlanFragment;
            case 1:
                TabPlantFragment tabPlantFragment = new TabPlantFragment();
                return tabPlantFragment;
            case 2:
                TabPlantPicFragment tabPlantPicFragment = new TabPlantPicFragment();
                return tabPlantPicFragment;
            default:
                return null;


           /* case 0:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new TabPlanFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new TabPlantFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new TabPlantPicFragment())
                        .addToBackStack(null)
                        .commit();
                break;*/


        }

    }

    @Override
    public int getCount() {
        return anInt;
    }
}
