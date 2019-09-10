import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zcg
 * @date 2019/9/10 0010 - 18:36
 *  1、事务级别大于签收
 *  2、事务偏向生产者，签收偏向消费者
 *
 */
public class JMSCustomer {
    public static final String MQ_URL = "tcp://192.168.19.130:61616";
    public static final String MYQUEUE = "queue";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        /**
         * true 表示开启事务，需要commit才能消费掉中间件中的信息，如果不commit不然会导致重复消费的现象
         * 事务开启的情况下，忽略签收影响 ，不过还是需要设置参数
         * false,
         * Session.AUTO_ACKNOWLEDGE    自动签收，每次调用receive()方法即消费一条消息
         * Session.CLIENT_ACKNOWLEDGE  手动签收，需要调用textMessage.acknowledge()消费信息
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(MYQUEUE);

        MessageConsumer messageConsumer = session.createConsumer(queue);

        /*同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
        receive()将一直阻塞
        receive(long timeout) 按照给定的时间阻塞，到时间自动退出*/

        TextMessage textMessage = null;
        while (true){
            textMessage = (TextMessage) messageConsumer.receive();
            if(textMessage!=null) {
                System.out.println("消费--" + textMessage.getText());
                textMessage.acknowledge();
                //session.commit(); //开启事务情况下，需要commit
            }else{
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();


        /*
        异步非阻塞方式(监听器onMessage())
        订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
        当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。
        messageConsumer.setMessageListener(message->{
            if(message!=null && message instanceof TextMessage){
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("消费--"+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        */






    }
}
