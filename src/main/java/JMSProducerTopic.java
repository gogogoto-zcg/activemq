import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zcg
 * @date 2019/9/10 0010 - 18:56
 */
public class JMSProducerTopic {
    public static final String MQ_URL="tcp://192.168.19.130:61616";
    public static final String TOPIC="topic";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC);

        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("发布消息--" + i);
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("成功发布信息====");


    }
}
