package net.griefergames.reloaded.database.api;

import lombok.NonNull;
import net.griefergames.reloaded.database.executor.HikariSqlExecutor;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerHolographicAPI extends HikariSqlExecutor {

    public boolean playerExists( @NonNull final UUID playerUuid ) {
        final AtomicBoolean value = new AtomicBoolean( false );

        final String sqlQuery = "SELECT * FROM `gg_playerholo` WHERE holo_player = ?";
        super.executeQuery( sqlQuery, new Object[] { playerUuid.toString() }, new SqlType[] { SqlType.STRING }, resultSet -> {
            try {
                value.set( resultSet.next() );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query", false );
            }
        } );

        return value.get();
    }

    public boolean holographicExists( final int holographicId ) {
        final AtomicBoolean value = new AtomicBoolean( false );

        final String sqlQuery = "SELECT * FROM `gg_playerholo` WHERE `holo_info` REGEXP ';(?<status>\\d+)?(" + holographicId + ")';";
        super.executeQuery( sqlQuery, new Object[] { holographicId }, new SqlType[] { SqlType.INTEGER }, resultSet -> {
            try {
                value.set( resultSet.next() );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query", true );
            }
        } );

        return value.get();
    }
}
