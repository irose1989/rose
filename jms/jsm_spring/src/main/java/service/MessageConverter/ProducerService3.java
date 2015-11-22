package service.MessageConverter;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 */
public interface ProducerService3 {
    void sendEmail(Destination destination,Serializable o);
}
