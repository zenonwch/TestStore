package by.zenonwch.webapp.teststore.exception;

public class JsonParsingException extends RuntimeException {
    public JsonParsingException(final String msg, final Throwable th) {
        super(msg, th);
    }
}