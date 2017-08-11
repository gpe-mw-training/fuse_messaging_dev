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

         Socket socket = new Socket("localhost", 61613);

         String connectFrame = "CONNECT\n" +
            "accept-version:1.2\n" +
            "host:localhost\n" +
            "login:guest\n" +
            "passcode:guest\n" +
            "request-id:1\n" +
            "\n" +
            END_OF_FRAME;
         sendFrame(socket, connectFrame);

         String response = receiveFrame(socket);
         System.out.println("response: " + response);

         String text = "This is a message sent using Stomp 1.2";
         String message = "SEND\n" +
            "destination:gpteQueue\n" +
            "\n" +
            text +
            END_OF_FRAME;
         sendFrame(socket, message);
         System.out.println("Sent Stomp message: " + text);

         String disconnectFrame = "DISCONNECT\n" +
            "\n" +
            END_OF_FRAME;
         sendFrame(socket, disconnectFrame);

         socket.close();

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
