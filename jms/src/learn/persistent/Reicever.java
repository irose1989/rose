package learn.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class Reicever {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn;
        Session session;
        Destination destination;
        MessageConsumer consumer;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("SecondQueue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("接收到的信息是："+((TextMessage)message).getText());
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
