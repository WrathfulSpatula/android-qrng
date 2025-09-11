package com.example.android.anuqrng;

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

        final TextView tvCache = mView.findViewById(R.id.tvCache);
        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
        }
        tvCache.setText(String.format("%d bits in cache", bitCount));

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.sp_settings_key), Context.MODE_PRIVATE);
        int sd = sharedPref.getInt(getString(R.string.sp_space_deriv_key), 5);
        int tm = sharedPref.getInt(getString(R.string.sp_time_mult_key), 1);
        int rw = sharedPref.getInt(getString(R.string.sp_reset_wait_key), 2000);
        int cw = sharedPref.getInt(getString(R.string.sp_cancel_wait_key), 100);
        boolean doHash = sharedPref.getBoolean(getString(R.string.sp_do_hash_key), true);
        boolean doControl = sharedPref.getBoolean(getString(R.string.sp_do_control_key), false);


        final EditText etSpace = mView.findViewById(R.id.etSpace);
        final EditText etTimeMult = mView.findViewById(R.id.etTimeMult);
        final EditText etResetWait = mView.findViewById(R.id.etResetWait);
        final EditText etCancelWait = mView.findViewById(R.id.etCancelWait);
        final Switch swHash = mView.findViewById(R.id.swHash);
        final Switch swControl = mView.findViewById(R.id.swControl);

        etSpace.setText(String.format("%d", sd));
        etTimeMult.setText(String.format("%d", tm));
        etResetWait.setText(String.format("%d", rw));
        etCancelWait.setText(String.format("%d", cw));
        swHash.setChecked(doHash);
        swControl.setChecked(doControl);

        Button btnRequest = mView.findViewById(R.id.btn_save);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdStr = etSpace.getText().toString();
                String tmStr = etTimeMult.getText().toString();
                String rwStr = etResetWait.getText().toString();
                String cwStr = etCancelWait.getText().toString();
                boolean doHash = swHash.isChecked();
                boolean doControl = swControl.isChecked();
                int sd, tm, rw, cw;
                try {
                    sd = Integer.parseInt(sdStr);
                    tm = Integer.parseInt(tmStr);
                    rw = Integer.parseInt(rwStr);
                    cw = Integer.parseInt(cwStr);
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
                spEdit.putInt(getString(R.string.sp_time_mult_key), tm);
                spEdit.putInt(getString(R.string.sp_reset_wait_key), rw);
                spEdit.putInt(getString(R.string.sp_cancel_wait_key), cw);
                spEdit.putBoolean(getString(R.string.sp_do_hash_key), doHash);
                spEdit.putBoolean(getString(R.string.sp_do_control_key), doControl);
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