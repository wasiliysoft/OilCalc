package ru.wasiliysoft.oilcalc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


class ResultAdapter extends BaseAdapter {

    private final LayoutInflater lInflater;
    private final Resources mRes;
    private final SharedPreferences mSettings;
    private float mPrice;
    private float mProportion;
    private float mConsumption;

    @Override
    public void notifyDataSetChanged() {
        initVars(mSettings, mRes);
        super.notifyDataSetChanged();
    }


    ResultAdapter(Activity activity) {
        Context mContext = activity.getApplicationContext();
        lInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRes = mContext.getResources();
        mSettings = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        initVars(mSettings, mRes);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = lInflater.inflate(R.layout.result_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.text3);
            viewHolder.t4 = (TextView) convertView.findViewById(R.id.text4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        float mPos = position + 1;

        float mFuel = mPos / (float) 2;

        viewHolder.t1.setText("" + mFuel) ;
        viewHolder.t2.setText("" + Round(mFuel *  (1000/mProportion)));
        viewHolder.t3.setText("" + Round(mFuel / (mConsumption / 100f)));
        viewHolder.t4.setText("" + Round(mFuel * mPrice));



        return convertView;
    }

    private static float Round(float f) {
        Log.d("Round in","" + f);
        int i = (int) (f * 10);
        Log.d("Round out","" + i/10f);
        return i / 10f;
    }

    private void initVars(SharedPreferences preferences, Resources resources) {
        mProportion = Float.valueOf(preferences
                .getString(
                        resources
                                .getString(R.string.pref_key_proportion),
                        resources
                                .getString(R.string.pref_default_proportion)));

        mPrice = Float.valueOf(preferences
                .getString(
                        resources
                                .getString(R.string.pref_key_price),
                        resources
                                .getString(R.string.pref_default_price)));

        mConsumption = Float.valueOf(preferences
                .getString(
                        resources
                                .getString(R.string.pref_key_consumption),
                        resources
                                .getString(R.string.pref_default_consumption)));

        Log.d(getClass().getSimpleName(), "" + mProportion);
        Log.d(getClass().getSimpleName(), "" + mPrice);
        Log.d(getClass().getSimpleName(), "" + mConsumption);
    }

    static class ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }

}



