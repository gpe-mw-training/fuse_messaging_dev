package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * This is a custom built messaging app that delivers messages over Stomp 1.2 on a TCP socket
 */
public class StompMessagingApp {

   private static final String END_OF_FRAME = "\u0000";

   public static void main(final String[] args) throws Exception {

   //Step 1. Create a TCP socket

   //Step 2. Send a CONNECT frame to connect to the AMQ 7 broker

   //Step 3. Send a SEND frame containing a body of text to the address of gpteQueue

   //Step 4. Send a DISCONNECT frame to disconnect from the broker

   //Step 5. Close the TCP socket

   }

   private static void sendFrame(Socket socket, String data) throws Exception {
      byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
      OutputStream outputStream = socket.getOutputStream();
      for (int i = 0; i < bytes.length; i++) {
         outputStream.write(bytes[i]);
      }
      outputStream.flush();
   }

   private static String receiveFrame(Socket socket) throws Exception {
      InputStream inputStream = socket.getInputStream();
      byte[] buffer = new byte[1024];
      int size = inputStream.read(buffer);

      byte[] data = new byte[size];
      System.arraycopy(buffer, 0, data, 0, size);

      String frame = new String(data, StandardCharsets.UTF_8);
      return frame;
   }
}
