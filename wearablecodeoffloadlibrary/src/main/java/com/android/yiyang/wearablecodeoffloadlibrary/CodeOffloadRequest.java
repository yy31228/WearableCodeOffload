package com.android.yiyang.wearablecodeoffloadlibrary;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Yi on 2/8/16.
 */
public class CodeOffloadRequest {
    public String mLocalIntent;
    public String mOffloadIntent;
    public boolean mForeground = true;
    public int mDelay = 0; //seconds
    public int mDataSize = 0; //Bytes
    public byte[] msg;

    public class Builder {
        private CodeOffloadRequest mCodeOffloadRequest;

        public Builder() {
            mCodeOffloadRequest = new CodeOffloadRequest();
        }

        public Builder setDelay(int delay) {
            mCodeOffloadRequest.mDelay = delay;
            return this;
        }

        public Builder setDataSize(int dataSize) {
            mCodeOffloadRequest.mDataSize = dataSize;
            return this;
        }

        public Builder setMsg(byte[] msg) {
            mCodeOffloadRequest.msg = msg;
            return this;
        }

        public CodeOffloadRequest build() {
            return mCodeOffloadRequest;
        }
    }

    public com.google.android.gms.common.api.PendingResult<com.google.android.gms.wearable.MessageApi.SendMessageResult>
    sendMessage(GoogleApiClient googleApiClient, Node node) {
        isForeGround();
        DataMap dataMap = new DataMap();
        dataMap.putString(CodeOffloadUtil.FLAG_LOCAL_INTENT, mLocalIntent);
        dataMap.putString(CodeOffloadUtil.FLAG_OFFLOAD_INTENT, mOffloadIntent);
        dataMap.putBoolean(CodeOffloadUtil.FLAG_FOREGROUNG, mForeground);
        dataMap.putInt(CodeOffloadUtil.FLAG_DELAY, mDelay);
        dataMap.putInt(CodeOffloadUtil.FLAG_DATA_SIZE, mDataSize);
        dataMap.putByteArray(CodeOffloadUtil.FLAG_MSG, msg);
        return Wearable.MessageApi.sendMessage(googleApiClient, node.getId(),
                CodeOffloadUtil.CODEOFFLOAD_PATH, dataMap.toByteArray());
    }

    private void isForeGround() {
        mForeground = true;
    }
}