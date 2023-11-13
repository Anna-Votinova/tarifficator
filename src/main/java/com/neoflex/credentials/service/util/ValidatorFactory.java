package com.neoflex.credentials.service.util;

import com.neoflex.credentials.dto.enums.ApplicationType;
import com.neoflex.credentials.service.util.impl.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorFactory {

    public static Validator getValidator(ApplicationType applicationType) {
        return switch (applicationType) {
            case MAIL -> new MailAppValidator();
            case MOBILE -> new MobileAppValidator();
            case BANK -> new BankAppValidator();
            case GOSUSLUGI -> new GosuslugiAppValidator();
            default -> new DefaultAppValidator();
        };
    }
}
