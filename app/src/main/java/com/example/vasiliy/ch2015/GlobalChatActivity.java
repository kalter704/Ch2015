package com.example.vasiliy.ch2015;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GlobalChatActivity extends AppCompatActivity implements View.OnClickListener {

    String[] temp = {
            "обязательно надо сохранить в данных о товаре то, что он теперь в корзине.",
            "овых пунктов. Нам остается только заполнить их данными. Это значительно ускоряет работу приложения, т.к. не надо прогонять inflate лишний раз.",
            "Тут важно понимать, что без этого обработчика",
            "lyflyfhfgj",
            "cfgjjhvgfj",
            "сделаем кнопку, которая будет отобра",
            "vzxcvcx",
            "лезны, но иногда их возможностей не хватает",
            "ажать содержимое корзины. В настоящем интернет-магазине мы повесили бы на ",
            "Например, при прокрутке списка, часть пунктов уходит за экран и их уже не надо прорисовывать. View из этих «невидимых» пунктов используются для новых пунктов.",
            "Метод getBox проверяет, какие товары отмечены галками и формирует из них коллекц",
            "jklh98yjho98y",
            "hjero89gergo",
            "hjerhg8er",
            "efe3wr3rw3r",
            "ouwehfw8efow3ef"
    };

    String[] names = {
            "Иван",
            "Марья",
            "Петр",
            "Антон",
            "Даша",
            "Борис",
            "Костя",
            "Игорь",
            "Анна",
            "Денис",
            "Андрей"
    };

    List<MyMessage> messages;
    Button btnSend;
    EditText editText;
    MyUser user;
    AdapterMessageList messAdapter;
    SocketClientAsyncTask socketClient;

    String ip = "192.168.1.2";
    String port = "8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);

        user = MyUser.getInstance();

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

        ListView lvMessages = (ListView) findViewById(R.id.listView);

        messages = new ArrayList<>();

        //fillDatas();

        messAdapter = new AdapterMessageList(this, messages);

        lvMessages.setAdapter(messAdapter);

        socketClient = new SocketClientAsyncTask(ip, port);
        socketClient.execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (socketClient != null) {
            if (!socketClient.getStatus().toString().equals("RUNNING")) {
                socketClient.execute();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (socketClient != null) {
            socketClient.cancel(false);
        }
    }


    private void fillDatas() {
        for (int i = 0; i < names.length; ++i) {
            messages.add(new MyMessage(names[i], temp[i]));
        }
    }

    @Override
    public void onClick(View v) {
        MyMessage myMessage = new MyMessage("User", editText.getText().toString());
        try {
            socketClient.sendMessage(URLEncoder.encode(myMessage.toJSON(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void newMess(String mess) {
        messages.add(new MyMessage(mess));
        messAdapter.notifyDataSetChanged();
    }

    class SocketClientAsyncTask extends AsyncTask<Void, String, Void> {

        private Socket socket = null;
        private PrintWriter out;
        private BufferedReader in;
        private String ip;
        private String port;
        String messForSend = null;
        String messWhichGet = null;
        boolean isSend = true;
        boolean isGet = false;

        public SocketClientAsyncTask(String ip, String port) {
            this.ip = ip;
            this.port = port;
        }

        private boolean connect() {
            try {
                //InetSocketAddress
                socket = new Socket(ip, Integer.parseInt(port));
                //socket = new Socket("globalchat", 8080, InetAddress.getAllByName("192.168.1.2")[0], 8080);
                socket.setKeepAlive(true);

                in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        private void disconnect() {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean isNotWork() {
            if (socket != null) {
                if (socket.isClosed()) {
                    return true;
                }
                if (!socket.isConnected()) {
                    return true;
                }
            }
            return false;
        }

        protected void onProgressUpdate(String... params) {
            newMess(params[0]);
        }

        public void sendMessage(String mess) {
            messForSend = mess;
            isSend = false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {
                this.connect();

                while (true) {
                    if (isCancelled()) return null;

                    if (isNotWork()) {
                        break;
                    }

                    StringBuilder sb = new StringBuilder();
                    String str = null;
                    try {
                        while (!socket.isClosed() && (str = in.readLine()) != null) {
                            sb.append(str);
                            isGet = true;
                        }
                        messWhichGet = sb.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }

                    if (isGet) {
                        isGet = false;
                        publishProgress();
                    }

                    if (!isSend) {
                        isSend = true;
                        out.println(messForSend);
                        out.flush();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.disconnect();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            this.disconnect();

        }

    }


}
