import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.ProducerService;

import javax.jms.Destination;

/**
 * Created by Administrator on 2015/11/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_JMC.xml"})
public class JMSTest2 {
    @Autowired
    private ProducerService service;
    @Autowired
    private Destination destination;
    @Test
    public void test(){
        service.sendMessage(destination,"prodeucer 发送了 消息");
    }
}
