package net.griefergames.reloaded.exception;

import lombok.NonNull;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger( "griefergames-logger" );

    public static void handleException( @NonNull final Exception exception, @NonNull final String message, final boolean printStackTrace ) {
        final var packageArray = ExceptionHandler.class.getPackageName().split( "[.]" );
        final var packageName  = packageArray[0] + "." + packageArray[1];

        final var i = new AtomicInteger( 0 );
        final var j = new AtomicInteger( 0 );

        Arrays.stream( exception.getStackTrace() ).forEach( traceElement -> {
            i.getAndIncrement();
            if ( traceElement.getClassName().startsWith( packageName ) ) {
                j.getAndIncrement();
            }
        } );

        final var filteredExceptionLine = ( i.get() - j.get() ) + 1;

        final var stackTraceElement = exception.getStackTrace()[filteredExceptionLine];
        final var className = stackTraceElement.getClassName();
        final var methodName = stackTraceElement.getMethodName();
        final var lineNumber = stackTraceElement.getLineNumber();

        LOGGER.log( Level.WARNING, "Message: {0} \nClass: {1} \nMethod: {2} \nLine: {3} \nException: {4}", new Object[] { message, className, methodName, lineNumber, exception } );

        if ( printStackTrace )
            exception.printStackTrace();
    }
}
