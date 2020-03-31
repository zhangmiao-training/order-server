package formssi.orderserver.provider;

import formssi.orderserver.config.rabbit.RabbitConfig;
import formssi.orderserver.constans.Constans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            logger.info("消息传递错误，消息内容如下：\n");
            logger.info("消息主体：{}", message);
            logger.info("消息编码：{}", replyCode);
            logger.info("消息描述：{}", replyText);
            logger.info("消息交换器：{}", exchange);
            logger.info("消息路由：{}", routingKey);
        }
    };

    public void sendMessage(String routeKey, String key, Object body) {
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData data = new CorrelationData();
        data.setId(key);
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHAGE, routeKey, body, data);
    }

}
