
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

         // Instantiate connection towards server 0

         // Step 5. Look-up a JMS Connection Factory object from JNDI on server 1

         // Step 6. We create a JMS Connection connection0 which is a connection to server 0

         // Step 7. We create a JMS Connection connection1 which is a connection to server 1

         // Step 8. We create a JMS Session on server 0

         // Step 9. We create a JMS Session on server 1

         // Step 10. We start the connections to ensure delivery occurs on them

         // Step 11. We create JMS MessageConsumer objects on server 0 and server 1

         // Step 12. We create a JMS MessageProducer object on server 0
         MessageProducer producer = session0.createProducer(queue);

         // Step 13. We send some messages to server 0

         final int numMessages = 10;

         for (int i = 0; i < numMessages; i++) {
         }

         // Step 14. We now consume those messages on *both* server 0 and server 1.
         // We note the messages have been distributed between servers in a round robin fashion
         // JMS Queues implement point-to-point message where each message is only ever consumed by a
         // maximum of one consumer

         for (int i = 0; i < numMessages; i += 2) {
         }
      } finally {
         // Step 15. Be sure to close our resources!
      }
   }
}
