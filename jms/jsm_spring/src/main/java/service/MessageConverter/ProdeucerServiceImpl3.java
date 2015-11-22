package service.MessageConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 */
@Component
public class ProdeucerServiceImpl3 implements ProducerService3 {
    @Autowired
    private JmsTemplate template;
    public void sendEmail(Destination destination, Serializable o) {
        System.out.println("发送了一份邮件");
        template.convertAndSend(destination, o);

    }
}
