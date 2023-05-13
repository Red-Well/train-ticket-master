package com.hhb.trainbookingsystem.annotation;


import com.hhb.trainbookingsystem.enums.OperationType;
import com.hhb.trainbookingsystem.enums.OperationUnit;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogDetail {

    String detail() default "";

    int level() default 0;

    OperationType operationType() default OperationType.UNKNOWN;

    OperationUnit operationUnit() default OperationUnit.UNKNOWN;
}
