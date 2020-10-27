package com.example.android.qrng;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {
    public static int SaveToDisk() {
        int toRet = 0;

        int bitCount = 0;
        if (RandSingleton.getInstance().randBools != null) {
            bitCount = RandSingleton.getInstance().randSize - RandSingleton.getInstance().randBoolOffset;
        }
        int byteCount = bitCount / 8;
        byte[] outputBytes = new byte[byteCount];
        for (int j = 0; j < byteCount; j++) {
            outputBytes[j] = 0;
            for (int i = 0; i < 8; i++) {
                if (RandSingleton.getInstance().randBools.get(RandSingleton.getInstance().randBoolOffset)) {
                    outputBytes[j] |= 1 << i;
                }
                RandSingleton.getInstance().randBoolOffset++;
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            File mDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/qrng");
            mDir.mkdirs();
            File mFile = new File(mDir, "q" + Long.toString(System.currentTimeMillis()).toString() + ".rnd");
            mFile.createNewFile();
            fileOutputStream = new FileOutputStream(mFile);
            fileOutputStream.write(outputBytes);
        } catch (FileNotFoundException e) {
            toRet = 1;
            Log.d("QRNG", "Could not create output file.");
            e.printStackTrace();
        } catch (IOException e) {
            toRet = 1;
            Log.d("QRNG", "Could not write to output file:");
            e.printStackTrace();
        }

        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            toRet = 1;
            Log.d("QRNG", "Could not close output file.");
            e.printStackTrace();
        }

        RandSingleton.getInstance().randBools = null;
        RandSingleton.getInstance().randBoolOffset = 0;

        return  toRet;
    }
}
