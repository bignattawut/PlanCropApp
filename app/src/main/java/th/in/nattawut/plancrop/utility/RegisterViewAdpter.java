package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

//สืบทอดมาจาก BaseAdapter

public class RegisterViewAdpter extends BaseAdapter {

    private Context context;
    private String[] userString,passwordString,idString,nameString,addressString,pidString,didString,sidString,vidString,phonString,emailString;

    public RegisterViewAdpter(Context context,
                              String[] userString,
                              String[] passwordString,
                              String[] idString,
                              String[] nameString,
                              String[] addressString,
                              String[] pidString,
                              String[] didString,
                              String[] sidString,
                              String[] vidString,
                              String[] phonString,
                              String[] emailString) {
        this.context = context;
        this.userString = userString;
        this.passwordString = passwordString;
        this.idString = idString;
        this.nameString = nameString;
        this.addressString = addressString;
        this.pidString = pidString;
        this.didString = didString;
        this.sidString = sidString;
        this.vidString = vidString;
        this.phonString = phonString;
        this.emailString = emailString;
    }

    @Override
    public int getCount() {
        return userString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.frm_register_view,parent,false);

        TextView username = view.findViewById(R.id.textUsername);
        TextView password = view.findViewById(R.id.textPassword);
        TextView id = view.findViewById(R.id.textID);
        TextView name = view.findViewById(R.id.textName);
        TextView address = view.findViewById(R.id.textAddress);
        TextView pid = view.findViewById(R.id.textPid);
        TextView did = view.findViewById(R.id.textDid);
        TextView sid = view.findViewById(R.id.textSid);
        TextView vid = view.findViewById(R.id.textVid);
        TextView phon = view.findViewById(R.id.textPhone);
        TextView email = view.findViewById(R.id.textEmail);

        username.setText(userString[position]);
        password.setText(passwordString[position]);
        id.setText(idString[position]);
        name.setText(nameString[position]);
        address.setText(addressString[position]);
        pid.setText(pidString[position]);
        did.setText(didString[position]);
        sid.setText(sidString[position]);
        vid.setText(vidString[position]);
        phon.setText(phonString[position]);
        email.setText(emailString[position]);

        /*View view = layoutInflater.inflate(R.layout.edit_register,parent,false);
        EditText username = view.findViewById(R.id.EditEdtUsername);
        EditText password = view.findViewById(R.id.EditEdtPassword);
        EditText name = view.findViewById(R.id.EditEdtName);
        EditText id = view.findViewById(R.id.EditEdtId);
        EditText address = view.findViewById(R.id.EditEdtAddress);
        TextView vid = view.findViewById(R.id.textVid);
        TextView sid = view.findViewById(R.id.textSid);
        EditText phon = view.findViewById(R.id.EditEdtPhone);
        EditText email = view.findViewById(R.id.EditEdtEmail);

        username.setText(userString[position]);
        password.setText(passwordString[position]);
        name.setText(nameString[position]);
        id.setText(idString[position]);
        address.setText(addressString[position]);
        vid.setText(vidString[position]);
        sid.setText(sidString[position]);
        phon.setText(phonString[position]);
        email.setText(emailString[position]);*/
        return view;
    }

}
