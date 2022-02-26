package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class Pla extends Fragment {

    EditText add1, add2;
    TextView sum;
    int w, h;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add1 = getView().findViewById(R.id.add1);
        add2 = getView().findViewById(R.id.add2);
        sum = getView().findViewById(R.id.sum);


        add2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String ws = add1.getText().toString();

                        if (ws.length() > 0) {
                            w = Integer.parseInt(ws);
                        }
                        String hs = add2.getText().toString();
                        if (hs.length() > 0) {
                            h = Integer.parseInt(hs);
                        }

                        int area = calcarea();
                        sum.setText(Integer.toString(area));

                    }
                }
        );
    }

    int calcarea()
    {
        return w+h;
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int i = Integer.parseInt(add1.getText().toString().trim());
//                int i1 = Integer.parseInt(add2.getText().toString().trim());
//                int qty = i+i1;
//                sum.setText(String.valueOf(qty));
//            }
//        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pla, container, false);
        return view;
    }
}
