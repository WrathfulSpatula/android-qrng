package com.example.android.qrng;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FloatFragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_float, container, false);

        mainActivity = (MainActivity)getActivity();

        final TextView tvCache = (TextView) mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize- RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(Integer.toString(bitCount) + " bits in cache");

        final TextView tvNumber = (TextView) mView.findViewById(R.id.tvNumber);

        Button floatBtn = (Button) mView.findViewById(R.id.btnFloat);
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bitCount = 0;
                if (RandSingleton.getInstance().randBools != null) {
                    bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
                }
                if (bitCount < 24) {
                    tvNumber.setText("Not enough bits in cache.");
                }
                else {
                    double value = 0.0;
                    double partSig = 0.5;
                    for (int i = 0; i < 24; i++) {
                        if (RandSingleton.getInstance().randBools.get(RandSingleton.getInstance().randBoolOffset)) {
                            value += partSig;
                        }
                        partSig /= 2;
                        RandSingleton.getInstance().randBoolOffset++;
                    }
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat format1 = new SimpleDateFormat("H:mm:ss");
                    String timeStr = format1.format(now.getTime());
                    tvNumber.setText(Double.toString(value) + " at " + timeStr);
                    tvCache.setText(Integer.toString(bitCount) + " bits in cache");
                }
            }
        });

        return mView;
    }
}
