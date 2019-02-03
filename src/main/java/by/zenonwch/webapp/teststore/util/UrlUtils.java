package by.zenonwch.webapp.teststore.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UrlUtils {

    public String currentUrlReplaceParam(final String... params) {
//        final RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
//
//        if (attribs instanceof NativeWebRequest) {
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();

        for (final String param : params) {
            builder.replaceQueryParam(param);
        }

        return builder.toUriString();
//        }
//
//        return null;
    }
}