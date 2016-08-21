package io.spiffy.website.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface AccessControl {

    public static String DEFAULT_RETURN_URI = "<requested_uri>";

    String returnUri() default DEFAULT_RETURN_URI;
}
