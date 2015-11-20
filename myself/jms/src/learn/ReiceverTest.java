package learn;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wb-chenchaobin on 2015/11/18.
 */
public class ReiceverTest {

    public static void main(String[] args) {
        reicever2();
        reicever1();
    }
    /**
     * 消费者接受消息方式：
     * 1，consumer.receive()
     * 或 consumer.receive(int timeout)；
     * */
    public static void reicever1(){
        Connection connection=null;
        ActiveMQConnectionFactory factory;
        Session session;
        MessageConsumer consumer;
        Destination destination;
        factory = new ActiveMQConnectionFactory();
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            while (true){
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println("consumer1接收到："+message.getText());
            }
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
    /**
     * 消费者接受消息方式：
     * 2，注册消息监听器
     * */
    public static void reicever2(){
        ActiveMQConnectionFactory factory;
        Connection connection =null;
        Session session;
        Destination destination;
        final MessageConsumer consumer;
        factory = new ActiveMQConnectionFactory();
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        System.out.println("consumer2接收到："+text);
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
