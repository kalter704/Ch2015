package com.example.vasiliy.ch2015;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String url = "http://ukkk6aaa625d.kalter704.koding.io:8080/";
        //String url = "http://192.168.1.2:8080";

        ((Button) findViewById(R.id.btnToGlobalChat)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnToChangeName)).setOnClickListener(this);
        /*
        ((Button) findViewById(R.id.btnToTemp)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnToServerSide)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnToClientSide)).setOnClickListener(this);
        */

        //new MyAsyncTask().execute(url);

        if (MyUser.getInstance(this).getName() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        ((TextView) findViewById(R.id.tvHello)).setText("Привет " + MyUser.getName());

    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d("QWERTY", "params[0] = " + params[0]);
            String temp = myRequest(params[0]);
            Log.d("QWERTY", "temp = " + temp);
            return parseFromJSON(temp);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("QWERTY", "response = " + result);

            ((TextView) findViewById(R.id.textView)).setText(result);
        }
    }

    protected String myRequest(String urlString) {
        BufferedReader reader = null;
        Log.d("QWERTY", "urlString = " + urlString);
        try {
            URL url = new URL(urlString);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoInput(true);
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));

            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            c.disconnect();
            return (buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String parseFromJSON(String resourse) {
        JSONObject dataJsonObject;
        String response = null;
        try {
            dataJsonObject = new JSONObject(resourse);
            response = dataJsonObject.getString("namePerem");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnToGlobalChat:
                intent = new Intent(MainActivity.this, GlobalChatActivity.class);
                break;
            case R.id.btnToChangeName:
                intent = new Intent(MainActivity.this, LoginActivity.class);
            /*
            case R.id.btnToTemp:
                intent = new Intent(MainActivity.this, TempActivity.class);
                break;
            case R.id.btnToClientSide:
                intent = new Intent(MainActivity.this, ClientSideActivity.class);
                break;
            case R.id.btnToServerSide:
                intent = new Intent(MainActivity.this, ServerSideActivity.class);
                break;
                */
        }
        startActivity(intent);
    }
}
