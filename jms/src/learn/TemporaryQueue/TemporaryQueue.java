package learn.TemporaryQueue;

/**
 * Created by wb-chenchaobin on 2015/11/19.
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *  TemporaryQueue和TemporaryTopic，从字面上就可以看出它们是“临时”的目的地。可以通过Session来创建，例如：
 TemporaryQueue replyQueue = session.createTemporaryQueue();
 虽然它们是由Session来创建的，但是它们的生命周期确实整个Connection。如果在一个Connection上创建了两个Session，则一个Session创建的TemporaryQueue或TemporaryTopic也可以被另一个Session访问。那如果这两个Session是由不同的Connection创建，则一个Session创建的TemporaryQueue不可以被另一个Session访问。
 另外，它们的主要作用就是用来指定回复目的地， 即作为JMSReplyTo。
 在下面的例子中，先创建一个Connection，然后创建两个Session，其中一个Session创建了一个TemporaryQueue，另一个Session在这个TemporaryQueue上读取消息。
 * */
public class TemporaryQueue {
    public static void main(String[] args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        try {
            Connection conn = factory.createConnection();
            conn.start();
            Session session1 = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Session session2 = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session1.createTemporaryQueue();
            MessageConsumer consumer = session2.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("seession2 收到消息："+((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            MessageProducer producer = session1.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(session1.createTextMessage("session1:发送的消息"));
            session1.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
