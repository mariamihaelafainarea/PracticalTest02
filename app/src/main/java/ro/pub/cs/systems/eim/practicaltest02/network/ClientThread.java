package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private int ora;
    private int minut;
    private String cerere;

    EditText raspuns;
    private Socket socket;

    public ClientThread(String clientAddress, int portAdress, String cerere, int ora, int minut, EditText raspuns){
        this.address = clientAddress;
        this.port = portAdress;
        this.cerere = cerere;
        this.ora = ora;
        this.minut= minut;

    }
    public ClientThread(String clientAddress,int portAdress,String cerere,EditText raspuns){
        this.address = clientAddress;
        this.port = portAdress;
        this.cerere = cerere;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e("Opa", "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e("Opa", "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(cerere);
            printWriter.flush();
            printWriter.println(ora);
            printWriter.flush();
            printWriter.println(minut);
            printWriter.flush();
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                final String fin_Result = result;
                raspuns.post(new Runnable() {
                    @Override
                    public void run() {
                        raspuns.setText(fin_Result);
                    }
                });
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}
