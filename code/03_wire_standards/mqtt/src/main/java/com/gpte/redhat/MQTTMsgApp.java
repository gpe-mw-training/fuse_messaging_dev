package com.gpte.redhat;

import java.util.concurrent.TimeUnit;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.junit.Test;

/**
 * MQTT messaging application for Wire Standards lab
 */
public class MQTTMsgApp {

   public static void main(final String[] args) throws Exception {

      new MQTTMsgApp().testMQTTCode();

   }

 @Test(timeout=3000)
 public void testMQTTCode() throws Exception {

   System.out.println("Connecting to a Red Hat JBoss AMQ 7 broker using MQTT protocol standard");

    //Step 1. Create and initiate a `BlockingConnection` to the JBoss AMQ 7 broker

   System.out.println("Connected to the Red Hat JBoss AMQ 7 broker");

     //Step 2. Create subscriptions to the three MQTT topics
   System.out.println("Subscribed to topics.");

   // Publish these messages using your code
   String payload1 = "This is message A";
   String payload2 = "This is message B";
   String payload3 = "This is message C";

     //Step 3. Publish the messages to the subscribed topics
   System.out.println("Sent all the messages.");

     //Step 4. Receive the messages from the JBoss AMQ 7 broker
   System.out.println("Received all the messages.");
        
   }
}
