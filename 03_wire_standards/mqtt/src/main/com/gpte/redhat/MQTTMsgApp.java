package com.gpte.redhat;

import java.util.concurrent.TimeUnit;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

/**
 * MQTT messaging application for Wire Standards lab exercise
 */
public class MQTTMsgApp {

   public static void main(final String[] args) throws Exception {

      System.out.println("Connecting to Red Hat JBoss AMQ 7 via MQTT");

      // Publish these messages using your code
      String payload1 = "This is message A";
      String payload2 = "This is message B";
      String payload3 = "This is message C";

      System.out.println("Received messages.");
   }
}
