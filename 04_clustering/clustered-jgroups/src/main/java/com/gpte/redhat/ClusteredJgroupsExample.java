package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import java.util.Hashtable;

/**
 * Lab exercise for testing the jgroups clustering feature of Red Hat JBoss AMQ 7.
 */
public class ClusteredJgroupsExample {

   public static void main(final String[] args) throws Exception {
      Connection connection0 = null;

      Connection connection1 = null;

      InitialContext ic0 = null;

      InitialContext ic1 = null;

      try {
         // Step 1. Get an initial context for looking up JNDI from server 0
         Hashtable<String, Object> properties = new Hashtable<>();
         properties.put("java.naming.factory.initial", "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
         properties.put("connectionFactory.ConnectionFactory", "tcp://localhost:61616");
         properties.put("queue.queue/exampleQueue", "exampleQueue");
         ic0 = new InitialContext(properties);

         // Step 2. Look-up the JMS Queue object from JNDI
	 // EXCLUDE-BEGIN
         Queue queue = (Queue) ic0.lookup("queue/exampleQueue");
	 // EXCLUDE-END

         // Step 3. Look-up a JMS Connection Factory object from JNDI on server 0
	 // EXCLUDE-BEGIN
         ConnectionFactory cf0 = (ConnectionFactory) ic0.lookup("ConnectionFactory");
	 // EXCLUDE-END

         // Step 4. Get an initial context for looking up JNDI from server 1
	 // EXCLUDE-BEGIN
         properties = new Hashtable<>();
         properties.put("java.naming.factory.initial", "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
         properties.put("connectionFactory.ConnectionFactory", "tcp://localhost:61617");
         ic1 = new InitialContext(properties);
	 // EXCLUDE-END

         // Step 5. Look-up a JMS Connection Factory object from JNDI on server 1
	 // EXCLUDE-BEGIN
         ConnectionFactory cf1 = (ConnectionFactory) ic1.lookup("ConnectionFactory");
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
	 // EXCLUDE-END

         Thread.sleep(1000);

         // Step 12. We create a JMS MessageProducer object on server 0
	 // EXCLUDE-BEGIN
         MessageProducer producer = session0.createProducer(queue);
	 // EXCLUDE-END

         // Step 13. We send some messages to server 0

	 // EXCLUDE-BEGIN
         final int numMessages = 10;

         for (int i = 0; i < numMessages; i++) {
            TextMessage message = session0.createTextMessage("This is text message " + i);

            producer.send(message);

            System.out.println("Sent message: " + message.getText());
         }
	 // EXCLUDE-END

         // Step 14. We now consume those messages on *both* server 0 and server 1.
         // We note the messages have been distributed between servers in a round robin fashion
         // JMS Queues implement point-to-point message where each message is only ever consumed by a
         // maximum of one consumer

         for (int i = 0; i < numMessages; i += 2) {
	 // EXCLUDE-BEGIN
            TextMessage message0 = (TextMessage) consumer0.receive(5000);

            System.out.println("Got message: " + message0.getText() + " from node 0");

            TextMessage message1 = (TextMessage) consumer1.receive(5000);

	 // EXCLUDE-END
            System.out.println("Got message: " + message1.getText() + " from node 1");
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

         if (ic0 != null) {
            ic0.close();
         }

         if (ic1 != null) {
            ic1.close();
         }
	 // EXCLUDE-END
      }
   }
}
