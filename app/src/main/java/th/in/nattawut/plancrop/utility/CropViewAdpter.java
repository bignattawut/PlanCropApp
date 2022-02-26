package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import th.in.nattawut.plancrop.R;

public class CropViewAdpter extends BaseAdapter {

    private Context context;
    private String[] cidString,cropStrings,tidStrings,croptypeString,beginharvestStrings,harvestperiodStrings,yield;

    public CropViewAdpter(Context context,
                          String[] cidString,
                          String[] cropStrings,
                          String[] tidStrings,
                          String[] croptypeString,
                          String[] beginharvestStrings,
                          String[] harvestperiodStrings,
                          String[] yield) {
        this.context = context;
        this.cidString = cidString;
        this.cropStrings = cropStrings;
        this.croptypeString = croptypeString;
        this.tidStrings = tidStrings;
        this.beginharvestStrings = beginharvestStrings;
        this.harvestperiodStrings = harvestperiodStrings;
        this.yield = yield;
    }

    @Override
    public int getCount() {
        return cropStrings.length;
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
        View view = layoutInflater.inflate(R.layout.frm_crop_view, parent, false);

        TextView textCidView = view.findViewById(R.id.textCid);
        TextView textCropView = view.findViewById(R.id.textCrop);
        TextView textTidView = view.findViewById(R.id.textCropTID);
        TextView textCropTidView = view.findViewById(R.id.textCropTypeTID);
        TextView textBeginHarvestView = view.findViewById(R.id.textBeginHarvest);
        TextView textHarvestPeriodView = view.findViewById(R.id.textHarvestPeriod);
        TextView textYieldView = view.findViewById(R.id.textYield);

        textCidView.setText(cidString[position]);
        textCropView.setText(cropStrings[position]);
        textTidView.setText(tidStrings[position]);
        textCropTidView.setText(croptypeString[position]);
        textBeginHarvestView.setText(beginharvestStrings[position]);
        textHarvestPeriodView.setText(harvestperiodStrings[position]);
        textYieldView.setText(yield[position]);

        return view;
    }
}
