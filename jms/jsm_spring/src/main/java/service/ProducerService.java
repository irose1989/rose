package service;

import javax.jms.Destination;

/**
 * Created by wb-chenchaobin on 2015/11/19.
 */
public interface ProducerService {
    public void sendMessage(Destination destination, final String message);
}
