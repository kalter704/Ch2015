package com.example.vasiliy.ch2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Temp123Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp123);
    }
}


////////// GlobalChatActivity

    /*
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




    @Override
    public void onClick(View v) {
        MyMessage myMessage = new MyMessage("User", editText.getText().toString());
        try {
            Log.d("QWERTY", "myMessage = " + myMessage.toJSON());
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
            InetAddress serverAddr = InetAddress.getByName("192.168.1.3");
            socket = new Socket(serverAddr, 8080);
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
            if (!isSend) {
                InetAddress serverAddr = null;
                try {
                    serverAddr = InetAddress.getByName("192.168.1.3");

                    socket = new Socket(serverAddr, 8080);
                    //socket = new Socket("globalchat", 8080, InetAddress.getAllByName("192.168.1.2")[0], 8080);
                    socket.setKeepAlive(true);

                    in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(messForSend);
                    out.flush();
                    isSend = true;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/

        /*
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
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.disconnect();
            }
        }
        */
    /*

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
*/

////////// GlobalChatActivity End