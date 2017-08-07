package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Lab exercise using a JMS Queue configured with a dual broker authentication mechanisms for SSL and non-SSL connections.
 */
public class SSLDualAuthenticationExample {

   public static void main(final String[] args) throws Exception {
      Connection producerConnection = null;
      Connection consumerConnection = null;
      InitialContext initialContext = null;
      try {
         // Step 1. Create an initial context to perform the JNDI lookup.
         initialContext = new InitialContext();

         // Step 2. Perfom a lookup on the queue

         // Step 3. Perform a lookup on the producer's SSL Connection Factory

         // Step 4. Perform a lookup on the consumer's Connection Factory

         // Step 5.Create a JMS Connection for the producer

         // Step 6.Create a JMS Connection for the consumer

         // Step 7. Create a JMS Session for the producer

         // Step 8. Create a JMS Session for the consumer

         // Step 9. Create a JMS Message Producer

         // Step 10. Create a Text Message

         System.out.println("Sent message: " + message.getText());

         // Step 11. Send the Message

         // Step 12. Create a JMS Message Consumer

         // Step 13. Start the Connection

         // Step 14. Receive the message

         System.out.println("Received message: " + messageReceived.getText());

         initialContext.close();
      } finally {
         // Step 15. Be sure to close our JMS resources!
         if (initialContext != null) {
            initialContext.close();
         }
         if (producerConnection != null) {
            producerConnection.close();
         }
         if (consumerConnection != null) {
            consumerConnection.close();
         }
      }
   }
}
