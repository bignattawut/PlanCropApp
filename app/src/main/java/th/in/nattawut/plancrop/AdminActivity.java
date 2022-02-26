package th.in.nattawut.plancrop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import th.in.nattawut.plancrop.fragment.AdminFrament;

public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentAdminFragment, new AdminFrament())
                    .commit();
        }
    }
}

