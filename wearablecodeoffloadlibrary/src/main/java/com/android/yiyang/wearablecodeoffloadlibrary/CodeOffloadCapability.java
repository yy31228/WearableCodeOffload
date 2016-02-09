package com.android.yiyang.wearablecodeoffloadlibrary;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Set;

/**
 * Created by Yi on 2/8/16.
 */

public class CodeOffloadCapability implements
        CapabilityApi.CapabilityListener {

    private static final String TAG = "WearableCodeOffloadCapacity";
    private static final String OFFLOAD_HANDLER_CAPABILITY_NAME = "code_offload_handler";

    private GoogleApiClient mGoogleApiClient;
    private Node mOffloadHandlerNode;

    public CodeOffloadCapability(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
        setupEchoHandlerNode();
    }

    public Node getNode() {
        return mOffloadHandlerNode;
    }

    public boolean isEmpty() {
        return mOffloadHandlerNode == null;
    }

    private void setupEchoHandlerNode() {
        Wearable.CapabilityApi.addCapabilityListener(
                mGoogleApiClient, this, OFFLOAD_HANDLER_CAPABILITY_NAME);

        Wearable.CapabilityApi.getCapability(
                mGoogleApiClient, OFFLOAD_HANDLER_CAPABILITY_NAME,
                CapabilityApi.FILTER_REACHABLE).setResultCallback(
                new ResultCallback<CapabilityApi.GetCapabilityResult>() {
                    @Override
                    public void onResult(CapabilityApi.GetCapabilityResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Log.e(TAG, "setupEchoHandlerNode() Failed to get capabilities, "
                                    + "status: " + result.getStatus().getStatusMessage());
                            return;
                        }
                        updateConfirmationCapability(result.getCapability());
                    }
                });
    }

    private void updateConfirmationCapability(CapabilityInfo capabilityInfo) {
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        if (connectedNodes.isEmpty()) {
            Log.w(TAG, "connectedNodes is empty");
            mOffloadHandlerNode = null;
        } else {
            mOffloadHandlerNode = pickBestNode(connectedNodes);
        }
    }

    private Node pickBestNode(Set<Node> connectedNodes) {
        Node best = null;
        if (connectedNodes != null) {
            for (Node node : connectedNodes) {
                if (node.isNearby()) {
                    return node;
                }
                best = node;
            }
        }
        return best;
    }

    @Override
    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        updateConfirmationCapability(capabilityInfo);
    }
}
