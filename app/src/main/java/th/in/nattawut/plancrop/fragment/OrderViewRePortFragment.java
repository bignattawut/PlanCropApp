package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.GetData;
import th.in.nattawut.plancrop.utility.Myconstant;
import th.in.nattawut.plancrop.utility.OrderReportAdpter;

public class OrderViewRePortFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        createListView();

    }

    private void createListView() {
        final ListView listView = getView().findViewById(R.id.listViewOrderReport);
        Myconstant myconstant = new Myconstant();
        String[] columnStrings = myconstant.getColumnOrderString();

        try {

            GetData getData = new GetData(getActivity());
            getData.execute(myconstant.getUrlselectorderreport());

            String jsonString = getData.get();
            Log.d("22big","JSON plan ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] sdateStrings = new String[jsonArray.length()];
            final String[] edateString = new String[jsonArray.length()];
            final String[] cropStrings = new String[jsonArray.length()];
            final String[] qtyString = new String[jsonArray.length()];
            final String[] nameStrings = new String[jsonArray.length()];
            final String[] telStrings = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sdateStrings[i] = jsonObject.getString(columnStrings[0]);
                edateString[i] = jsonObject.getString(columnStrings[1]);
                cropStrings[i] = jsonObject.getString(columnStrings[2]);
                qtyString[i] = jsonObject.getString(columnStrings[3]);
                nameStrings[i] = jsonObject.getString(columnStrings[4]);
                telStrings[i] = jsonObject.getString(columnStrings[5]);
            }

            OrderReportAdpter orderReportAdpter = new OrderReportAdpter(getActivity(),
                    sdateStrings,edateString,cropStrings,qtyString, nameStrings,telStrings);
            listView.setAdapter(orderReportAdpter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_orderreport, container, false);
        return view;
    }
}
