package formssi.orderserver.provider;

import formssi.orderserver.config.rabbit.RabbitConfig;
import formssi.orderserver.constans.Constans;
import formssi.orderserver.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate.ConfirmCallback confirmCallback =  new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            if (ack && correlationData != null) {
                redisUtil.deleteMapItem(Constans.KEY_UNCONFIRM_MAP, correlationData.getId());
                logger.info("消息已确认，消息已从redis中清理：{}", correlationData.getId());
            }
        }
    };

    public void sendMessage(String key, Object body) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        redisUtil.setMapItem(Constans.KEY_UNCONFIRM_MAP, key, body);
        CorrelationData data = new CorrelationData();
        data.setId(key);
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHAGE, body, data);
    }
}
