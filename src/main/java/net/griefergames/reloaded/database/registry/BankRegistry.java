package net.griefergames.reloaded.database.registry;

import net.griefergames.reloaded.database.executor.HikariSqlExecutor;
import net.griefergames.reloaded.exception.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class BankRegistry extends HikariSqlExecutor {

    public boolean playerExists( @NotNull final UUID playerUuid ) {
        final var value = new AtomicBoolean( false );

        final var sqlQuery = "SELECT * FROM `gg_bank` WHERE `bank_player_uuid` = ?";
        super.executeQuery( sqlQuery, new Object[] { playerUuid.toString() }, new SqlType[] { SqlType.STRING }, resultSet -> {
            try {
                value.set( resultSet.next() );
            } catch ( SQLException exception ) {
                ExceptionHandler.handleException( exception, "Error while executing sql-query", false );
            }
        } );

        return value.get();
    }
}
