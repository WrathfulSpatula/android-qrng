package com.example.android.qrng;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

;

public class SettingsFragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);

        mainActivity = (MainActivity) getActivity();

        final TextView tvCache = (TextView) mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(Integer.toString(bitCount) + " bits in cache");

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.sp_settings_key), Context.MODE_PRIVATE);
        int sd = sharedPref.getInt(getString(R.string.sp_space_deriv_key), 5);
        int td = sharedPref.getInt(getString(R.string.sp_time_deriv_key), 1);
        int tm = sharedPref.getInt(getString(R.string.sp_time_mult_key), 1);
        boolean doHash = sharedPref.getBoolean(getString(R.string.sp_do_hash_key), true);


        final EditText etSpace = (EditText) mView.findViewById(R.id.etSpace);
        final EditText etTime = (EditText) mView.findViewById(R.id.etTime);
        final EditText etTimeMult = (EditText) mView.findViewById(R.id.etTimeMult);
        final Switch swHash = (Switch) mView.findViewById(R.id.swHash);

        etSpace.setText(Integer.toString(sd));
        etTime.setText(Integer.toString(td));
        etTimeMult.setText(Integer.toString(tm));
        swHash.setChecked(doHash);

        Button btnRequest = (Button) mView.findViewById(R.id.btn_save);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdStr = etSpace.getText().toString();
                String tdStr = etTime.getText().toString();
                String tmStr = etTimeMult.getText().toString();
                boolean doHash = swHash.isChecked();
                int sd, td, tm;
                try {
                    sd = Integer.parseInt(sdStr);
                    td = Integer.parseInt(tdStr);
                    tm = Integer.parseInt(tmStr);
                }
                catch (Error e) {
                    showToast("Invalid values");
                    return;
                }

                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.sp_settings_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sharedPref.edit();
                spEdit.putInt(getString(R.string.sp_space_deriv_key), sd);
                spEdit.putInt(getString(R.string.sp_time_deriv_key), td);
                spEdit.putInt(getString(R.string.sp_time_mult_key), tm);
                spEdit.putBoolean(getString(R.string.sp_do_hash_key), doHash);
                spEdit.commit();

                showToast("Updated settings");
            }
        });

        return mView;
    }

    private void showToast(final String text) {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}