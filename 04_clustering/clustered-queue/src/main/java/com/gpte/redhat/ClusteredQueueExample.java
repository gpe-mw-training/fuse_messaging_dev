
package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Use this application to test server side load-balancing of messages between the queue instances on different
 * nodes of the cluster.
 */
public class ClusteredQueueExample {

   public static void main(final String[] args) throws Exception {
      Connection connection0 = null;

      Connection connection1 = null;

      try {
         // Step 2. Instantiate the Queue
	 // EXCLUDE-BEGIN
         Queue queue = ActiveMQJMSClient.createQueue("exampleQueue");
	 // EXCLUDE-END

         // Instantiate connection towards server 0
	 // EXCLUDE-BEGIN
         ConnectionFactory cf0 = new ActiveMQConnectionFactory("tcp://localhost:61616");
	 // EXCLUDE-END

         // Step 5. Look-up a JMS Connection Factory object from JNDI on server 1
	 // EXCLUDE-BEGIN
         ConnectionFactory cf1 = new ActiveMQConnectionFactory("tcp://localhost:61617");
	 // EXCLUDE-END

         // Step 6. We create a JMS Connection connection0 which is a connection to server 0
	 // EXCLUDE-BEGIN
         connection0 = cf0.createConnection();
	 // EXCLUDE-END

         // Step 7. We create a JMS Connection connection1 which is a connection to server 1
	 // EXCLUDE-BEGIN
         connection1 = cf1.createConnection();
	 // EXCLUDE-END

         // Step 8. We create a JMS Session on server 0
	 // EXCLUDE-BEGIN
         Session session0 = connection0.createSession(false, Session.AUTO_ACKNOWLEDGE);
	 // EXCLUDE-END

         // Step 9. We create a JMS Session on server 1
	 // EXCLUDE-BEGIN
         Session session1 = connection1.createSession(false, Session.AUTO_ACKNOWLEDGE);
	 // EXCLUDE-END

         // Step 10. We start the connections to ensure delivery occurs on them
	 // EXCLUDE-BEGIN
         connection0.start();

         connection1.start();
	 // EXCLUDE-END

         // Step 11. We create JMS MessageConsumer objects on server 0 and server 1
	 // EXCLUDE-BEGIN
         MessageConsumer consumer0 = session0.createConsumer(queue);

         MessageConsumer consumer1 = session1.createConsumer(queue);

         Thread.sleep(1000);
	 // EXCLUDE-END

         // Step 12. We create a JMS MessageProducer object on server 0
         MessageProducer producer = session0.createProducer(queue);

         // Step 13. We send some messages to server 0

         final int numMessages = 10;

         for (int i = 0; i < numMessages; i++) {
	 // EXCLUDE-BEGIN
            TextMessage message = session0.createTextMessage("This is text message " + i);

            producer.send(message);

            System.out.println("Sent message: " + message.getText());
	 // EXCLUDE-END
         }

         // Step 14. We now consume those messages on *both* server 0 and server 1.
         // We note the messages have been distributed between servers in a round robin fashion
         // JMS Queues implement point-to-point message where each message is only ever consumed by a
         // maximum of one consumer

         for (int i = 0; i < numMessages; i += 2) {
	 // EXCLUDE-BEGIN
            TextMessage message0 = (TextMessage) consumer0.receive(5000);

            System.out.println("Got message: " + message0.getText() + " from node 0");

            TextMessage message1 = (TextMessage) consumer1.receive(5000);

	    if (message1 != null)
		System.out.println("Got message: " + message1.getText() + " from node 1");
	    else
		System.out.println("Got NULL message on consumer1 from node 1");
	 // EXCLUDE-END
         }
      } finally {
         // Step 15. Be sure to close our resources!
	 // EXCLUDE-BEGIN

         if (connection0 != null) {
            connection0.close();
         }

         if (connection1 != null) {
            connection1.close();
         }
	 // EXCLUDE-END
      }
   }
}
