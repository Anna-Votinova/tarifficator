package com.neoflex.product.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TariffKafkaMessage {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private UUID productId;
    private Double rate;
    private Long author;
    private long version;
    private String email;
}
