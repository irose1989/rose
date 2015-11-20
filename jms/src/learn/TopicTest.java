package learn;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class TopicTest {


    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn;
        Session session;
        Topic topic;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            topic = new ActiveMQTopic("FirstQueue");
            //consumer1
            final MessageConsumer consumer1 = session.createConsumer(topic);
            consumer1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("consumer1 接收到的信息："+((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            final MessageConsumer consumer2 = session.createConsumer(topic);
            consumer2.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("consumer2 接收到的信息："+((TextMessage)message).getText());
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
