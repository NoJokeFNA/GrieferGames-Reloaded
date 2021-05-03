package net.griefergames.reloaded.database.builder;

import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder {

    public void buildConnection() {
        final String sqlQuery = "";
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            preparedStatement.executeUpdate();
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error while executing sql-statement" );
        }
    }
}
