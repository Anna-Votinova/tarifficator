package com.neoflex.credentials.service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NullOrBlankUtil {

    public static boolean isNullOrBlank(String field) {
        return Objects.isNull(field) || field.isBlank();
    }
}
