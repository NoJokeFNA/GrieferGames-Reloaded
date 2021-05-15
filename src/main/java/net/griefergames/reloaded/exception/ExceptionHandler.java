package net.griefergames.reloaded.exception;

import lombok.NonNull;
import lombok.val;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;

import java.util.logging.Level;

public class ExceptionHandler {

    public static void handleException( @NonNull final Exception exception, @NonNull final String message, final boolean printStackTrace ) {
        val stackTraceElement = exception.getStackTrace()[4];
        val className = stackTraceElement.getClassName();
        val methodName = stackTraceElement.getMethodName();
        val lineNumber = stackTraceElement.getLineNumber();

        GrieferGamesLogger.log( Level.WARNING, "Message: {0} \nClass: {1} \nMethod: {2} \nLine: {3} \nExecption: {4}", new Object[] { message, className, message, lineNumber, exception } );

        if ( printStackTrace )
            exception.printStackTrace();
    }
}
