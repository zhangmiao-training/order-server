package formssi.orderserver.provider;

import formssi.orderserver.config.rabbit.RabbitConfig;
import formssi.orderserver.constans.Constans;
import formssi.orderserver.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String key, Object body) {
        CorrelationData data = new CorrelationData();
        data.setId(key);
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHAGE, null, body, data);
    }
}
