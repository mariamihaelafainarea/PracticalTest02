package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;
import ro.pub.cs.systems.eim.practicaltest02.model.Alarm;

public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket serverSocket = null;

    private HashMap<String, Alarm> data = null;

    public ServerThread(int port) {
        this.port = port;

        Log.d("opa","portul este" + port);
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
        }
        this.data = new HashMap<>();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public synchronized void setData(String ip, Alarm alarm) {
        this.data.put(ip, alarm);
    }

    public synchronized HashMap<String, Alarm> getData() {
        return data;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("Opa", "[SERVER THREAD] Waiting for a client invocation...");

                Socket socket = serverSocket.accept();
                Log.i("Opa", "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
//                CommunicationThread communicationThread = new CommunicationThread(this, socket);
//                communicationThread.execute(socket);

                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);

                if (bufferedReader == null || printWriter == null) {
                    return;
                }
                String cerere = bufferedReader.readLine();
                if(cerere.equals("seteaza")){
                    String ora = bufferedReader.readLine();
                    String minut = bufferedReader.readLine();
                    Log.i("opa", "[COMMUNICATION THREAD] Cerere de setare alarma" + ora + " " + minut);

                }else if(cerere.equals("reseteaza")){

                    Log.i("opa", "[COMMUNICATION THREAD] Cerere de resetare alarma");

                }else if(cerere.equals("verifica")){

                    Log.i("opa", "[COMMUNICATION THREAD]Cerere de verificare");


                }
            }
        } catch (IOException ioException) {
                ioException.printStackTrace();
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
