package formssi.orderserver.producer;

import formssi.orderserver.provider.TopicSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TopicSenderTests {

    @Autowired
    private TopicSender topicSender;

    @Test
    public void test1() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("direct.route", key, "我是主题消息test1");
    }

    @Test
    public void test2() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("star", key, "我是主题消息test2");
    }

    @Test
    public void test3() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("x.star.y", key, "我是主题消息test3");
    }

    @Test
    public void test4() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("x.star", key, "我是主题消息test4");
    }

    @Test
    public void test5() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("well.", key, "我是主题消息test5");
    }

    @Test
    public void test6() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("well.t", key, "我是主题消息test6");
    }

    @Test
    public void test7() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        topicSender.sendMessage("well.test", key, "我是主题消息test7");
    }

}
