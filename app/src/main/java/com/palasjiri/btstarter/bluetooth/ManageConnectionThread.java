package com.palasjiri.btstarter.bluetooth;


import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManageConnectionThread extends Thread{

    private final BluetoothSocket socket;
    private final InputStream inStream;
    private final OutputStream outStream;
    private final BluetoothActions btActions;

    public ManageConnectionThread(BluetoothSocket socket, BluetoothActions btActions){
        this.socket = socket;
        this.btActions = btActions;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        // Get the input and output streams, using temp objects because member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        inStream = tmpIn;
        outStream = tmpOut;
    }

    @Override
    public void run() {

        btActions.execute(outStream, inStream);

        try {
            inStream.close();
        } catch (IOException e) {}

        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
