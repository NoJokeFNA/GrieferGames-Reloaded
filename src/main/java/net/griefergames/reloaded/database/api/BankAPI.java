package net.griefergames.reloaded.database.api;

import lombok.NonNull;
import net.griefergames.reloaded.database.factory.HikariSqlFactory;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class BankAPI extends HikariSqlFactory {

    public boolean playerExists( @NonNull final UUID playerUuid ) {
        final AtomicBoolean value = new AtomicBoolean( false );

        final String sqlQuery = "SELECT * FROM `gg_bank` WHERE `bank_player_uuid` = ?";
        super.executeQuery( sqlQuery, new Object[] { playerUuid.toString() }, new SqlType[] { SqlType.STRING }, resultSet -> {
            try {
                value.set( resultSet.next() );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query" );
            }
        } );

        return value.get();
    }
}
