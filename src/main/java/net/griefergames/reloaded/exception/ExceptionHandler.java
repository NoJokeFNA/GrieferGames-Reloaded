package net.griefergames.reloaded.exception;

import lombok.val;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;

import java.util.logging.Level;

public class ExceptionHandler {

    public static void handleException( Exception exception, String message ) {
        val stackTraceElement = exception.getStackTrace()[4];
        final String
                className = stackTraceElement.getClassName(),
                methodName = stackTraceElement.getMethodName();

        final int lineNumber = stackTraceElement.getLineNumber();

        GrieferGamesLogger.log( Level.WARNING, "{0}: {{1} - {2} - {3}} - {4}", message, className, methodName, lineNumber, exception );
    }
}
