package service.MessageConverter;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 */
public class EmailMessageConverter implements MessageConverter {

    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        return session.createObjectMessage((Serializable) o);
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return ((ObjectMessage)message).getObject();
    }
}
