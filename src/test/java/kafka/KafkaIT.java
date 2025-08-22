package kafka;

import com.ffucks.dtos.OrderCreated;
import com.ffucks.services.OrderKafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Map;

@SpringBootTest(
        properties = {
                // override para apontar para o broker embutido
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "app.kafka.topic=demo.orders"
        }
)
@EmbeddedKafka(partitions = 1, topics = "demo.orders")
class KafkaIT {

    // injeta o broker embutido
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    // garante que o Spring use o broker embutido como bootstrap
    @DynamicPropertySource
    static void kafkaProps(DynamicPropertyRegistry r) {
        r.add("spring.kafka.bootstrap-servers",
                () -> System.getProperty("spring.embedded.kafka.brokers"));
    }

    @Autowired
    private OrderKafkaProducer producer;

    @Test
    void should_send_and_receive() {
        // envia
        producer.send(new OrderCreated("EK-1", "Dev", 10.0));

        // usa a SOBRECARREGA que recebe EmbeddedKafkaBroker (sem ambiguidade)
        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps("test-group", "true", embeddedKafka);

        // define deserializers (às vezes necessário, dependendo da sua config)
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps);
        var consumer = consumerFactory.createConsumer();

        // faz o consumer “apontar” para o tópico embutido
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "demo.orders");

        // lê um registro com timeout
        ConsumerRecord<String, String> rec =
                KafkaTestUtils.getSingleRecord(consumer, "demo.orders", Duration.ofSeconds(10));

        assertThat(rec.value()).contains("\"orderId\":\"EK-1\"");
        consumer.close();
    }
}
