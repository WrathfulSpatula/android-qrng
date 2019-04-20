package com.example.android.anuqrng;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PermuteFragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;

    public PermuteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_permute, container, false);

        mainActivity = (MainActivity)getActivity();

        final TextView tvCache = (TextView) mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(Integer.toString(bitCount) + " bits in cache");

        final TextView tvNumber = (TextView) mView.findViewById(R.id.tvNumber);

        final EditText etOptionCount = (EditText) mView.findViewById(R.id.etOptionCount);
        etOptionCount.setText("78");

        Button btnPermute = (Button) mView.findViewById(R.id.btnPermute);
        btnPermute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int optionCount = 2;
                String optionCountStr = etOptionCount.getText().toString();
                try {
                    optionCount = Integer.parseInt(optionCountStr);
                }
                catch (Error e) {
                    tvNumber.setText("Enter an integer.");
                    return;
                }

                double value = 0.0;
                int bitCount = 0;

                if ((RandSingleton.getInstance().randBools == null) ||
                        (optionCount > 2 && ((RandSingleton.getInstance().randSize - 24) < RandSingleton.getInstance().randBoolOffset)) ||
                        (RandSingleton.getInstance().randSize <= RandSingleton.getInstance().randBoolOffset)
                ) {
                    tvNumber.setText("Not enough bits in cache.");
                    return;
                }

                bitCount = RandSingleton.getInstance().randSize- RandSingleton.getInstance().randBoolOffset;

                if (optionCount == 2) {
                    value = RandSingleton.getInstance().randBools.get(RandSingleton.getInstance().randBoolOffset) ? 1.0 : 0.0;
                    RandSingleton.getInstance().randBoolOffset++;
                }
                else if (bitCount < 24) {
                    tvNumber.setText("Not enough bits in cache.");
                    return;
                }
                else {
                    double partSig = 0.5;
                    for (int i = 0; i < 24; i++) {
                        if (RandSingleton.getInstance().randBools.get(RandSingleton.getInstance().randBoolOffset)) {
                            value += partSig;
                        }
                        partSig /= 2;
                        RandSingleton.getInstance().randBoolOffset++;
                    }
                }

                bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;

                int selected = (int)(optionCount * value) + 1;
                if (selected > optionCount) {
                    selected = optionCount;
                }

                Calendar now = Calendar.getInstance();
                SimpleDateFormat format1 = new SimpleDateFormat("H:mm:ss");
                String timeStr = format1.format(now.getTime());
                tvNumber.setText(Integer.toString(selected) + " / " + Integer.toString(optionCount) + " at " + timeStr);
                tvCache.setText(Integer.toString(bitCount) + " bits in cache");
                if (optionCount > 2) {
                    etOptionCount.setText(Integer.toString(optionCount - 1));
                }
            }
        });

        return mView;
    }
}
