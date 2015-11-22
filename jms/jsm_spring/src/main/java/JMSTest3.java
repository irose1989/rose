import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.MessageConverter.Email;
import service.MessageConverter.ProducerService3;
import service.ProducerService;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_JMC.xml"})
public class JMSTest3 {
    @Autowired
    private ProducerService producerServiceImpl;
    @Autowired
    private Destination destination;
    @Autowired
    private ProducerService3 ProdeucerServiceImpl3;
    @Test
    public void test(){

        producerServiceImpl.sendMessage(destination," prodeucer 发送的消息");
    }
    @Test
    public void test2(){
        Email email = new Email();
        email.setContent("老婆我爱你");
        email.setReceiver("古道雪");
        email.setTitle("爱你一万年");
        ProdeucerServiceImpl3.sendEmail(destination,(Serializable)email);
    }
}
