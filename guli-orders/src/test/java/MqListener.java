import com.guli.orders.entity.OrderEntity;
import com.guli.orders.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/4/18 16:43
 */
@Slf4j
@RabbitListener(queues = {"test.queue"})
public class MqListener {

    @RabbitHandler
    public void receiver00(OrderEntity orderEntity) {
        log.info("接收到消息为：{}", orderEntity);
    }

    @RabbitHandler
    public void receiver01(OrderReturnReasonEntity orderReturn) {
        log.info("接收到消息为：{}", orderReturn);
    }
}
