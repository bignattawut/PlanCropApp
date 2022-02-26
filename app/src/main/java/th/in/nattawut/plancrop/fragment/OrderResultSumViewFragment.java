package th.in.nattawut.plancrop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.OrderResultBalance;
import th.in.nattawut.plancrop.utility.OrderResultSum;
import th.in.nattawut.plancrop.utility.OrderResultSumAdpter;
import th.in.nattawut.plancrop.utility.OrderService;

public class OrderResultSumViewFragment extends Fragment {

    ListView listViewOrderResultSum,listViewOrderResultBalance;
    //PieChart listViewOrderResultSum;
    OrderService orderService;
    List<OrderResultBalance> list = new ArrayList<OrderResultBalance>();
    List<OrderResultSum> listSum = new ArrayList<OrderResultSum>();

    private PieChart pieChart,Balance;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listViewOrderResultSum = getView().findViewById(R.id.listViewOrderResultSum);
        listViewOrderResultBalance = getView().findViewById(R.id.listViewOrderResultBalance);
        orderService = APIUtils.getService();
        //orderResultBalance();
        orderResultSum();


        pieChart = getView().findViewById(R.id.listViewOrderChartResult);
        getGrowthChart();


       /*Balance = getView().findViewById(R.id.listViewOrderResultChartBalance);
        orDerBalanceChart();**/
    }

    private void orDerBalanceChart() {
        Call<List<OrderResultBalance>> call = orderService.OrderResultBalance();
        call.enqueue(new Callback<List<OrderResultBalance>>() {
            @Override
            public void onResponse(Call<List<OrderResultBalance>> call, Response<List<OrderResultBalance>> response) {
                if (response.body()!=null) {
                    List<PieEntry> pieEntries = new ArrayList<>();
                    for (OrderResultBalance orderResultBalance : response.body()){
                        pieEntries.add(new PieEntry(orderResultBalance.getYield(),orderResultBalance.getCrop()));

                    }
                    Balance.setVisibility(View.VISIBLE);
                    Balance.animateXY(3000,3000);

                    PieDataSet pieDataSet = new PieDataSet(pieEntries,"OrderResult");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueLinePart1Length(0.5f);
                    pieDataSet.setValueLinePart2Length(0.5f);
                    //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setValueFormatter(new PercentFormatter());

                    PieData pieData = new PieData(pieDataSet);
                    Balance.setData(pieData);
                    Balance.setCenterText("สถิตความต้องการคงเหลือ");
                    Balance.setCenterTextColor(Color.parseColor("#00bcd4"));
                    Balance.setCenterTextSize(10);
                    Balance.setHoleRadius(30);
                    Balance.setTransparentCircleRadius(40);
                    Balance.setUsePercentValues(true);

                    Description description = new Description();
                    description.setText("สถิตการวางแผนพืช");
                    Balance.setDescription(description);
                    Balance.invalidate();

                }
            }

            @Override
            public void onFailure(Call<List<OrderResultBalance>> call, Throwable t) {

            }
        });
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
                    pieChart.animateXY(3000,3000);

                    PieDataSet pieDataSet = new PieDataSet(pieEntries,"OrderResult");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueLinePart1Length(0.5f);
                    pieDataSet.setValueLinePart2Length(0.5f);
                    //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setValueFormatter(new PercentFormatter());

                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.setCenterText("สถิตความต้องการ");
                    pieChart.setCenterTextColor(Color.parseColor("#00bcd4"));
                    pieChart.setCenterTextSize(10);
                    pieChart.setHoleRadius(30);
                    pieChart.setTransparentCircleRadius(40);
                    pieChart.setUsePercentValues(true);

                    Description description = new Description();
                    description.setText("สถิตความต้องการพืช");
                    pieChart.setDescription(description);
                    pieChart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<List<OrderResultSum>> call, Throwable t) {

            }
        });

    }

    private void orderResultSum() {
        Call<List<OrderResultSum>> call = orderService.OrderResultSum();
        call.enqueue(new Callback<List<OrderResultSum>>() {
            @Override
            public void onResponse(Call<List<OrderResultSum>> call, Response<List<OrderResultSum>> response) {
                if (response.isSuccessful()) {
                    listSum = response.body();
                    listViewOrderResultSum.setAdapter(new OrderResultSumAdpter(getActivity(),R.layout.frm_orderresultsum_view,listSum));
                }
            }

            @Override
            public void onFailure(Call<List<OrderResultSum>> call, Throwable t) {

            }
        });

    }

    /*private void orderResultBalance() {
        Call<List<OrderResultBalance>> call = orderService.OrderResultBalance();
        call.enqueue(new Callback<List<OrderResultBalance>>() {
            @Override
            public void onResponse(Call<List<OrderResultBalance>> call, Response<List<OrderResultBalance>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listViewOrderResultBalance.setAdapter(new OrderResultBalanceAdpter(getActivity(),R.layout.frm_orderresultbalance_view,list));
                }
            }

            @Override
            public void onFailure(Call<List<OrderResultBalance>> call, Throwable t) {

            }
        });

    }**/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_conclude, container, false);
        return view;
    }
}
