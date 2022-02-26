package th.in.nattawut.plancrop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import th.in.nattawut.plancrop.fragment.MemberFragment;

public class MemberActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMemberFragment, new MemberFragment())
                    .commit();
        }
    }
}

