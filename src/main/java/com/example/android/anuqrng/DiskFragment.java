package com.example.android.anuqrng;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.android.anuqrng.MainActivity.GET_BOOL_ARRAY;

public class DiskFragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;

    public DiskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_disk, container, false);

        mainActivity = (MainActivity)getActivity();

        final TextView tvCache = (TextView) mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(Integer.toString(bitCount) + " bits in cache");

        Button btnDisk = (Button) mView.findViewById(R.id.btn_disk);
        btnDisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.SaveToDisk();
                tvCache.setText("0 bits in cache");
            }
        });

        final EditText etBitCount = (EditText) mView.findViewById(R.id.etBitCount);
        etBitCount.setText("1006632960");

        Button btnDiskRepeat = (Button) mView.findViewById(R.id.btn_disk_repeat);
        btnDiskRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long bitCount = 0;
                String bitCountStr = etBitCount.getText().toString();
                try {
                    bitCount = Long.parseLong(bitCountStr);
                }
                catch (Error e) {
                    return;
                }

                Intent cameraIntent = new Intent(getActivity(), CameraActivity.class);
                // We are not automatically generating a fixed length, so set the default argument.
                cameraIntent.putExtra("GENERATE_LENGTH", bitCount);
                startActivityForResult(cameraIntent, GET_BOOL_ARRAY);

            }
        });

        return mView;
    }
}