package learn.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class Sender {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory;
        Connection conn=null;
        Session session;
        Destination destination;
        MessageProducer producer ;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("SecondQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage("PERSISTENT_msg");
            producer.send(message);
            System.out.println("发送PERSISTENT_msg");
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message = session.createTextMessage("NON_PERSISTENT_msg");
            producer.send(message);
            session.commit();
            System.out.println("发送NON_PERSISTENT_msg");
            System.out.println("发送成功！！！！");
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
