package com.android.yiyang.wearablecodeoffloadservice;

import com.google.android.gms.wearable.DataMap;

/**
 * Created by Yi on 2/8/16.
 */
public class Task {
    public String mLocalIntent;
    public String mOffloadIntent;
    public boolean mForeground = true;
    public int mDelay = 0; //seconds
    public int mDataSize = 0; //Bytes
    public byte[] msg;

    public Task(byte[] data) {
        DataMap dataMap = DataMap.fromByteArray(data);
        mLocalIntent = dataMap.getString(CodeOffloadUtil.FLAG_LOCAL_INTENT);
        mOffloadIntent = dataMap.getString(CodeOffloadUtil.FLAG_OFFLOAD_INTENT);
        mForeground = dataMap.getBoolean(CodeOffloadUtil.FLAG_FOREGROUNG, true);
        mDelay = dataMap.getInt(CodeOffloadUtil.FLAG_DELAY);
        mDataSize = dataMap.getInt(CodeOffloadUtil.FLAG_DATA_SIZE);
        msg = dataMap.getByteArray(CodeOffloadUtil.FLAG_MSG);
    }
}
