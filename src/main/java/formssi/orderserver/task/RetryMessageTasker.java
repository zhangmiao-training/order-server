package formssi.orderserver.task;

import formssi.orderserver.constans.Constans;
import formssi.orderserver.provider.OrderSender;
import formssi.orderserver.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RetryMessageTasker {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OrderSender orderSender;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定时任务
     */
    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void resend() {
        Map<Object, Object> unconfirmMap = redisUtil.getMap(Constans.KEY_UNCONFIRM_MAP);
        logger.info("定时任务开始，未确认消息数：{}", unconfirmMap.size());
        for (Map.Entry<Object, Object> entry : unconfirmMap.entrySet()) {
            CorrelationData data = new CorrelationData();
            data.setId(entry.getKey().toString());
            logger.info("正在重发消息：{}", entry.getKey().toString());
            orderSender.sendMessage(entry.getKey().toString(), entry.getValue());

        }
    }

}
