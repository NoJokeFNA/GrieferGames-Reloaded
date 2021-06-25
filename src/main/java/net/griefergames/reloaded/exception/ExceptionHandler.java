package net.griefergames.reloaded.exception;

import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger("griefergames-logger");

    public static void handleException(@NotNull final Exception exception, @NotNull final String message, final boolean printStackTrace) {
        val packageArray = ExceptionHandler.class.getPackageName().split("[.]");
        val packageName = packageArray[0] + "." + packageArray[1];

        val i = new AtomicInteger(0);
        val j = new AtomicInteger(0);

        Arrays.stream(exception.getStackTrace()).forEach(traceElement -> {
            i.getAndIncrement();
            if (traceElement.getClassName().startsWith(packageName)) {
                j.getAndIncrement();
            }
        });

        val filteredExceptionLine = (i.get() - j.get()) + 1;

        val stackTraceElement = exception.getStackTrace()[filteredExceptionLine];
        val className = stackTraceElement.getClassName();
        val methodName = stackTraceElement.getMethodName();
        val lineNumber = stackTraceElement.getLineNumber();

        LOGGER.log(Level.WARNING, "Message: {0} \nClass: {1} \nMethod: {2} \nLine: {3} \nException: {4}", new Object[]{message, className, methodName, lineNumber, exception});

        if (printStackTrace)
            exception.printStackTrace();
    }
}
