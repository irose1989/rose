package learn.selector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class SelectorTest {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn=null;
        Session session;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            session = conn.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("ThirdQueue");
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 10; i++) {
                String reicever = (i%3==0)?"A":"B";
                TextMessage message = session.createTextMessage("message"+i+"reicever="+reicever);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                producer.send(message);
            }
            session.commit();
            MessageConsumer c1 = session.createConsumer(destination,"reicever=A");
            c1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("c1");
                }
            });



        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
