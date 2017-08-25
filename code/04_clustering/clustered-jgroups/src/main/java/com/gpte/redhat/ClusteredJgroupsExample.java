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

         // Step 3. Look-up a JMS Connection Factory object from JNDI on server 0

         // Step 4. Get an initial context for looking up JNDI from server 1
	 System.out.println("Created InitialContext\n");

         // Step 5. Look-up a JMS Connection Factory object from JNDI on server 1
	 System.out.println("Created ConnectionFactory\n");

         // Step 6. We create a JMS Connection connection0 which is a connection to server 0
	 System.out.println("Created Connection0\n");

         // Step 7. We create a JMS Connection connection1 which is a connection to server 1
	 System.out.println("Created Connection1\n");

         // Step 8. We create a JMS Session on server 0
	 System.out.println("Created Session0\n");

         // Step 9. We create a JMS Session on server 1
	 System.out.println("Created Session1\n");

         // Step 10. We start the connections to ensure delivery occurs on them

         // Step 11. We create JMS MessageConsumer objects on server 0 and server 1

         Thread.sleep(1000);

         // Step 12. We create a JMS MessageProducer object on server 0

         // Step 13. We send some messages to server 0


         // Step 14. We now consume those messages on *both* server 0 and server 1.
         // We note the messages have been distributed between servers in a round robin fashion
         // JMS Queues implement point-to-point message where each message is only ever consumed by a
         // maximum of one consumer

         for (int i = 0; i < numMessages; i += 2) {
	    System.out.println("Got message: " + message1.getText() + " from node 1");
         }
      } finally {
         // Step 15. Be sure to close our resources!

      }
   }
}
