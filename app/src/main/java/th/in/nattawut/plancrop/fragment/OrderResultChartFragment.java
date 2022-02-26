package th.in.nattawut.plancrop.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.OrderResultSum;
import th.in.nattawut.plancrop.utility.OrderService;

public class OrderResultChartFragment extends Fragment {

    private PieChart pieChart;
    OrderService orderService;

    public OrderResultChartFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_orderresultchart, container, false);

        pieChart = view.findViewById(R.id.listViewOrderChartResult);
        getGrowthChart();
        return view;
    }

    private void getGrowthChart(){

        orderService = APIUtils.getService();
        Call<List<OrderResultSum>> call = orderService.OrderResultSum();
        call.enqueue(new Callback<List<OrderResultSum>>() {
            @Override
            public void onResponse(Call<List<OrderResultSum>> call, Response<List<OrderResultSum>> response) {
                if (response.body()!=null) {
                        List<PieEntry> pieEntries = new ArrayList<>();
                        for (OrderResultSum orderResultSum : response.body()){
                            pieEntries.add(new PieEntry(orderResultSum.getQty(),orderResultSum.getCrop()));
                        }
                        pieChart.setVisibility(View.VISIBLE);
                        pieChart.animateXY(5000,5000);

                        PieDataSet pieDataSet = new PieDataSet(pieEntries,"OrderResult");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.setCenterText("สถิตความต้องการ");


                        Description description = new Description();
                        description.setText("สถิตความต้องการพืชปลอดสาร");
                        pieChart.setDescription(description);
                        pieChart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<List<OrderResultSum>> call, Throwable t) {

            }
        });

    }
}
