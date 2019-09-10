package util;

import org.apache.activemq.broker.BrokerService;

/**
 * @author zcg
 * @date 2019/9/10 0010 - 23:59
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        //内嵌的一个ACTIVEMQ 服务实例
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
