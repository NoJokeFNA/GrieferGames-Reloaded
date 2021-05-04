package net.griefergames.reloaded.database.factory;

import lombok.NonNull;
import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class HikariSqlFactory {

    /**
     * Execute a query in your database and get directly the result by the {@code preparedStatementConsumer}
     *
     * @param sqlQuery                  the full sql-query
     * @param preparedStatementConsumer the {@link PreparedStatement} callback
     */
    public void executeQuery( @NonNull final String sqlQuery, @NonNull final Consumer<PreparedStatement> preparedStatementConsumer ) {
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            preparedStatementConsumer.accept( preparedStatement );
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error executing sql-query" );
        }
    }
}