package formssi.orderserver.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //---------------直连交换机---------------
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_EXCHAGE = "order.exchage";
    public static final String ORDER_ROUTE = "order.route";

    @Bean
    public Queue createQueue() {
        return new Queue(ORDER_QUEUE, true); //true 是否持久
    }

    @Bean
    DirectExchange createDirectExchange() {
        return new DirectExchange(ORDER_EXCHAGE);
    } //直连型交换机

    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(createQueue()).to(createDirectExchange()).with(ORDER_ROUTE);
    }
    //---------------直连交换机---------------


    //---------------扇型交换机---------------
    public static final String FANOUT_QUEUE1 = "fanout.queue1";
    public static final String FANOUT_QUEUE2 = "fanout.queue2";
    public static final String FANOUT_EXCHAGE = "fanout.exchage";

    @Bean
    public Queue createQueue1() {
        return new Queue(FANOUT_QUEUE1);
    }

    @Bean
    public Queue createQueue2() {
        return new Queue(FANOUT_QUEUE2);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHAGE);
    }

    @Bean
    Binding bindingFanout1() {
        return BindingBuilder.bind(createQueue1()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanout2() {
        return BindingBuilder.bind(createQueue2()).to(fanoutExchange());
    }
    //---------------扇型交换机---------------


    //---------------主题交换机---------------
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_QUEUE3 = "topic.queue3";
    public static final String TOPIC_EXCHAGE = "topic.exchage";

    @Bean
    public Queue createQueue3() {
        return new Queue(TOPIC_QUEUE1);
    }

    @Bean
    public Queue createQueue4() {
        return new Queue(TOPIC_QUEUE2);
    }

    @Bean
    public Queue createQueue5() {
        return new Queue(TOPIC_QUEUE3);
    }


    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHAGE);
    }

    @Bean
    Binding bindingTopic1() {
        return BindingBuilder.bind(createQueue3()).to(topicExchange()).with("direct.route"); //类似直连交换机
    }

    @Bean
    Binding bindingTopic2() {
        return BindingBuilder.bind(createQueue4()).to(topicExchange()).with("*.star.*"); //* (星号) 用来表示一个单词 (必须出现的)
    }

    @Bean
    Binding bindingTopic3() {
        return BindingBuilder.bind(createQueue5()).to(topicExchange()).with("well.#"); //# (井号) 用来表示任意数量单词（零个或多个）
    }
    //---------------主题交换机---------------

}