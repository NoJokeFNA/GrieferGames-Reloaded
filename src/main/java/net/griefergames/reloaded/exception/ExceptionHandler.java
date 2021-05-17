package net.griefergames.reloaded.exception;

import lombok.NonNull;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;

import java.util.logging.Level;

public class ExceptionHandler {

    public static void handleException( @NonNull final Exception exception, @NonNull final String message, final boolean printStackTrace ) {
        var stackTraceElement = exception.getStackTrace()[4];
        var className = stackTraceElement.getClassName();
        var methodName = stackTraceElement.getMethodName();
        var lineNumber = stackTraceElement.getLineNumber();

        GrieferGamesLogger.log( Level.WARNING, "Message: {0} \nClass: {1} \nMethod: {2} \nLine: {3} \nExecption: {4}", new Object[] { message, className, message, lineNumber, exception } );

        if ( printStackTrace )
            exception.printStackTrace();
    }
}
