package service.sessionAwareMessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

/**
 * Created by Administrator on 2015/11/22.
 */

public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener {
    @Autowired
    private Destination destination;
    public void onMessage(Message message, Session session) throws JMSException {
        System.out.println("收到信息："+((TextMessage)message).getText());
        MessageProducer producer = session.createProducer(destination);
        producer.send(session.createTextMessage("reply from consumer"));
    }
}
