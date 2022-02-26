package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import th.in.nattawut.plancrop.R;

public class MyAlert {
    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }

    public void onrmaIDialog(String titilString, String messageString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_userregister);
        builder.setTitle(titilString);
        builder.setMessage(messageString);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}