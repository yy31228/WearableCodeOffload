package com.android.yiyang.wearablecodeoffloadservice;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Yi on 2/8/16.
 */

public class CodeOffloadSolver {
    private static CodeOffloadSolver codeOffloadSolver;
    private Context mContext;

    private CodeOffloadSolver(Context context) {mContext = context;}

    public static CodeOffloadSolver getSolver(Context context) {
        if(codeOffloadSolver == null)
            codeOffloadSolver = new CodeOffloadSolver(context);
        return codeOffloadSolver;
    }

    public void addTask(byte[] data) {
        Task task = new Task(data);
        solve(task);
    }

    protected void solve(Task task) {
        startTask(task, false);
    }

    protected void startTask(Task task, boolean offload) {
        Intent startIntent;
        if(offload) {
            startIntent = new Intent(task.mOffloadIntent);
        }else {
            startIntent = new Intent(task.mLocalIntent);
            startIntent.putExtra(CodeOffloadUtil.FLAG_FOREGROUNG, task.mForeground);
        }
        startIntent.putExtra(CodeOffloadUtil.FLAG_MSG, task.msg);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(startIntent);
    }
}
