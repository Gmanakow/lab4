package com.manakov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProcess implements Runnable{

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerProcess(Socket socket) throws Throwable{
        this.socket = socket;

        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void run(){
        try{
            readInputHeaders();
            writeResponse("<html><body><h1>Hello World</h1></body></html>");
        } catch (Throwable throwable){
            // empty
        } finally {
            try{
                socket.close();
            } catch ( Throwable throwable){
                // empty
            }
        }
    }

    public void writeResponse(String s) throws Throwable{
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    public void readInputHeaders() throws Throwable{
        boolean trigger = true;

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream)
        );

        while(true) {
            String s = bufferedReader.readLine();
            if (trigger) {
                System.out.println(s);
                trigger = false;
            }
            if (s == null || s.trim().length() == 0){
                break;
            }
        }
    }

}
