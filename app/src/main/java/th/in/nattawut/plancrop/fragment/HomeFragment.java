package th.in.nattawut.plancrop.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class HomeFragment extends Fragment {

    TextView scrollingText;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Register Controller
        registerController();

        //createToolbar();

//        TextView textView = getView().findViewById(R.id.textLogin);
//        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);
//        textView.startAnimation(animation);

        scrollingText = getView().findViewById(R.id.textLogin);
        scrollingText.setSelected(true);

        orderController();

        PlantReprotallController();

        PlantResultController();



        /////////
//        MaterialSpinner spinner1 = getView().findViewById(R.id.spinner1);
//        String[] item = {"Sumsung","Apple","Microsoft"};
//        spinner1.setHint("lalallalala");
//        spinner1.setItems(item);
//        spinner1.setSelectedIndex(1);
//        final MaterialSpinner spinner2 = getView().findViewById(R.id.spinner2);
//        setSprinner2(spinner2,spinner1.getText().toString());
//        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view,
//                                       int position, long id, String item) {
//                setSprinner2(spinner2,item);
//            }
//        });

        /////////

    }
//    private void setSprinner2(MaterialSpinner sprinner2,String item){
//        if (item.equals("Sumsung")) {
//            sprinner2.setItems("Galaxy s","Galaxy y");
//        } else if (item.equals("Apple")) {
//            sprinner2.setItems("ipons s","ipad y");
//        } else if (item.equals("Microsoft")) {
//            sprinner2.setItems("i");
//        }
//
//
//    }



    ///////
    private void PlantReprotallController() {
        CardView PlantReprotall = getView().findViewById(R.id.imageViewPlantReprotall);
        PlantReprotall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new PlantReportallViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void orderController() {
        CardView Order = getView().findViewById(R.id.imageViewOrder);
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new OrderViewRePortFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void registerController() {
        CardView imageViewLogin = getView().findViewById(R.id.imageViewLogin);
        imageViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkStatus();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new MainFragment())
                        //.replace(R.id.contentMainFragment, new maptest())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void PlantResultController() {
        CardView imageViewPlantResult = getView().findViewById(R.id.imageViewPlantResult);
        imageViewPlantResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkStatus();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new PlantResultViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void checkStatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("กรุณาเลือกการทำงาน");
        builder.setMessage("ต้องการเข้าสู่แอพพลิเคชัน ?");
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("เข้าสู่ระบบ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new PlantResultViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        builder.show();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.frm_main1, container, false);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_main1, container, false);
        return view;
    }
}
