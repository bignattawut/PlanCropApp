package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.PlantPicViewFragment2;

public class MainPlanFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Add Plan
        addPlan();

        addPlant();

        addPlanPic();

        addOrder();



    }
    private void addPlan() {
        CardView cardViewPlanHome = getView().findViewById(R.id.CardViewPlanHome);
        cardViewPlanHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlanFarmerViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void addPlant() {
        CardView cardViewPlanHome = getView().findViewById(R.id.CardViewPlantHome);
        cardViewPlanHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantFarmerViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void addPlanPic() {
        CardView cardViewPlanHome = getView().findViewById(R.id.CardViewPicHome);
        cardViewPlanHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantPicViewFragment2())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void addOrder() {
        CardView cardViewPlanHome = getView().findViewById(R.id.CardViewOrderHome);
        cardViewPlanHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new OrderViewRePortFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_tablayout_home, container, false);
        return view;
    }
}
