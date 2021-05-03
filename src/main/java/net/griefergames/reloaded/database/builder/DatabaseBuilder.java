package net.griefergames.reloaded.database.builder;

import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder {

    public void buildConnection() {
        final String sqlQuery = "";
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            preparedStatement.executeUpdate();
        } catch ( SQLException exception ) {
            ExceptionFilter.filterException( exception, "Error while executing sql-statement" );
        }
    }
}
