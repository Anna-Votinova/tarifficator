package com.neoflex.product.service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CheckDateUtil {

    public static boolean isDateBefore(LocalDate toDate, LocalDate fromDate) {
        return toDate.isBefore(fromDate);
    }
}
