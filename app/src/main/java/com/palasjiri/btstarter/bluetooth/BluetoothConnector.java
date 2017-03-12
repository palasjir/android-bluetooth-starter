package com.palasjiri.btstarter.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.palasjiri.btstarter.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static com.palasjiri.btstarter.utils.BluetoothUtils.findDeviceWithName;
import static com.palasjiri.btstarter.utils.BluetoothUtils.formatDeviceNames;

/**
 * @author palas
 */
public class BluetoothConnector {

    public static final String TAG = "BtConnector";

    public static final int n33H = 51;

    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    private ConnectThread connectThread;

    private String DEVICE_NAME = "70004 SRM TwinTrac";
    private BluetoothActions btActions = new BluetoothActions(){

        @Override
        public void execute(OutputStream outStream, InputStream inStream) {
            int current;
            int counter = 0;

            int[] bytesRead = new int[17];

            byte[] msg = new byte[8];
            msg[0] = (byte) 0xFC;
            msg[1] = (byte) 0;
            msg[2] = (byte) 0x20;

            Checksum checksum = new CRC32();
            checksum.update(msg, 1, 2);
            byte[] checksumBytes = ByteUtils.intToBytes((int) checksum.getValue());
            for(int i = 0; i < 4; i++) {
                msg[i + 3] = checksumBytes[i];
            }
            msg[7] = (byte) 0xFD;

            try {
                outStream.write(msg);
                outStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            do{
                try {
                    current = inStream.read();
                    inStream.reset();
                    bytesRead[counter++] = current;
                    Log.e(TAG, "Current:" + current);
                } catch (IOException e) {
                    Log.e(TAG, "At the end of the input stream!");
                    break;
                }
            }while (current != -1);
        }
    };


    public void connect() {
        BluetoothDevice device = findDeviceWithName(btAdapter, DEVICE_NAME);
        if (device != null) {
            connectThread = new ConnectThread(btAdapter, device, uuid, btActions);
            connectThread.start();
        } else {
            Log.e(TAG, "Bluetooth device not found! Did you mean: " + formatDeviceNames(btAdapter));
        }
    }

    public void cancel() {
        connectThread.cancel();
        connectThread = null;
    }

}
