
package com.gpte.redhat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class SecurityExample {

   public static void main(final String[] args) throws Exception {
      boolean result = true;
      Connection failConnection = null;
      Connection billConnection = null;
      Connection andrewConnection = null;
      Connection frankConnection = null;
      Connection samConnection = null;

      InitialContext initialContext = null;
      try {
         // /Step 1. Create an initial context to perform the JNDI lookup.
         initialContext = new InitialContext();

         // Step 2. perform lookup on the topics
         Topic genericTopic = (Topic) initialContext.lookup("topic/genericTopic");
         Topic europeTopic = (Topic) initialContext.lookup("topic/europeTopic");
         Topic usTopic = (Topic) initialContext.lookup("topic/usTopic");

         // Step 3. perform a lookup on the Connection Factory

         // Step 4. Try to create a JMS Connection without user/password. It will fail.
         try {
         }
          catch (Exception e) {
           System.out.println("A non-JMSSecurityException occurred: " + e.getMessage());
         }

         // Step 5. bill tries to make a connection using wrong password
         try {
         }
        catch (Exception e) {
            System.out.println("A non-JMSException occurred: " + e.getMessage());
         }


         // Step 6. bill makes a good connection.

         // Step 7. andrew makes a good connection.

         // Step 8. frank makes a good connection.

         // Step 9. sam makes a good connection.

         // Step 10. Check every user can publish/subscribe genericTopics.
         System.out.println("------------------------Checking permissions on " + genericTopic + "----------------");
         System.out.println("-------------------------------------------------------------------------------------");

         System.out.println("------------------------Checking permissions on " + europeTopic + "----------------");

         // Step 11. Check permissions on news.europe.europeTopic for bill: can't send and can't receive

         // Step 12. Check permissions on news.europe.europeTopic for andrew: can send but can't receive

         // Step 13. Check permissions on news.europe.europeTopic for frank: can't send but can receive

         // Step 14. Check permissions on news.europe.europeTopic for sam: can't send but can receive
         System.out.println("-------------------------------------------------------------------------------------");

         System.out.println("------------------------Checking permissions on " + usTopic + "----------------");

         // Step 15. Check permissions on news.us.usTopic for bill: can't send and can't receive

         // Step 16. Check permissions on news.us.usTopic for andrew: can't send and can't receive

         // Step 17. Check permissions on news.us.usTopic for frank: can both send and receive

         // Step 18. Check permissions on news.us.usTopic for sam: can't send but can receive
         System.out.println("-------------------------------------------------------------------------------------");
      } finally {
	     System.out.println("dropped into finally");
         // Step 19. Be sure to close our JMS resources!
         if (failConnection != null) {
            failConnection.close();
         }
         if (billConnection != null) {
            billConnection.close();
         }
         if (andrewConnection != null) {
            andrewConnection.close();
         }
         if (frankConnection != null) {
            frankConnection.close();
         }
         if (samConnection != null) {
            samConnection.close();
         }

         // Also the initialContext
         if (initialContext != null) {
	     System.out.println("closing initialContext");
            initialContext.close();
         }
      }
   }

   // Check the user can receive message but cannot send message.
   private static void checkUserReceiveNoSend(final Topic topic,
                                              final Connection connection,
                                              final String user,
                                              final Connection sendingConn) throws JMSException {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer producer = session.createProducer(topic);
      MessageConsumer consumer = session.createConsumer(topic);
      TextMessage msg = session.createTextMessage("hello-world-1");

      try {
         producer.send(msg);
         throw new IllegalStateException("Security setting is broken! User " + user +
                                            " can send message [" +
                                            msg.getText() +
                                            "] to topic " +
                                            topic);
      } catch (JMSException e) {
         System.out.println("User " + user + " cannot send message [" + msg.getText() + "] to topic: " + topic);
      }

      // Now send a good message
      Session session1 = sendingConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      producer = session1.createProducer(topic);
      producer.send(msg);

      TextMessage receivedMsg = (TextMessage) consumer.receive(2000);

      if (receivedMsg != null) {
         System.out.println("User " + user + " can receive message [" + receivedMsg.getText() + "] from topic " + topic);
      } else {
         throw new IllegalStateException("Security setting is broken! User " + user + " cannot receive message from topic " + topic);
      }

      session1.close();
      session.close();
   }

   // Check the user can send message but cannot receive message
   private static void checkUserSendNoReceive(final Topic topic,
                                              final Connection connection,
                                              final String user,
                                              final Connection receivingConn) throws JMSException {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer producer = session.createProducer(topic);
      try {
         session.createConsumer(topic);
      } catch (JMSException e) {
         System.out.println("User " + user + " cannot receive any message from topic " + topic);
      }

      Session session1 = receivingConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer goodConsumer = session1.createConsumer(topic);

      TextMessage msg = session.createTextMessage("hello-world-2");
      producer.send(msg);

      TextMessage receivedMsg = (TextMessage) goodConsumer.receive(2000);
      if (receivedMsg != null) {
         System.out.println("User " + user + " can send message [" + receivedMsg.getText() + "] to topic " + topic);
      } else {
         throw new IllegalStateException("Security setting is broken! User " + user +
                                            " cannot send message [" +
                                            msg.getText() +
                                            "] to topic " +
                                            topic);
      }

      session.close();
      session1.close();
   }

   // Check the user has neither send nor receive permission on topic
   private static void checkUserNoSendNoReceive(final Topic topic,
                                                final Connection connection,
                                                final String user) throws JMSException {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer producer = session.createProducer(topic);

      try {
         session.createConsumer(topic);
      } catch (JMSException e) {
         System.out.println("User " + user + " cannot create consumer on topic " + topic);
      }

      TextMessage msg = session.createTextMessage("hello-world-3");
      try {
         producer.send(msg);
         throw new IllegalStateException("Security setting is broken! User " + user +
                                            " can send message [" +
                                            msg.getText() +
                                            "] to topic " +
                                            topic);
      } catch (JMSException e) {
         System.out.println("User " + user + " cannot send message [" + msg.getText() + "] to topic: " + topic);
      }

      session.close();
   }

   // Check the user connection has both send and receive permissions on the topic
   private static void checkUserSendAndReceive(final Topic topic,
                                               final Connection connection,
                                               final String user) throws JMSException {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      TextMessage msg = session.createTextMessage("hello-world-4");
      MessageProducer producer = session.createProducer(topic);
      MessageConsumer consumer = session.createConsumer(topic);
      producer.send(msg);
      TextMessage receivedMsg = (TextMessage) consumer.receive(5000);
      if (receivedMsg != null) {
         System.out.println("User " + user + " can send message: [" + msg.getText() + "] to topic: " + topic);
         System.out.println("User " + user + " can receive message: [" + msg.getText() + "] from topic: " + topic);
      } else {
         throw new IllegalStateException("Error! User " + user + " cannot receive the message! ");
      }
      session.close();
   }

   private static Connection createConnection(final String username,
                                              final String password,
                                              final ConnectionFactory cf) throws JMSException {
      return cf.createConnection(username, password);
   }
}
