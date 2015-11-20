import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.ProducerService;

import javax.jms.Destination;

/**
 * Created by wb-chenchaobin on 2015/11/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_JMC.xml"})
public class JMSTest {
    @Autowired
    private ProducerService producerService;
    @Autowired
    @Qualifier("queueDestination")//限定词
    private Destination destination;

    @Test
    public void testSend() {
        for (int i=0; i<10; i++) {
            producerService.sendMessage(destination, "老婆大人：我爱你啊" + (i+1));
        }
    }
}
