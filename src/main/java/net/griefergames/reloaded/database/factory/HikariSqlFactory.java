package net.griefergames.reloaded.database.factory;

import lombok.NonNull;
import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public abstract class HikariSqlFactory {

    /**
     * Execute a query in your database and get directly the result by the {@code preparedStatementConsumer} - synchronously
     * <p>
     * <b>Use this method only if you need a return value</b>
     * <p>
     * <b>If you don't need a return value take a look at {@link #executeQuery(String, PreparedStatement, String[], SqlType...)}</b>
     *
     * @param sqlQuery                  the full sql-query
     * @param preparedStatementConsumer the {@link PreparedStatement} callback
     *
     * @see Consumer
     * @see Consumer#accept(Object)
     * @see PreparedStatement
     */
    public void executeQuery( @NonNull final String sqlQuery, @NonNull final Consumer<PreparedStatement> preparedStatementConsumer ) {
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            preparedStatementConsumer.accept( preparedStatement );
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error executing sql-query" );
        }
    }

    /**
     * Execute a query in your database - synchronously
     * <p>
     * <b>Use this method only if you don't need a return value</b>
     * <p>
     * <b>If you need a return value take a look at {@link #executeQuery(String, Consumer)}</b>
     * <p></p>
     * This method is a pretty simply way to execute a query directly into your database since it counts every placeholder - question mark - directly and replace it as well with the given types
     * <p></p>
     * For example:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", preparedStatement, new String[] { "firstKey", "secondKey" }, SqlType.STRING, SqlType.INTEGER )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks
     * <p>
     * So your `key` will be an {@link String} like <b>"Test"</b> and your `anotherKey` will be an {@link Integer} like <b>54</b>
     * <p></p>
     * Of course you can also do UPDATE-Statements with it, this was just an example to show you how it works
     *
     * @param sqlQuery          the full sql-query
     * @param preparedStatement the {@link PreparedStatement}
     * @param replacements      all the replacements
     * @param sqlTypes          the {@link SqlType}'s for the {@code replacements}
     *
     * @see SqlType
     * @see PreparedStatement
     */
    public void executeQuery( @NonNull final String sqlQuery, @NonNull final PreparedStatement preparedStatement, @NonNull final String[] replacements, @NonNull final SqlType... sqlTypes ) {
        final AtomicInteger index = new AtomicInteger( 0 );
        final String[] sqlQuerySplitter = sqlQuery.split( "[?]" );

        final int
                questionMarkAmount = sqlQuerySplitter.length,
                valuesAmount = replacements.length;

        if ( questionMarkAmount != valuesAmount )
            throw new IllegalArgumentException( "Count doesn't match! question marks = " + questionMarkAmount + " -> values = " + valuesAmount );

        final Map<String, SqlType> queryMap = new ConcurrentHashMap<>();
        for ( String replacement : replacements ) {
            for ( SqlType sqlType : sqlTypes ) {
                queryMap.put( replacement, sqlType );
            }
        }

        queryMap.forEach( ( replacement, sqlType ) -> {
            try {
                StatementFactory.setPreparedStatement( index.incrementAndGet(), sqlType, replacement, preparedStatement );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query" );
            }
        } );

        index.set( 0 );
    }

    /**
     * Execute a query in your database - asynchronously
     * <p>
     * <b>Use this method only if you don't need a return value</b>
     * <p>
     * <b>If you need a return value take a look at {@link #executeQuery(String, Consumer)}</b>
     * <p></p>
     * This method is a pretty simply way to execute a query directly into your database since it counts every placeholder - question mark - directly and replace it as well with the given types
     * <p></p>
     * For example:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", preparedStatement, new String[] { "firstKey", "secondKey" }, SqlType.STRING, SqlType.INTEGER )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks
     * <p>
     * So your `key` will be an {@link String} like <b>"Test"</b> and your `anotherKey` will be an {@link Integer} like <b>54</b>
     * <p></p>
     * Of course you can also do UPDATE-Statements with it, this was just an example to show you how it works
     *
     * @param sqlQuery          the full sql-query
     * @param preparedStatement the {@link PreparedStatement}
     * @param replacements      all the replacements
     * @param sqlTypes          the {@link SqlType}'s for the {@code replacements}
     *
     * @see CompletableFuture
     * @see CompletableFuture#runAsync(Runnable)
     * @see PreparedStatement
     */
    public CompletableFuture<Void> executeQueryAsync( @NonNull final String sqlQuery, @NonNull final PreparedStatement preparedStatement, @NonNull final String[] replacements, @NonNull final SqlType... sqlTypes ) {
        return CompletableFuture.runAsync( () -> this.executeQuery( sqlQuery, preparedStatement, replacements, sqlTypes ) );
    }

    /**
     * Execute a query in your database and get directly the result by the {@code preparedStatementConsumer} - asynchronously
     *
     * @param sqlQuery                  the full sql-query
     * @param preparedStatementConsumer the {@link PreparedStatement} callback
     *
     * @return the asynchronous {@link CompletableFuture}
     *
     * @see CompletableFuture
     * @see CompletableFuture#runAsync(Runnable)
     * @see Consumer
     * @see Consumer#accept(Object)
     * @see PreparedStatement
     */
    public CompletableFuture<Void> executeQueryAsync( @NonNull final String sqlQuery, @NonNull final Consumer<PreparedStatement> preparedStatementConsumer ) {
        return CompletableFuture.runAsync( () -> this.executeQuery( sqlQuery, preparedStatementConsumer ) );
    }

    private static class StatementFactory {

        private static void setPreparedStatement( final int index, final SqlType sqlType, final Object replacement, final PreparedStatement preparedStatement ) throws SQLException {
            switch ( sqlType ) {
                case NULL:
                    preparedStatement.setNull( index, ( int ) replacement );
                    break;

                case BOOLEAN:
                    preparedStatement.setBoolean( index, ( boolean ) replacement );
                    break;

                case BYTE:
                    preparedStatement.setByte( index, ( byte ) replacement );
                    break;

                case SHORT:
                    preparedStatement.setShort( index, ( short ) replacement );
                    break;

                case INTEGER:
                    preparedStatement.setInt( index, ( int ) replacement );
                    break;

                case LONG:
                    preparedStatement.setLong( index, ( long ) replacement );
                    break;

                case FLOAT:
                    preparedStatement.setFloat( index, ( float ) replacement );
                    break;

                case DOUBLE:
                    preparedStatement.setDouble( index, ( double ) replacement );
                    break;

                case BIG_DECIMAL:
                    preparedStatement.setBigDecimal( index, ( BigDecimal ) replacement );
                    break;

                case STRING:
                    preparedStatement.setString( index, ( String ) replacement );
                    break;

                case BYTES:
                    preparedStatement.setBytes( index, ( byte[] ) replacement );
                    break;

                case DATE:
                    preparedStatement.setDate( index, ( Date ) replacement );
                    break;

                case TIME:
                    preparedStatement.setTime( index, ( Time ) replacement );
                    break;

                case TIMESTAMP:
                    preparedStatement.setTimestamp( index, ( Timestamp ) replacement );
                    break;

                case OBJECT:
                    preparedStatement.setObject( index, replacement );
                    break;

                case ARRAY:
                    preparedStatement.setArray( index, ( Array ) replacement );
                    break;

                case URL:
                    preparedStatement.setURL( index, ( URL ) replacement );
                    break;
            }

            System.out.println( sqlType + " - " + preparedStatement.toString() + " - " + index + " - " + replacement );
        }
    }

    private enum SqlType {

        NULL,
        BOOLEAN,
        BYTE,
        SHORT,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        BIG_DECIMAL,
        STRING,
        BYTES,
        DATE,
        TIME,
        TIMESTAMP,
        OBJECT,
        ARRAY,
        URL;
    }
}