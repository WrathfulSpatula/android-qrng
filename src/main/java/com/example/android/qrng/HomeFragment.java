package com.example.android.qrng;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;;

import java.io.IOException;

import static com.example.android.qrng.MainActivity.GET_BOOL_ARRAY;

public class HomeFragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        mainActivity = (MainActivity)getActivity();

        final TextView tvCache = (TextView) mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randBools.length - RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(Integer.toString(bitCount) + " bits in cache");

        final EditText etBitCount = (EditText) mView.findViewById(R.id.etBitCount);
        etBitCount.setText("4096");

        Button btnRequest = (Button) mView.findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bitCount = 0;
                String bitCountStr = etBitCount.getText().toString();
                try {
                    bitCount = Integer.parseInt(bitCountStr);
                }
                catch (Error e) {
                    return;
                }

                int byteCount = (bitCount + 7) / 8;
                new RetrieveBytesTask().execute(byteCount);
                tvCache.setText(Integer.toString(bitCount) + " bits in cache");
            }
        });

        Button btnPurify = (Button) mView.findViewById(R.id.btn_purify);
        btnPurify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] tempRandBools = new boolean[RandSingleton.getInstance().randBools.length / 2];

                // Bit derivatives
                for (int i = 0; i < tempRandBools.length; i++) {
                    tempRandBools[i] = RandSingleton.getInstance().randBools[i * 2] ^ RandSingleton.getInstance().randBools[i * 2 + 1];
                }

                RandSingleton.getInstance().randBools = tempRandBools;
                RandSingleton.getInstance().randBoolOffset /= 2;

                tvCache.setText(Integer.toString(tempRandBools.length) + " bits in cache");
            }
        });

        return mView;
    }

    class RetrieveBytesTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... byteCounts) {
            int byteCount = byteCounts[0];
            boolean[] anuBits = new boolean[byteCount * 8];
            AnuRandom anuRandom = new AnuRandom(byteCount);
            byte[] anuBytes = null;
            try {
                anuBytes = anuRandom.getBytesSafe();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error getting bits.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return null;
            }

            for (int i = 0; i < anuBytes.length; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((anuBytes[i] & (1 << j)) > 0) {
                        anuBits[(i * 8) + j] = true;
                    } else {
                        anuBits[(i * 8) + j] = false;
                    }
                }
            }

            RandSingleton.getInstance().randBools = anuBits;
            RandSingleton.getInstance().randBoolOffset = 0;

            return null;
        }
    }
}