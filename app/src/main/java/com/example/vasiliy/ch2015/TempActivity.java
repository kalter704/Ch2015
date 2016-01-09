package com.example.vasiliy.ch2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TempActivity extends AppCompatActivity {

    public static String SERVERIP = "192.168.1.3"; //your computer IP address
    public static int SERVERPORT = 8080;
    String message;
    String bufer;
    PrintWriter out;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button) findViewById(R.id.btn);
        final Button conn = (Button) findViewById(R.id.cnbtn);
        final EditText ips = (EditText) findViewById(R.id.ipstr);
        final EditText ports = (EditText) findViewById(R.id.portstr);

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SERVERIP = ips.getText().toString();
                //SERVERPORT = Integer.parseInt(ports.getText().toString());
                conn.setEnabled(false);
                mThr.start();
            }
        });

        //mThr.start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = editText.getText().toString();
                Log.e("TCP Client", "Send Button");
            }
        });
    }
    Thread mThr = new Thread(new Runnable() {
        @Override
        public void run() {
            InetAddress serverAddr;
            Socket socket = null;
            Log.e("TCP Client", "C: Connecting...");
            while(true){
                if(bufer == message){

                }
                else{
                    try{
                        serverAddr = InetAddress.getByName(SERVERIP);
                        //socket = new Socket(serverAddr, SERVERPORT);
                        socket = new Socket(serverAddr, 8080);
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                        out.println(message);
                        out.flush();
                        bufer = message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try{
                        assert socket != null;
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    });
}
