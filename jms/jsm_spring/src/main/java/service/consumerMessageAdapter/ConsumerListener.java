package service.consumerMessageAdapter;

import service.MessageConverter.Email;

/**
 * Created by Administrator on 2015/11/22.
 */
public class ConsumerListener {
    public String getMessage(String message){
        System.out.println("Adapter收到信息"+message);
        return "reply from adapter";
    }
    public void getEmail(Email email){
        System.out.println("adapter收到邮件"+email);
    }
}
