package com.neoflex.credentials.service.util;

import com.neoflex.credentials.service.util.impl.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidatorFactory {

    public static final String APP_MAIL = "mail";
    public static final String APP_MOBILE = "mobile";
    public static final String APP_BANK = "bank";
    public static final String APP_GOSUSLUGI = "gosuslugi";

    public static Validator getValidator(String applicationType) {
        return switch (applicationType.toLowerCase()) {
            case APP_MAIL -> new MailAppValidator();
            case APP_MOBILE -> new MobileAppValidator();
            case APP_BANK -> new BankAppValidator();
            case APP_GOSUSLUGI -> new GosuslugiAppValidator();
            default -> null;
        };
    }
}
