package formssi.orderserver.controller;

import formssi.orderserver.bean.ResultBean;
import formssi.orderserver.provider.OrderSender;
import formssi.orderserver.provider.TopicSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    OrderSender orderSender;

    @Autowired
    TopicSender topicSender;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping(value = "pushOrder", method = RequestMethod.GET)
    public Object pushOrder() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        String sendTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> body = new HashMap<>();
        body.put("orderId", key);
        body.put("sendTime", sendTime);
        logger.info("消息处理中");
        orderSender.sendMessage(key, body);
        return ResultBean.success("ok");
    }


    @ResponseBody
    @RequestMapping(value = "rabbitTest", method = RequestMethod.GET)
    public Object rabbitTest() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("star", key, "我是主题消息test2");

        key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("x.star.y", key, "我是主题消息test3");

        key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("x.star", key, "我是主题消息test4");

        return ResultBean.success("ok");
    }
}
