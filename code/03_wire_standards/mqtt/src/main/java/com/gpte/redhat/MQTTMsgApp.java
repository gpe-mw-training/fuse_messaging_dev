package com.gpte.redhat;

import java.util.concurrent.TimeUnit;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

/**
 * MQTT messaging application for Wire Standards lab
 */
public class MQTTMsgApp {

   public static void main(final String[] args) throws Exception {

      System.out.println("Connecting to a Red Hat JBoss AMQ 7 broker using MQTT protocol standard");


      System.out.println("Connected to a Red Hat JBoss AMQ 7 broker");

      System.out.println("Subscribed to topics.");

      // Publish these messages using your code
      String payload1 = "This is message A";
      String payload2 = "This is message B";
      String payload3 = "This is message C";

      System.out.println("Sent all the messages.");

      System.out.println("Received all the messages.");

   }
}
