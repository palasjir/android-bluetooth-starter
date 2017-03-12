package com.palasjiri.btstarter.bluetooth;


import java.io.InputStream;
import java.io.OutputStream;

public interface BluetoothActions {

    void execute(OutputStream outStream, InputStream inStream);

}
