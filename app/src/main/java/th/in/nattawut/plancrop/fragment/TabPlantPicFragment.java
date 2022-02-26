package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import th.in.nattawut.plancrop.R;

public class TabPlantPicFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        //Add Plan
//        addPlantPic();
    }

//    private void addPlantPic() {
//        FloatingActionButtonExpandable floatingActionButtonPlantPic = getView().findViewById(R.id.floatingActionButtonPlantPic);
//        floatingActionButtonPlantPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentHomeFragment, new PlantPictureFragment())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//    }

//    private void addPlantPic() {
//        FloatingActionButton floatingActionButton = getView().findViewById(R.id.floatingActionButton3);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentHomeFragment, new PlantPictureFragment())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_tablayout_plantpic1, container, false);
        return view;
    }
}
