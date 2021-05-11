package net.griefergames.reloaded.database.factory;

import lombok.NonNull;
import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class HikariSqlFactory {

    /**
     * Execute a query in your database and get directly the {@link ResultSet} by the {@code resultSetCallback} - synchronously
     * <p>
     * <b>If you don't need a callback, just set the {@code resultSetCallback} to <b>null</b>></b>
     * <p></p>
     * This method is a pretty simply way to execute a query directly into your database since it counts every placeholder - question mark - directly and replace it as well with the given types
     * <p></p>
     * For example:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", new String[] { "firstKey", "secondKey" }, new SqlType[] { SqlType.STRING, SqlType.INTEGER }, null )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks
     * <p></p>
     * Another example with a callback:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", new String[] { "firstKey", "secondKey" }, new SqlType[] { SqlType.STRING, SqlType.INTEGER }, resultSetCallback -> {} )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks and gives you the callback
     * <p>
     * So your `key` will be an {@link String} like <b>"Test"</b> and your `anotherKey` will be an {@link Integer} like <b>54</b>
     * <p></p>
     * Of course you can also use UPDATE-Statements with it, this was just an example to show you how it works
     *
     * @param sqlQuery          the full sql-query
     * @param replacements      all the replacements
     * @param sqlTypes          the {@link SqlType}'s for the {@code replacements}
     * @param resultSetCallback the {@link ResultSet} callback
     *
     * @see #executeQueryAsync(String, String[], SqlType[], Consumer) 
     * @see SqlType
     * @see StatementFactory#setPreparedStatement(int, Object, SqlType, PreparedStatement)
     * @see Consumer#accept(Object)
     * @see ResultSet
     */
    public void executeQuery( @NonNull final String sqlQuery, @NonNull final String[] replacements, @NonNull final SqlType[] sqlTypes, final Consumer<ResultSet> resultSetCallback ) {
        final String[] sqlQuerySplitter = sqlQuery.split( "[?]" );

        final int
                placeholdersAmount = sqlQuerySplitter.length,
                replacementsAmount = replacements.length;

        if ( placeholdersAmount != replacementsAmount )
            throw new IllegalArgumentException( "Count doesn't match! placeholders = " + placeholdersAmount + " -> replacements = " + replacementsAmount );

        final Map<String, SqlType> queryMap = new ConcurrentHashMap<>();
        for ( final String replacement : replacements ) {
            for ( final SqlType sqlType : sqlTypes ) {
                queryMap.put( replacement, sqlType );
            }
        }

        final List<Map.Entry<String, SqlType>> entryArray = new ArrayList<>( queryMap.entrySet() );
        try ( final Connection connection = DataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            for ( int index = 0; index < queryMap.size(); index++ ) {
                final Map.Entry<String, SqlType> entry = entryArray.get( index );
                StatementFactory.setPreparedStatement( ( index + 1 ), entry.getKey(), entry.getValue(), preparedStatement );

                if ( resultSetCallback != null ) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    resultSetCallback.accept( resultSet );
                }
            }
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error while executing sql-query" );
        }
    }

    /**
     * Execute a query in your database and get directly the {@link ResultSet} by the {@code resultSetCallback} - asynchronously
     * <p>
     * <b>If you don't need a callback, just set the {@code resultSetCallback} to <b>null</b>></b>
     * <p></p>
     * This method is a pretty simply way to execute a query directly into your database since it counts every placeholder - question mark - directly and replace it as well with the given types
     * <p></p>
     * For example:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", new String[] { "firstKey", "secondKey" }, new SqlType[] { SqlType.STRING, SqlType.INTEGER }, null )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks
     * <p></p>
     * Another example with a callback:
     * <p>
     * {@code #executeQuery( "SELECT * FROM `table_name` WHERE `key` = ? AND `anotherKey` = ?", new String[] { "firstKey", "secondKey" }, new SqlType[] { SqlType.STRING, SqlType.INTEGER }, resultSetCallback -> {} )}
     * <p></p>
     * This example shows you a query that executes 2 values - string and integer - with the 2 placeholders - question marks and gives you the callback
     * <p>
     * So your `key` will be an {@link String} like <b>"Test"</b> and your `anotherKey` will be an {@link Integer} like <b>54</b>
     * <p></p>
     * Of course you can also use UPDATE-Statements with it, this was just an example to show you how it works
     *
     * @param sqlQuery          the full sql-query
     * @param replacements      all the replacements
     * @param sqlTypes          the {@link SqlType}'s for the {@code replacements}
     * @param resultSetCallback the {@link ResultSet} callback
     *
     * @see #executeQuery(String, String[], SqlType[], Consumer) 
     * @see SqlType
     * @see StatementFactory#setPreparedStatement(int, Object, SqlType, PreparedStatement)
     * @see Consumer#accept(Object)
     * @see ResultSet
     * @see CompletableFuture#runAsync(Runnable) 
     */
    public CompletableFuture<Void> executeQueryAsync( @NonNull final String sqlQuery, @NonNull final String[] replacements, @NonNull final SqlType[] sqlTypes, final Consumer<ResultSet> resultSetCallback ) {
        return CompletableFuture.runAsync( () -> this.executeQuery( sqlQuery, replacements, sqlTypes, resultSetCallback ) );
    }

    private static class StatementFactory {

        /**
         * Execute a {@link PreparedStatement} by the given types
         *
         * @param index             the index you want to set
         * @param sqlType           the {@link SqlType} you want to use
         * @param replacement       all the replacements
         * @param preparedStatement the {@link PreparedStatement}
         *
         * @see SqlType
         * @see PreparedStatement
         */
        private static void setPreparedStatement( final int index, @NonNull final Object replacement, @NonNull final SqlType sqlType, @NonNull final PreparedStatement preparedStatement ) throws SQLException {
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
        }
    }

    public enum SqlType {

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