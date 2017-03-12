package com.palasjiri.btstarter.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static com.palasjiri.btstarter.Constants.TAG;

public class ConnectThread extends Thread {

    private final BluetoothSocket btSocket;
    private final BluetoothDevice btDevice;
    private final BluetoothAdapter btAdapter;
    private final BluetoothActions btActions;

    public ConnectThread(BluetoothAdapter btAdapter, BluetoothDevice btDevice, UUID uuid, BluetoothActions btActions) {

        // Use a temporary object that is later assigned to btSocket,
        // because btSocket is final
        BluetoothSocket tmp = null;
        this.btDevice = btDevice;
        this.btAdapter = btAdapter;
        this.btActions = btActions;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = this.btDevice.createRfcommSocketToServiceRecord(uuid);
            tmp = this.btDevice.createInsecureRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) { }
        btSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        btAdapter.cancelDiscovery();
        try {
            // Connect the device through the btSocket. This will block
            // until it succeeds or throws an exception
            btSocket.connect();
        } catch (IOException connectException) {
            Log.e(TAG, "Unable to connect");
            // Unable to connect; close the btSocket and get out
            try {
                btSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        Log.i(TAG, "Successs");

        new ManageConnectionThread(btSocket, btActions).start();

    }

    /** Will cancel an in-progress connection, and close the btSocket */
    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) { }
    }
}
