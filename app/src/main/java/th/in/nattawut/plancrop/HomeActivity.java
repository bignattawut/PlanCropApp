package th.in.nattawut.plancrop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import th.in.nattawut.plancrop.fragment.About;
import th.in.nattawut.plancrop.fragment.FarmerViewFragment;
import th.in.nattawut.plancrop.fragment.MainPlanFragment;
import th.in.nattawut.plancrop.fragment.OrderResultSumViewFragment;
import th.in.nattawut.plancrop.fragment.OrderViewRePortFragment;
import th.in.nattawut.plancrop.fragment.PlanFarmerViewFragment;
import th.in.nattawut.plancrop.fragment.PlanResultViewFragment;
import th.in.nattawut.plancrop.fragment.PlantFarmerViewFragment;
//import th.in.nattawut.plancrop.fragment.PlantPicture;
import th.in.nattawut.plancrop.fragment.PlantReportViewFragment;
import th.in.nattawut.plancrop.fragment.PlantReportallViewFragment;
import th.in.nattawut.plancrop.fragment.PlantResultViewFragment;
import th.in.nattawut.plancrop.fragment.TabPlanFragment;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlantPicViewFragment2;
import th.in.nattawut.plancrop.utility.SaveSharedPreference;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String nameString;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    TextView textView;
    LinearLayout linearLayout;
    CardView cardView;

    private int typeDataInt;
    private String idLogin;

    OrderService orderService;
    private SaveSharedPreference SaveSharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //receiveValueFromMain
        receiveValueFromMain();

        //Add Fragment to Activity
        addFragment(savedInstanceState);


        ////navigationView
        Toolbar toolbar = findViewById(R.id.toolbarHone);
        //toolbar.setNavigationIcon(R.drawable.ic_action_camera);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //nameString = getIntent().getStringExtra("Name");
        //getSupportActionBar().setSubtitle(nameString);

        //get name nav_header_name
        nameString = getIntent().getStringExtra("name");
        View view = navigationView.getHeaderView(0);
        textView = view.findViewById(R.id.nav_header_name);
        textView.setText(nameString);
        cardView = view.findViewById(R.id.nav_header_id);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ///////

        plantController();


    }


    private void plantController() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new FarmerViewFragment())
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user:
                setTitle("หน้าหลัก");
                //getSupportActionBar().setSubtitle(nameString);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new TabPlanFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_plan:
                setTitle("วางแผนเพาะปลูก");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlanFarmerViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_Plant:
                setTitle("การเพาะปลูก");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantFarmerViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_PlantPicture:
                setTitle("บันทึกกิจกรรมเพาะปลูก");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantPicViewFragment2())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_produce:
                setTitle("สรุปการวางแผนเพาะปลูก");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlanResultViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_PlantRepor:
                setTitle("พืชที่ปลูก ณ ปัจจุบัน");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantReportViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_PlantReportall:
                setTitle("พืชเพาะปลูก");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantReportallViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_want:
                setTitle("แจ้งความต้องการ");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new OrderViewRePortFragment())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.menu_chart:
                setTitle("ประมาณการผลผลิต");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new PlantResultViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.munu_orderresultsum:
                setTitle("สถิตความต้องการพืชปลอดสาร");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new OrderResultSumViewFragment())
                        .addToBackStack(null)
                        .commit();
                break;
//            case R.id.menu_member:
//                setTitle("สมาชิก");
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentHomeFragment, new MemberFragment())
//                        .addToBackStack(null)
//                        .commit();
//                break;
            case R.id.menu_about:
                setTitle("เกี่ยวกับเรา");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new About())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_exit:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
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
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();

                    }
                });
                builder.show();
                break;

        }
        //drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.closeDrawers();
        return true;
    }


    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentHomeFragment, new MainPlanFragment())
                    //.replace(R.id.contentHomeFragment, new HomeFragment())
                    .commit();
        }
    }


    private void receiveValueFromMain() {
        nameString = getIntent().getStringExtra("name");
        Log.d("1Jan", "nameString ==> " + nameString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_select,menu);//Show layout
        menuInflater.inflate(R.menu.manu_exit,menu);
        menuInflater.inflate(R.menu.manu_option, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSingOut){
//            finish();
//            SaveSharedPreference.setLoggedIn(getApplicationContext(),false);
//
            SaveSharedPreference = new SaveSharedPreference();
            logout();


        }
        if (item.getItemId() == R.id.option) {
            getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentHomeFragment, new About())
                        .addToBackStack(null)
                        .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        SaveSharedPreference.setLoggedIn(getApplicationContext(),false);
        finish();
        startActivity(new Intent(HomeActivity.this,MainActivity.class));

    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

