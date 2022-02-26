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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import th.in.nattawut.plancrop.MainActivity;
import th.in.nattawut.plancrop.MemberActivity;
import th.in.nattawut.plancrop.R;

public class MemberFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeController();

        memberController();

        ordertController();

        selectOrDerController();

        plantReportallController();

        CreateToolbal();

        PlantResultController();


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
        Toolbar toolbar = getView().findViewById(R.id.toolbarMemberActivity);
        ((MemberActivity)getActivity()).setSupportActionBar(toolbar);

         String nameString = getActivity().getIntent().getStringExtra("name");
        ((MemberActivity)getActivity()).getSupportActionBar().setTitle("สมาชิก : คุณ " + nameString);
        //((MemberActivity)getActivity()).getSupportActionBar().setSubtitle(nameString);

        ((MemberActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MemberActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.mipmap.ic_appnattawut1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentMemberFragment,new MainFragment())
//                        .addToBackStack(null)
//                        .commit();

            }
        });
        setHasOptionsMenu(true);

    }



    private void homeController() {
        CardView Requirement = getView().findViewById(R.id.CardViewHome);
        Requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new MainFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void memberController() {
        CardView Requirement = getView().findViewById(R.id.CardViewMember);
        Requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new MemberViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    private void plantReportallController() {
        CardView Requirement = getView().findViewById(R.id.CardViewPlantReportall);
        Requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new PlantReportallViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void ordertController() {
        CardView Requirement = getView().findViewById(R.id.CardViewAddOrder);
        Requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new OrderViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void selectOrDerController() {
        CardView Requirement = getView().findViewById(R.id.CardViewSelectOrder);
        Requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMemberFragment, new OrderViewRePortFragment())
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
                        .replace(R.id.contentMemberFragment, new PlantResultViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member, container, false);
        return view;
    }
}
