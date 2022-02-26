package th.in.nattawut.plancrop.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.nattawut.plancrop.R;
import th.in.nattawut.plancrop.utility.APIUtils;
import th.in.nattawut.plancrop.utility.OrderResultSum;
import th.in.nattawut.plancrop.utility.OrderService;
import th.in.nattawut.plancrop.utility.PlanResult;
import th.in.nattawut.plancrop.utility.PlanResultAdpter;

public class PlanResultViewFragment extends Fragment {


    ListView listView;
    OrderService orderService;
    List<PlanResult> list = new ArrayList<PlanResult>();

    ImageView selctDate;
    TextView date;
    DatePickerDialog dataPickerDialog;
    Calendar calendar;

    private PieChart pieChart;

    ListView listViewOrderResultSum;
    //PieChart listViewOrderResultSum;
    List<OrderResultSum> listOrder = new ArrayList<OrderResultSum>();
    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getView().findViewById(R.id.listViewPlanResult);
        orderService = APIUtils.getService();

        pdateController();

        cropController();

        //plantViewController();
    }

    private void pdateController() {
        final TextView edate = getActivity().findViewById(R.id.sdate);
        ImageView selctDate = getActivity().findViewById(R.id.imageViewDatesdate);
        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //final Date cha = calendar.getTime();

                dataPickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                //date.setText(y + "/" + (m + 1) + "/" + d);
                                edate.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        },year,month,day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void cropController() {
        Button button = getView().findViewById(R.id.selete1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
    }

    private void add() {
        TextView sdate = getView().findViewById(R.id.sdate);
        String sdateString = sdate.getText().toString().trim();

        selectPlanReportall(sdateString);
    }

    private void selectPlanReportall(String sdateString) {
        Call<List<PlanResult>> call = orderService.getPlanReport(sdateString);
        call.enqueue(new Callback<List<PlanResult>>() {
            @Override
            public void onResponse(Call<List<PlanResult>> call, Response<List<PlanResult>> response) {
                list = response.body();
                listView.setAdapter(new PlanResultAdpter(getActivity(),R.layout.frm_planresult_view,list));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<PlanResult>> call, Throwable t) {

            }
        });

    }

    private void plantViewController() {
//        FloatingActionButtonExpandable floatingActionButton = getView().findViewById(R.id.floatingActionButtonViewOrderResult);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                chart();
//            }
//        });
    }

    private void chart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_pie_chart);
        builder.setTitle("สถิตการวางแผนเพาะปลูก");
        //กำหนดหัวเเรื้อง
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.frm_view_orderresultchart, null);
        pieChart = view.findViewById(R.id.listViewOrderChartResult);

        //getGrowthChart();
        charController();
        charpdateController();

        builder.setView(view);
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void charpdateController() {
        final TextView edate = view.findViewById(R.id.sdate);
        ImageView selctDate = view.findViewById(R.id.imageViewDatesdate);
        selctDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //final Date cha = calendar.getTime();

                dataPickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                //date.setText(y + "/" + (m + 1) + "/" + d);
                                edate.setText(y + "/" + (m + 1) + "/" + d);
                                //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);

                            }
                        },year,month,day);
                //dataPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dataPickerDialog.show();
            }
        });
    }

    private void charController() {
        Button button = view.findViewById(R.id.selete1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartdate();

            }
        });
    }

    private void chartdate() {
        TextView sdate = view.findViewById(R.id.sdate);
        String sdateString = sdate.getText().toString().trim();

        getGrowthChart(sdateString);
    }

    private void getGrowthChart(String sdateString){
        //orderService = APIUtils.getService();
        Call<List<PlanResult>> call = orderService.getPlanReport(sdateString);
        call.enqueue(new Callback<List<PlanResult>>() {
            @Override
            public void onResponse(Call<List<PlanResult>> call, Response<List<PlanResult>> response) {
                if (response.body()!=null) {
                    List<PieEntry> pieEntries = new ArrayList<>();
                    for (PlanResult orderResultSum : response.body()){
                        pieEntries.add(new PieEntry(orderResultSum.getYield(),orderResultSum.getCrop()));

                    }
                    pieChart.setVisibility(View.VISIBLE);
                    pieChart.animateXY(5000,5000);

                    PieDataSet pieDataSet = new PieDataSet(pieEntries,"PlanResult");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.setCenterText("สถิติการวางแผน");


                    Description description = new Description();
                    description.setText("สถิตการวางแผนเพาะปลูก");
                    pieChart.setDescription(description);
                    pieChart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<List<PlanResult>> call, Throwable t) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_view_planresult, container, false);
        return view;
    }
}
