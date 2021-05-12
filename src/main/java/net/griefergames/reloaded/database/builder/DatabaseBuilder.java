package net.griefergames.reloaded.database.builder;

import net.griefergames.reloaded.database.DataSource;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder {

    public void createTable() {
        /*
        bank_player_info -> player_uuid; player_name
         */
        final String bankSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_bank` (" +
                "  id               INT(11)      NOT NULL AUTO_INCREMENT," +
                "  bank_player_info VARCHAR(100) NOT NULL," +
                "  bank_amount      VARCHAR(32)  NOT NULL," +
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

        /*
        cooldown_player_info -> player_uuid; player_name
         */
        final String cooldownSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_cooldown` (" +
                "  id                   INT(11)      NOT NULL AUTO_INCREMENT," +
                "  cooldown_player_info VARCHAR(100) NOT NULL," +
                "  cooldown_type        VARCHAR(10)  NOT NULL," +
                "  cooldown             BIGINT       NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        /*
        holo_player_info -> player_uuid; player_name
        holo_info -> holo_id, holo_location, holo_text;
         */
        final String playerHolographicSql = "CREATE TABLE IF NOT EXISTS `gg_playerholo` (" +
                "  id               INT(11)     NOT NULL AUTO_INCREMENT," +
                "  holo_player_info VARCHAR(64) NOT NULL," +
                "  holo_info        MEDIUMTEXT  NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        final String transactionsSqlQuery = "CREATE TABLE IF NOT EXISTS `gg_transactions` (" +
                "  id                 INT(11)      NOT NULL AUTO_INCREMENT," +
                "  transaction_player VARCHAR(64)  NOT NULL," +
                "  transaction_type   VARCHAR(32)  NOT NULL," +
                "  transaction_amount INT(11)      NOT NULL," +
                "  transaction_date   BIGINT       NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";

        this.createTable( bankSqlQuery );
        this.createTable( boosterSqlQuery );
        this.createTable( clanSqlQuery );
        this.createTable( cooldownSqlQuery );
        this.createTable( playerHolographicSql );
        this.createTable( transactionsSqlQuery );
    }

    /**
     * Simply create a table by inserting the sql-query
     *
     * @param sqlQuery the sql-query
     */
    private void createTable( final String sqlQuery ) {
        try ( Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement( sqlQuery ) ) {
            preparedStatement.executeUpdate();
        } catch ( SQLException exception ) {
            ExceptionHandler.handleException( exception, "Error while executing sql-statement" );
        }
    }
}
