import com.guli.orders.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/4/18 10:14
 */
@Slf4j
@RabbitListener(queues = {"test.queue"})
public class MessageQueue {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    public void testCreate() {
        // 创建交换机，返回值为空
        TopicExchange exchange = new TopicExchange("test.exchange");
        amqpAdmin.declareExchange(exchange);

        // 创建队列，返回值为String或者Queue
        Queue queue = new Queue("test.queue");
        String res = amqpAdmin.declareQueue(queue);
        log.info("队列创建结果为：{}", res);

        // 创建绑定，返回值为空
        Binding binding = new Binding("test.queue",
                Binding.DestinationType.QUEUE,
                "test.exchange", "test.key", null);
        amqpAdmin.declareBinding(binding);
    }

    /**
     * 如果发送的消息是一个实体对象，则该对象应该实现Serializable接口，在消息的header中会显示该消息的实体类型
     * 序列化后的对象发送后会以编码的形式展示，如果希望以json的形式展示，则应注入一个MessageConverter的Bean作为配置
     * 并在配置中return一个jackson对象
     */
    public void testSending() {
        rabbitTemplate.convertAndSend("test.exchange",
                "test.key",
                "message sent...");
    }

    /**
     * 接收到的消息由header+body组成，body为消息内容，原生的Message类型的body为字节数组byte[]类型，需要自行做类型转换
     * 可以在监听方法中传入第二个参数作为接受的消息的实体类型，spring会自动将body部分其转换该实体类型
     * 第三个参数Channel可以获取当前数据传输的channel
     */
//    @RabbitListener(queues = {"test.queue"})
    @RabbitHandler
    public void testListener(Message message, OrderEntity entity,
                             Channel channel) throws IOException {
        /**
         * 对于同一channel内的每个消息，MQ都会自动生成一个deliveryTag，该tag在该channel内按顺序自动递增
         * basicAck表示发送回执，参数二为multiple，即表示是否批量确认ack
         * basicNack表示拒收该消息，参数二为multiple，参数三为requeue
         * 当requeue=true时，被拒收的消息会被重新放到队尾，等待下一次消费
         */
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
        channel.basicNack(deliveryTag, false, true);

        log.info("监听到的消息类型是：{}", message.getBody().getClass());
        log.info("消息为：{}", entity);
    }

}
