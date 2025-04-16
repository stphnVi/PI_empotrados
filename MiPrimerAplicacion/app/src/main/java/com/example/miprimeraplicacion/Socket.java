package com.example.miprimeraplicacion;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Socket {
    public static java.net.Socket socket;
    public static PrintWriter out;
    public static Scanner in;
    public static String message;
    public static boolean sistemaInit = false;
    public static DataOutputStream dataOut;

    public static void iniciarSocket() {
        new Thread(() -> {
            try {
                // Cambiar a la dirección IP de su servidor
                socket = new java.net.Socket("192.168.1.23", 1717);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new Scanner(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    while (true) {
                        // Escuchar continuamente los mensajes del servidor
                        if (in.hasNextLine()) {
                            message = in.nextLine();
                            //textViewChat.append("Servidor: " + message + "\n");
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendMessage(String message) {
        new Thread(() -> {
            try {
                if (message != null) {
                    out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



}