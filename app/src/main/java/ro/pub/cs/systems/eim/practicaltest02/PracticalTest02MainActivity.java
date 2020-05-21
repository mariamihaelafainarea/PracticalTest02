package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class PracticalTest02MainActivity extends AppCompatActivity {


    EditText portServer = null;
    EditText portClient = null;
    EditText clientAdress = null;
    EditText ora = null;
    EditText minut = null;
    Button seteazaAlarma = null;
    Button reseteazaAlarma = null;
    Button verificaAlarma = null;
    EditText raspunsServer = null;
    Button connectButton = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        portServer  = findViewById(R.id.server_port_edit_text);
        portClient = findViewById(R.id.client_port_edit_text);
        clientAdress = findViewById(R.id.server_adresa_edittext);
        ora = findViewById(R.id.ora_edittext);
        minut = findViewById(R.id.minut_edittext);
        seteazaAlarma = findViewById(R.id.seteaza_buton);
        reseteazaAlarma = findViewById(R.id.reseteaza_buton);
        verificaAlarma = findViewById(R.id.poll_buton);
        raspunsServer = findViewById(R.id.raspuns_server);
        connectButton = findViewById(R.id.connect_button);

        connectButton.setOnClickListener(connectButtonClickListener);
        seteazaAlarma.setOnClickListener(setAlarmClickListener);
        reseteazaAlarma.setOnClickListener(resetAlarmClickListener);
        verificaAlarma.setOnClickListener(verifyAlarmClickListener);




    }


    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = portServer.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e("Opa", "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }
    private SetAlarmClickListener setAlarmClickListener = new SetAlarmClickListener();
    private class SetAlarmClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAdress.getText().toString();
            String clientPort = portClient.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String ora_value=  ora.getText().toString();
            String minut_value = minut.getText().toString();
            if (ora_value == null || minut_value == null){
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            raspunsServer.setText("");
            clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), "set",Integer.parseInt(ora_value), Integer.parseInt(minut_value),raspunsServer);
            clientThread.start();
        }

    }

    private ResetAlarmClickListener resetAlarmClickListener = new ResetAlarmClickListener();
    private class ResetAlarmClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAdress.getText().toString();
            String clientPort = portClient.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }

            raspunsServer.setText("");
            clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort),"reset",raspunsServer);
            clientThread.start();
        }

    }

    private verifyAlarmClickListener verifyAlarmClickListener = new verifyAlarmClickListener();
    private class verifyAlarmClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAdress.getText().toString();
            String clientPort = portClient.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort),"poll",raspunsServer);
            clientThread.start();
        }
    }
}
