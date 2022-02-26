package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class FarmerViewAdminAdpter extends BaseAdapter {


    private Context context;
    private String[] midString,useridString,pwdString,idString,nameString,addressString,pidString,didString,sidString,vidString,telString,emailString,areaString;

    public FarmerViewAdminAdpter(Context context,
                                 String[] midString,
                                 String[] useridString,
                                 String[] pwdString,
                                 String[] idString,
                                 String[] nameString,
                                 String[] addressString,
                                 String[] pidString,
                                 String[] didString,
                                 String[] sidString,
                                 String[] vidString,
                                 String[] telString,
                                 String[] emailString,
                                 String[] areaString) {
        this.context = context;
        this.midString = midString;
        this.useridString = useridString;
        this.pwdString = pwdString;
        this.idString = idString;
        this.nameString = nameString;
        this.addressString = addressString;
        this.pidString = pidString;

        this.didString = didString;
        this.sidString = sidString;
        this.vidString = vidString;
        this.telString = telString;
        this.emailString = emailString;
        this.areaString = areaString;
    }

    @Override
    public int getCount() {
        return midString.length;
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
        View view = layoutInflater.inflate(R.layout.frm_farmer_view,parent,false);

        TextView name = view.findViewById(R.id.textNamef);
        TextView phon = view.findViewById(R.id.textPhonef);
//        TextView Username = view.findViewById(R.id.textUsername);
//        TextView textpid = view.findViewById(R.id.textPid);
//        TextView textdid = view.findViewById(R.id.textDid);
//        TextView textsid = view.findViewById(R.id.textSid);
        TextView textvid = view.findViewById(R.id.textvid);

        name.setText(nameString[position]);
        phon.setText(telString[position]);
//        Username.setText(useridString[position]);
//        textpid.setText(pidString[position]);
//        textdid.setText(didString[position]);
//        textsid.setText(sidString[position]);
        textvid.setText(vidString[position]);


        return view;
    }
}
