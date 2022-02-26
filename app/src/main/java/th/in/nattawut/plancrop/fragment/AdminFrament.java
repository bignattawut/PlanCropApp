package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import th.in.nattawut.plancrop.AdminActivity;
import th.in.nattawut.plancrop.MainActivity;
import th.in.nattawut.plancrop.R;

public class AdminFrament extends Fragment {


    CardView farmer;
    CardView register;
    CardView crop;
    CardView cropType;
    CardView plan;
    CardView plant;
    CardView Site;
    CardView Result;

    private String nameString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        receiveValueFromMain();

        //CropType Controller
        cropTypeController();

        //Crop Controller
        cropController();

        //Register Controller
        RegisterController();

        //Farmer Controller
        farmerController();

        //Plan Controller
        planController();

        //Plant Controller
        plantController();

        //Site Controller
        siteController();

        //Order Controller
        orderController();

        //PlantReprotallControlle
        PlantReprotallController();

        PlantResultController();

        CreateToolbal();



    }

    private void receiveValueFromMain() {
        nameString = getActivity().getIntent().getStringExtra("name");
        Log.d("1Jan", "nameString ==> " + nameString);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSingOut) {
            //getActivity().finish();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("ต้องการออกจากระบบใช่หรือไม่?");
            builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    //finish();
                    getActivity().finish();
                    dialog.dismiss();

                }
            });
            builder.show();

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manu_exit,menu);

    }

    private void CreateToolbal() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarHomeActivity);
        ((AdminActivity)getActivity()).setSupportActionBar(toolbar);

        ((AdminActivity)getActivity()).getSupportActionBar().setTitle("Admin : " + nameString);
        //((AdminActivity)getActivity()).getSupportActionBar().setSubtitle("jojoj");

        ((AdminActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AdminActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.mipmap.ic_appnattawut1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentAdminFragment, new MainFragment())
//                        .addToBackStack(null)
//                        .commit();

            }
        });
        setHasOptionsMenu(true);

    }


    private void farmerController() {
        farmer = getView().findViewById(R.id.imageViewFarmer);
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new FarmerViewAdminFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void RegisterController() {
        register = getView().findViewById(R.id.imageViewRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new RegisterViewAdminFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private void cropController() {
        crop = getView().findViewById(R.id.imageViewCrop);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new CropViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void cropTypeController() {
        cropType = getView().findViewById(R.id.imageViewCropTypr);
        cropType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new CropTypeViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void planController() {
        plan = getView().findViewById(R.id.imageViewPlan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new PlanViewFragment1())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void plantController() {
        plant = getView().findViewById(R.id.imageViewPlant);
        plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new PlantViewFragment1())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void siteController() {
        Site = getView().findViewById(R.id.imageViewSite);
        Site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new SiteViewFrament())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void orderController() {
        Site = getView().findViewById(R.id.imageViewOrder);
        Site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new OrderViewRePortFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void PlantReprotallController() {
        Site = getView().findViewById(R.id.imageViewPlantReprotall);
        Site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new PlantReportallViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void PlantResultController() {
        Result = getView().findViewById(R.id.imageViewPlantResult);
        Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentAdminFragment, new PlantResultViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin2, container, false);
        return view;
    }
}

