package service.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by Administrator on 2015/11/22.
 */
public class ConsumerListener3 implements MessageListener {
    public void onMessage(Message message) {
        if(message instanceof ObjectMessage){
            try {
                Email email = (Email) ((ObjectMessage)message).getObject();
                System.out.println("收到一份邮件"+email);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
