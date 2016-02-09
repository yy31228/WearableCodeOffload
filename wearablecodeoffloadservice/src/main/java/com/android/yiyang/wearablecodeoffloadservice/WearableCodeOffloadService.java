package com.android.yiyang.wearablecodeoffloadservice;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WearableCodeOffloadService extends WearableListenerService {

    private static final String TAG = "CodeOffloadService";

    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(CodeOffloadUtil.CODEOFFLOAD_PATH)) {
            CodeOffloadSolver.getSolver(this).addTask(messageEvent.getData());
        }
    }

}