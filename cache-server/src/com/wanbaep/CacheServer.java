package com.wanbaep;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class CacheServer {
    static int portNum = 40000;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(portNum);
            while (true) {
                System.out.println("I'm waiting");
                //thread?

                clientSocket = serverSocket.accept();
                System.out.println("Accept Client Address: " + clientSocket.getInetAddress());
                ServerThread serverThread = new ServerThread(clientSocket);
                Thread thread = new Thread(serverThread);
                thread.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
