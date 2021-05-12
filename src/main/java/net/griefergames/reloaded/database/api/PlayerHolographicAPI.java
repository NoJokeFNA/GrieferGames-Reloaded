package net.griefergames.reloaded.database.api;

import net.griefergames.reloaded.database.factory.HikariSqlFactory;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerHolographicAPI extends HikariSqlFactory {

    public boolean holographicExists( final int holographicId ) {
        final AtomicBoolean value = new AtomicBoolean( false );

        final String sqlQuery = "SELECT * FROM `gg_playerholo` WHERE `holo_info` REGEXP ';(?<status>\\d+)?(" + holographicId + ")';";
        super.executeQuery( sqlQuery, new Object[] { holographicId }, new SqlType[] { SqlType.INTEGER }, resultSet -> {
            try {
                value.set( resultSet.next() );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query" );
            }
        } );

        return value.get();
    }
}
