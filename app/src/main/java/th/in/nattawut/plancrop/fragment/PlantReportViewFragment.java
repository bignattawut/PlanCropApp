package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlantReport;
import th.in.nattawut.plancrop.utility.PlantReportAdpter;

public class PlantReportViewFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    OrderService orderService;
    List<PlantReport> list = new ArrayList<PlantReport>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        swiRefreshLayou();

        showMid();

    }
    private void swiRefreshLayou() {
        mSwipeRefreshLayout = getView().findViewById(R.id.swiRefreshLayoutPlantReport);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorlightGreen);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMid();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void showMid() {
        listView = getView().findViewById(R.id.listViewPlantReport);
        orderService = APIUtils.getService();
        if (getActivity().getIntent().getExtras() != null) {
            String mid = getActivity().getIntent().getExtras().getString("mid");
            plantReportController(mid);

        }
    }

    private void plantReportController(String mid) {
        Call<List<PlantReport>> call = orderService.getPlantReport(mid);
        call.enqueue(new Callback<List<PlantReport>>() {
            @Override
            public void onResponse(Call<List<PlantReport>> call, Response<List<PlantReport>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new PlantReportAdpter(getActivity(),R.layout.frm_plantreport_view,list));
                }
            }

            @Override
            public void onFailure(Call<List<PlantReport>> call, Throwable t) {

            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_plantreport, container, false);
        return view;
    }
}
