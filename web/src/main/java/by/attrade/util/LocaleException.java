package by.attrade.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocaleException {
    private Exception exception;
    private String code;
    private String attrName;
    private Object[] args;

    public LocaleException(Exception exception, Object... args) {
        this.exception = exception;
        this.code = exception.getClass().getName();
        this.attrName = exception.getClass().getSimpleName();
        this.args = args;
    }

    public LocaleException(Exception exception, String code, String attrName, Object... args) {
        this.exception = exception;
        this.code = code;
        this.attrName = attrName;
        this.args = args;
    }
}
