package net.griefergames.reloaded.database.builder;

import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseBuilder {

    public void createTable() {
        /*
        clan_info -> clan_name; clan_tag; clan_cb
        clan_members -> player_uuid, player_name, player_rank;
         */
        final String clanSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_clans` (" +
                "  id               INT(11)      NOT NULL AUTO_INCREMENT," +
                "  clan_info        VARCHAR(100) NOT NULL," +
                "  clan_max_members INT(3)       NOT NULL," +
                "  clan_money       INT(11)      NOT NULL," +
                "  clan_members     MEDIUMTEXT   NOT NULL," +
                "  clan_invites     MEDIUMTEXT   NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        final String boosterSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_booster` (" +
                "  id               INT(11)     NOT NULL AUTO_INCREMENT," +
                "  booster_type     VARCHAR(10) NOT NULL," +
                "  booster_level    INT(1)      NOT NULL," +
                "  booster_cooldown BIGINT      NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        /*
        cooldown_info -> player_uuid; player_name
         */
        final String cooldownSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_cooldown` (" +
                "  id               INT(11)      NOT NULL AUTO_INCREMENT," +
                "  cooldown_info    VARCHAR(100) NOT NULL," +
                "  cooldown_type    VARCHAR(10)  NOT NULL," +
                "  cooldown         BIGINT       NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        this.createTable( clanSqlQuery );
        this.createTable( boosterSqlQuery );
        this.createTable( cooldownSqlQuery );
    }

    private void createTable( final String... sqlQuery ) {
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( Arrays.toString( sqlQuery ) ) ) {
            preparedStatement.executeUpdate();
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error while executing sql-statement" );
        }
    }
}
