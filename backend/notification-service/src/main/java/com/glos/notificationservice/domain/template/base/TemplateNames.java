package com.glos.notificationservice.domain.template.base;

import com.glos.notificationservice.domain.template.*;

import java.util.function.Function;

public enum TemplateNames {
    CHANGE_EMAIL("change-email", ChangeEmailMessageTemplate::new),
    CHANGE_PASSWORD("change-password", ChangePasswordMessageTemplate::new),
    CHANGE_PHONE_NUMBER("change-phone-number", ChangePhoneNumberMessageTemplate::new),
    CHANGE_USERNAME("change-username", ChangeUsernameMessageTemplate::new),
    CONFIRM_EMAIL("confirm-email", ConfirmEmailMessageTemplate::new),
    CONFIRM_PHONE_NUMBER("confirm-phone-number", ConfirmPhoneNumberMessageTemplate::new),
    DROP_ACCOUNT("drop-account", DropAccountMessageTemplate::new),
    NOTIFICATION("notification", SimpleMessageTemplate::new),
    CUSTOM_HTML(null, HtmlMessageTemplate::new);

    public static TemplateNames valueOfIgnoreCase(String name) {
        return valueOf(name.toUpperCase().replace("-", "_"));
    }

    private final String template;
    private final Function<MessageProps, MessageTemplate> fun;

    TemplateNames(String template, Function<MessageProps, MessageTemplate> fun) {
        this.template = template;
        this.fun = fun;
    }

    public String template() {
        return template;
    }
    public Function<MessageProps, MessageTemplate> messageTemplate() { return fun; }
}
