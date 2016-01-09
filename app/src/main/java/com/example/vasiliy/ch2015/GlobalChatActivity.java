package com.example.vasiliy.ch2015;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.vasiliy.autobahn.WebSocket;
import com.example.vasiliy.autobahn.WebSocketConnection;
import com.example.vasiliy.autobahn.WebSocketException;

import java.util.ArrayList;
import java.util.List;

public class GlobalChatActivity extends AppCompatActivity implements View.OnClickListener {


    List<MyMessage> messages;
    Button btnSend;
    EditText editText;
    MyUser user;
    AdapterMessageList messAdapter;
    //SocketClientAsyncTask socketClient;

    boolean isOpenWebSocket = false;

    private final WebSocketConnection mConnection = new WebSocketConnection();

    private final String wsuri = "ws://192.168.1.3:8080";

    ConnectionSupportAsyncTask connectionSupportAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);

        user = MyUser.getInstance(this);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

        ListView lvMessages = (ListView) findViewById(R.id.listView);

        messages = new ArrayList<>();

        //fillDatas();

        messAdapter = new AdapterMessageList(this, messages);

        lvMessages.setAdapter(messAdapter);

        connectionSupportAsyncTask = new ConnectionSupportAsyncTask();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (connectionSupportAsyncTask != null) {
            if (!connectionSupportAsyncTask.getStatus().toString().equals("RUNNING")) {
                connectionSupportAsyncTask.execute();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (connectionSupportAsyncTask != null) {
            connectionSupportAsyncTask.cancel(false);
        }
    }

    class ConnectionSupportAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {
                if (isCancelled()) {
                    mConnection.disconnect();
                    return null;
                }
                if (!isOpenWebSocket) {
                    try {
                        mConnection.connect(wsuri, new WebSocket.ConnectionHandler() {

                            @Override
                            public void onOpen() {
                                Log.d("QWERTY", "Status: Connected to " + wsuri);
                                //mConnection.sendTextMessage("Hello, world!");
                                isOpenWebSocket = true;
                            }

                            @Override
                            public void onTextMessage(String payload) {
                                Log.d("QWERTY", "Got echo: " + payload);
                                messages.add(new MyMessage(payload));
                                messAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onRawTextMessage(byte[] payload) {

                            }

                            @Override
                            public void onBinaryMessage(byte[] payload) {

                            }


                            @Override
                            public void onClose(int code, String reason) {
                                Log.d("QWERTY", "Connection lost.");
                                isOpenWebSocket = false;
                            }

                        });
                    } catch (WebSocketException e) {
                        Log.d("QWERTY", e.toString());
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    @Override
    public void onClick(View v) {
        if (isOpenWebSocket) {
            mConnection.sendTextMessage((new MyMessage(user.getName(), editText.getText().toString())).toJSON());
            editText.setText("");
        }
    }

}
