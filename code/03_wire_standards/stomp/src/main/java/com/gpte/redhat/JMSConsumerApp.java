package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * This is a custom built messaging app that consumes messages via a JMS MessageConsumer.
 */
public class JMSConsumerApp {

   public static void main(final String[] args) throws Exception {
      Connection connection = null;
      InitialContext initialContext = null;

      try {
   //Step 1. Creates an initial context that performs a JNDI lookup for the queue and then performs a lookup of both the queue and the connection factory

   //Step 2. Creates a JMS session, a JMS connection, and a MessageConsumer

   //Step 3. Initiates the JMS connection

   //Step 4. Receives the STOMP message and removes all used JMS resources

      } finally {

         if (initialContext != null) {
            initialContext.close();
         }
         if (connection != null) {
            connection.close();
         }
      }
   }
}
