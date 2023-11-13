package com.neoflex.credentials.service.util;

import com.neoflex.credentials.service.util.impl.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorFactory {

    public static Validator getValidator(String applicationType) {
        return switch (applicationType.toLowerCase()) {
            case "mail" -> new MailAppValidator();
            case "mobile" -> new MobileAppValidator();
            case "bank" -> new BankAppValidator();
            case "gosuslugi" -> new GosuslugiAppValidator();
            default -> new DefaultAppValidator();
        };
    }
}
