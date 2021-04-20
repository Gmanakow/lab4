package com.manakov;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App 
{
    public static void main( String[] args )
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        try{
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true) {
                Socket s = serverSocket.accept();
                threadPool.submit(new ServerProcess(s));
            }
        } catch (Throwable t){
            // empty
        }
    }
}
