package com.neoflex.tariffs.integration.kafka;

import com.neoflex.tariffs.dto.TariffKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffMessageProducer {

    private final KafkaTemplate<String, TariffKafkaMessage> kafkaTemplate;

    public void sendTariffMessage(String topicName, TariffKafkaMessage tariffMessage) {
        log.info("Got the request for sending Tariff Kafka Message to the Product service. Topic name = {}, " +
                "tariffMessage = {}", topicName, tariffMessage);
        try {
            CompletableFuture<SendResult<String, TariffKafkaMessage>> future = kafkaTemplate
                    .send(topicName, tariffMessage)
                    .completable();
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Tariff Kafka Message with tariff id {} and product id {} was sent, offset: {}",
                            tariffMessage.getId(), tariffMessage.getProductId(), result.getRecordMetadata().offset());
                } else {
                    log.error("Unable to send Tariff Kafka Message with tariff id {} and product id {} due to: {} ",
                            tariffMessage.getId(), tariffMessage.getProductId(), ex.getMessage());
                }
            });
        } catch (Exception exception) {
            log.error("Error with sending Tariff Kafka Message with tariff id {} and product id {} happened. " +
                            "The reason is: ", tariffMessage.getId(), tariffMessage.getProductId(), exception);
        }
    }
}
