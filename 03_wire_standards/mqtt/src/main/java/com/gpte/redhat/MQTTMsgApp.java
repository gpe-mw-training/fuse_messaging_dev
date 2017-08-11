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

      	// EXCLUDE-BEGIN
      MQTT mqtt = new MQTT();
      mqtt.setHost("tcp://localhost:1883");
      BlockingConnection connection = mqtt.blockingConnection();
      connection.connect();
      	// EXCLUDE-END

      System.out.println("Connected to a Red Hat JBoss AMQ 7 broker");

      	// EXCLUDE-BEGIN
      Topic[] topics = {new Topic("gpte/topic/publish", QoS.AT_LEAST_ONCE), new Topic("redhat/#", QoS.AT_LEAST_ONCE), new Topic("enterprise/+/linux", QoS.AT_MOST_ONCE)};
      connection.subscribe(topics);
        // EXCLUDE-END
      System.out.println("Subscribed to topics.");

      // Publish these messages using your code
      String payload1 = "This is message A";
      String payload2 = "This is message B";
      String payload3 = "This is message C";

      	// EXCLUDE-BEGIN
      connection.publish("gpte/topic/publish", payload1.getBytes(), QoS.AT_LEAST_ONCE, false);
      connection.publish("redhat/temp", payload2.getBytes(), QoS.AT_MOST_ONCE, false);
      connection.publish("enterprise/1/linux", payload3.getBytes(), QoS.EXACTLY_ONCE, false);
        // EXCLUDE-END
      System.out.println("Sent all the messages.");

      	// EXCLUDE-BEGIN
      Message messageA = connection.receive(5, TimeUnit.SECONDS);
      Message messageB = connection.receive(5, TimeUnit.SECONDS);
      Message messageC = connection.receive(5, TimeUnit.SECONDS);

      System.out.println("Received a message! "+new String(messageA.getPayload()));
      System.out.println("Received a message! "+new String(messageB.getPayload()));
      System.out.println("Received a message! "+new String(messageC.getPayload()));
        // EXCLUDE-END
      System.out.println("Received all the messages.");

   }
}
