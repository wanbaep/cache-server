package com.wanbaep;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class ServerThread implements Runnable {
    private Socket socket;
    private Request request;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.print("[" + Thread.currentThread().getName() + "] - " + Thread.currentThread().getId() + " Thread ");

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;

            StringBuffer requestBuffer = new StringBuffer();
            int port = 0;
            byte[] buf = new byte[512];
            int count;

            InputStream in = socket.getInputStream();

            String byteToString = null;
            int first = 0;
            while ((count = in.read(buf)) != -1) {
                byteToString = new String(buf, 0, count);
                requestBuffer.append(byteToString);
//                System.out.println(byteToString);
            }

            String requestString = requestBuffer.toString();
            String parsing[] = requestBuffer.toString().split("\n");
            String toSplit[] = parsing[0].split(" ");

            Request request = new Request();
            request.setMethod(toSplit[0]);
            request.setUri(toSplit[1]);
            request.setHttpVersion(toSplit[2]);

            for (int i = 1; i < parsing.length; i++) {
                String header[] = parsing[i].split(": ");
                if (header.length == 2) {
                    request.setHeader(header[0], header[1]);
                } else {
                    System.out.println("header is wrong");
                }
            }

            System.out.print(request.getMethod() + " " + request.getHeader().get("Host") + " : ");

            sendHttpsGet(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHttpsGet(Request request) {
        URL url = null;

        try {

            url = new URL(request.getUri());
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            // Set Hostname verification
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // Ignore host name verification. It always returns true.
                    return true;
                }

            });

            // SSL setting
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);  // No validation for now
            conn.setSSLSocketFactory(context.getSocketFactory());

            // Connect to host
            conn.connect();
            conn.setInstanceFollowRedirects(true);

            InputStream inn = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inn));
            String li = null;
            while ((li = reader.readLine()) != null) {
//                System.out.print(" Reading Response ");
//                System.out.printf("%s\n", li);
            }
            System.out.println(" Reading Response Done");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
