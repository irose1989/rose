package learn.replyTo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class ReplyTo {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn;
        final Session session;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination1 = session.createQueue("SecondQueue");
            final Destination destination2 = session.createQueue("ThirdQueue");
            MessageProducer producer = session.createProducer(destination1);
            Message message = session.createTextMessage("Andy,how are you");
            message.setJMSReplyTo(destination2);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(message);
            session.commit();

            MessageConsumer consumer = session.createConsumer(destination1);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        MessageProducer producer1 = session.createProducer(destination2);
                        producer1.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                        TextMessage message1 = session.createTextMessage("I'm fine");
                        producer1.send(message1);
                        session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            MessageConsumer consumer1 = session.createConsumer(destination2);
            consumer1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("consumer1 接收到的是:"+ ((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
