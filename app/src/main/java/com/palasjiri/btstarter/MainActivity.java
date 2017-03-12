package com.palasjiri.btstarter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.palasjiri.btstarter.bluetooth.BluetoothConnector;

public class MainActivity extends AppCompatActivity {

    Button startBtBtn;
    Button cancelBtBtn;

    BluetoothConnector bluetoothConnector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtBtn = (Button) findViewById(R.id.start_bt_btn);
        cancelBtBtn = (Button) findViewById(R.id.cancel_bt_btn);

        startBtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothConnector == null) {
                    bluetoothConnector = new BluetoothConnector();
                    bluetoothConnector.connect();
                }
            }
        });

        cancelBtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothConnector != null) {
                    bluetoothConnector.cancel();
                    bluetoothConnector = null;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
