package learn;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class SenderTest {
    public static  void send(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection =null;
        try {
            connection= factory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //Destination destination = session.createQueue("FirstQueue");
            Topic topic = new ActiveMQTopic("FirstQueue");
            //MessageProducer producer = session.createProducer(destination);
            MessageProducer producer = session.createProducer(topic);
            sendMsg(producer,session);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendMsg(MessageProducer producer,Session session){
        try {
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage("SenderTest发送第"+i+"个消息");
                System.out.println("SenderTest发送第"+i+"个消息");
                producer.send(message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        send();
    }
}
