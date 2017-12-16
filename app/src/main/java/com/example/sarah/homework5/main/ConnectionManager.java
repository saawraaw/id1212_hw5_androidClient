package com.example.sarah.homework5.main;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sarah on 12/15/2017.
 */

public class ConnectionManager {
    private static PrintWriter toServer ;
    private static BufferedReader fromServer ;
    private static Socket clientSocket ;


    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static PrintWriter getToServer() {
        return toServer;
    }


    public static BufferedReader getFromServer() {
        return fromServer;
    }


    public static void setClientSocket(Socket socket) {
        clientSocket = socket;
    }

    public static void setToServer(PrintWriter printWriter) {
        toServer = printWriter;
    }


    public static void setFromServer (BufferedReader bufferedReader){
        fromServer = bufferedReader;
    }


}
