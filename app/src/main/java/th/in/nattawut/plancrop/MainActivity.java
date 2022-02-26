package th.in.nattawut.plancrop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import th.in.nattawut.plancrop.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Value Login
        //String[] loginString = getIntent().getStringArrayExtra("MID");
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMainFragment, new HomeFragment())
                    .commit();

            }
        }
    }

