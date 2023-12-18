package com.neoflex.tariffs.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
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
