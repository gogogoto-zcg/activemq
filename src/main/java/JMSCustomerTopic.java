import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zcg
 * @date 2019/9/10 0010 - 18:56
 */
public class JMSCustomerTopic {

    public static final String MQ_URL="tcp://192.168.19.130:61616";
    public static final String TOPIC="topic";

    public static void main(String[] args) throws JMSException {
        //创建一个MQ连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //获取会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建主题目的地
        Topic topic = session.createTopic(TOPIC);
        //创建主题订阅者
        MessageConsumer messageConsumer = session.createConsumer(topic);
        TextMessage textMessage=null;
        while (true){
            //创建信息
             textMessage=(TextMessage) messageConsumer.receive();
            if(textMessage!=null){
                System.out.println("消费="+textMessage.getText());
            }else{
                break;
            }
        }
        //释放资源
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
