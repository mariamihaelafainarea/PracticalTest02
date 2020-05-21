package ro.pub.cs.systems.eim.practicaltest02.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class CommunicationThread  extends AsyncTask<String,String,String> {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    protected String doInBackground(String... params) {
        Socket socket = null;

        try{
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e("opa", "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
            }
            String cerere = bufferedReader.readLine();
            if(cerere.equals("seteaza")){
                String ora = bufferedReader.readLine();
                String minut = bufferedReader.readLine();
                Log.e("opa", "[COMMUNICATION THREAD] Cerere de setare alarma" + ora + " " + minut);

            }else if(cerere.equals("reseteaza")){

                Log.e("opa", "[COMMUNICATION THREAD] Cerere de resetare alarma");

            }else if(cerere.equals("verifica")){

                Log.e("opa", "[COMMUNICATION THREAD]Cerere de verificare");


            }

        }catch(Exception e){

            e.printStackTrace();
        }
        return "";
    }

}
