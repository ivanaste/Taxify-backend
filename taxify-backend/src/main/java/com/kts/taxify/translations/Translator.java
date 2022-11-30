package com.kts.taxify.translations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    public Translator(@Qualifier("messages") ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(Translation translation, String[] params) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(translation.getCode(), params, locale);
    }

    public static String toLocale(Translation translation) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(translation.getCode(), null, locale);
    }
}