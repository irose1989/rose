package learn.JMSCorrelationId;

/**
 * Created by wb-chenchaobin on 2015/11/19.
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *    前面讲过JMSCorrelationID主要是用来关联多个Message，例如需要回复一个消息的时候，
 *    通常把回复的消息的JMSCorrelationID设置为原来消息的ID。在下面这个例子中，
 *    创建了三个消息生产者A，B，C和三个消息消费者A，B，C。生产者A给消费者A发送一个消息，
 *    同时需要消费者A给它回复一个消息。B、C与A类似。
     简图如下：
     生产者A-----发送----〉消费者A-----回复------〉生产者A
     生产者B-----发送----〉消费者B-----回复------〉生产者B
     生产者C-----发送----〉消费者C-----回复------〉生产者C

     需要注意的是，所有的发送和回复都使用同一个Queue，通过Selector区分。
 * */
public class JMSCorrelation {
    private Session session;
    private Destination destination;
    public static void main(String[] args) {
        JMSCorrelation jmsCorrelation = new JMSCorrelation();
        jmsCorrelation.comm();
    }
    public void comm(){
        ActiveMQConnectionFactory factory;
        Connection conn;
        factory = new ActiveMQConnectionFactory();
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("SecondQueue");

            setConsumer("c1");
            setConsumer("c2");
            setConsumer("c3");
            setProducer("p1", "c1");
            setProducer("p2","c2");
            setProducer("p3","c3");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void setProducer(final String producerName,String consumerName) throws JMSException {
        final MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(producerName+" to "+consumerName);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        message.setStringProperty("reveiver", consumerName);
        producer.send(message);
        session.commit();
        System.out.println(producerName+"已经成功发送消息到："+consumerName);

        //回复的消息
        MessageConsumer consumer = session.createConsumer(destination,"JMSCorrelationID='"+message.getJMSMessageID()+"'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println(producerName+"收到回复："+((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setConsumer(final String consumerName) throws JMSException {
        final MessageConsumer consumer = session.createConsumer(destination,"reveiver='"+consumerName+"'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println(consumerName+"收到消息："+((TextMessage)message).getText());
                    //回复消息
                    MessageProducer producer = session.createProducer(destination);
                    TextMessage msg = session.createTextMessage("reply from "+consumerName);
                    msg.setJMSCorrelationID(message.getJMSMessageID());
                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                    producer.send(msg);
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
