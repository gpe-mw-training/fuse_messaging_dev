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

         initialContext = new InitialContext();

         Queue queue = (Queue) initialContext.lookup("queue/gpteQueue");
         ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

         connection = cf.createConnection();

         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         MessageConsumer consumer = session.createConsumer(queue);

         connection.start();

         TextMessage messageReceived = (TextMessage) consumer.receive(5000);
         System.out.println("Received JMS message: " + messageReceived.getText());

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
