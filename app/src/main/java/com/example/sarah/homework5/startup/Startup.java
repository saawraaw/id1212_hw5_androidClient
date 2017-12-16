package com.example.sarah.homework5.startup;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sarah.homework5.R;
import com.example.sarah.homework5.main.ConnectionManager;
import com.example.sarah.homework5.main.TCPClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

public class Startup extends AppCompatActivity implements Serializable {
    private transient Socket clientSocket ;
    private transient BufferedReader fromServer ;
    private transient PrintWriter toServer ;
    private Boolean connected  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_startup);
        setupActivity();
    }

    private void setupActivity(){
        final Button button = (Button) findViewById(R.id.connect_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(v.getContext().CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                   new TCPConnection().execute();
                } else {
                    showErrorMessage(getString(R.string.no_connection), getString(R.string.no_internet));
                }
            }
        });

    }

    private void showErrorMessage (String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Startup.this);
        builder.setMessage(msg)
                .setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Socket getSocket () {
        return clientSocket;
    }

    public BufferedReader getInputBuffer () {
        return fromServer;
    }

    public PrintWriter getOutputBuffer () {
        return toServer;
    }




    private class TCPConnection extends AsyncTask <Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(this.getClass().toString() , "In Do in background");
            try {
                clientSocket = new Socket ("192.168.1.156" , 3333);
                fromServer = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                toServer = new PrintWriter(clientSocket.getOutputStream(),true);
                connected = true ;
            } catch (Exception e) {
                connected = false;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(connected) {
                ConnectionManager.setClientSocket(clientSocket);
                ConnectionManager.setFromServer(fromServer);
                ConnectionManager.setToServer(toServer);
                Intent intent = new Intent(Startup.this, TCPClient.class);
                startActivity(intent);

            } else {
                showErrorMessage(getString(R.string.no_connection) , getString(R.string.could_not_connect));
            }
        }

    }
}

