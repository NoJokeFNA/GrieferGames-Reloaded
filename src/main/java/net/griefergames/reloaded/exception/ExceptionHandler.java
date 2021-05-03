package net.griefergames.reloaded.exception;

import lombok.val;

public class ExceptionHandler {

    public static void handleException( Exception exception, String message ) {
        val stackTraceElement = exception.getStackTrace()[4];
        final String
                className = stackTraceElement.getClassName(),
                methodName = stackTraceElement.getMethodName();

        final int lineNumber = stackTraceElement.getLineNumber();

        System.out.println( message + ": {" + className + " - " + methodName + " - " + lineNumber + "} - " + exception );
    }
}
