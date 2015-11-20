package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by wb-chenchaobin on 2015/11/19.
 */
@Component
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private JmsTemplate jmsTemplate;
    public void sendMessage(Destination destination, final String message) {
        System.out.println("---------------陈朝斌发送消息-----------------");
        System.out.println("---------------陈朝斌发了一个消息：" + message);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
