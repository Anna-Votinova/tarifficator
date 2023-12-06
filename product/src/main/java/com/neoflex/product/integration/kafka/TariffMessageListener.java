package com.neoflex.product.integration.kafka;

import com.neoflex.product.dto.TariffKafkaMessage;
import com.neoflex.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TariffMessageListener {

    private final ProductService productService;

    @KafkaListener(
            topics = "install-tariff",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "tariffKafkaListenerContainerFactory")
    public void listenTopicInstallTariff(@Payload TariffKafkaMessage tariffKafkaMessage) {
        log.info("Received Tariff Kafka Message - {} - in the topic install-tariff", tariffKafkaMessage);
        productService.installTariff(tariffKafkaMessage);
    }
}
