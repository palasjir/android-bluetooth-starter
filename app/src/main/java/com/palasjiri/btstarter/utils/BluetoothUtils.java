package com.palasjiri.btstarter.utils;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothUtils {

    public static BluetoothDevice findDeviceWithName(BluetoothAdapter ba, String name) {
        if(name == null){
            return null;
        }
        Set<BluetoothDevice> pairedDevices = ba.getBondedDevices();
        for (BluetoothDevice pairedDevice : pairedDevices) {
            String deviceName = pairedDevice.getName();
            if(deviceName.equals(name)) {
                return pairedDevice;
            }
        }
        return null;
    }

    public static List<String> getDeviceNames(BluetoothAdapter ba) {
        List<String> names = new ArrayList<>();
        for ( BluetoothDevice d : ba.getBondedDevices()) {
            names.add(d.getName());
        }
        return names;
    }

    public static String formatDeviceNames(BluetoothAdapter ba) {
        List<String> names = getDeviceNames(ba);
        String str = "[ ";
        for(String name: names){
            str += "\"" + name + "\"";
        }
        str += " ]";
        return str;
    }

}
