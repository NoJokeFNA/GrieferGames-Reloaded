package net.griefergames.reloaded.exception;

import lombok.NonNull;
import lombok.val;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;

import java.util.logging.Level;

public class ExceptionHandler {

    public static void handleException( @NonNull final Exception exception, @NonNull final String message ) {
        val stackTraceElement = exception.getStackTrace()[4];
        final String
                className = stackTraceElement.getClassName(),
                methodName = stackTraceElement.getMethodName();

        final int lineNumber = stackTraceElement.getLineNumber();

        GrieferGamesLogger.log( Level.WARNING, "%s: {%s - %s - %d} - %s", message, className, methodName, lineNumber, exception );
    }
}
