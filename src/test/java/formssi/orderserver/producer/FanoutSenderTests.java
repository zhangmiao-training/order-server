package formssi.orderserver.producer;

import formssi.orderserver.provider.FanoutSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class FanoutSenderTests {

    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void test() {
        String key = String.valueOf(UUID.randomUUID());
        key = key.replaceAll("-", "");
        fanoutSender.sendMessage(key, "我是扇形消息主体");
    }

}
