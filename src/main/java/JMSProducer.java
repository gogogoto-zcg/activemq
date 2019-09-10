import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zcg
 * @date 2019/9/10 0010 - 18:16
 */
public class JMSProducer {
    //MQ的服务端口
    public static final String MQ_URL = "tcp://192.168.19.130:61616";
    //消息目的地
    public static final String MYQUEUE = "queue";

    public static void main(String[] args) throws JMSException {
        //获得消息中间件的连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //获取连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        /**
         *  创建会话,true为开启事务，需要commit提交之后才能进入队列,false 表示send直接发送到中间件
         */
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建目的地也就是消息中间件
        Queue queue = session.createQueue(MYQUEUE);
        //创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);

        //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//设置消息非持久化,MQ宕机消息永久丢失
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);//设置消息持久化，默认消息持久化

        //发布消息
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg----" + i);
            messageProducer.send(textMessage);
        }
        session.commit();
        //释放资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("run is ok...");

    }


}
