package net.griefergames.reloaded.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.val;
import net.griefergames.reloaded.GrieferGamesReloaded;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
  public static final HikariConfig HIKARI_CONFIG =
      new HikariConfig(
          GrieferGamesReloaded.PLUGIN.getPlugin().getDataFolder().getAbsolutePath()
              + "\\"
              + "datasource.properties");
  public static final HikariDataSource HIKARI_DATA_SOURCE;

  static {
    val properties =
        GrieferGamesReloaded.PLUGIN
            .getConfigHandler()
            .getPropertiesReader()
            .getDataSourceProperties();

    HIKARI_CONFIG.setMaximumPoolSize((int) properties.get("dataSource.maximumPoolSize"));

    HIKARI_CONFIG.setDriverClassName(properties.getProperty("dataSource.driverClassName"));
    HIKARI_CONFIG.setUsername(properties.getProperty("dataSource.user"));
    HIKARI_CONFIG.addDataSourceProperty("port", properties.getProperty("dataSource.port"));
    HIKARI_CONFIG.setPassword(properties.getProperty("dataSource.password"));
    HIKARI_CONFIG.addDataSourceProperty(
        "databaseName", properties.getProperty("dataSource.databaseName"));
    HIKARI_CONFIG.addDataSourceProperty(
        "severName", properties.getProperty("dataSource.serverName"));

    HIKARI_CONFIG.setJdbcUrl(
        properties
            .getProperty("dataSource.jbdcUrl")
            .replace("{0}", HIKARI_CONFIG.getDataSourceProperties().getProperty("serverName"))
            .replace("{1}", HIKARI_CONFIG.getDataSourceProperties().getProperty("port"))
            .replace("{2}", HIKARI_CONFIG.getDataSourceProperties().getProperty("databaseName")));

    if (properties.getProperty("dataSource.driverClassName").equals("org.sqlite.SQLiteDataSource"))
      HIKARI_CONFIG.setJdbcUrl("jdbc:sqlite:plugins/GrieferGames/database.db");

    HIKARI_CONFIG.addDataSourceProperty(
        "cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
    HIKARI_CONFIG.addDataSourceProperty(
        "prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
    HIKARI_CONFIG.addDataSourceProperty(
        "prepStmtCacheSqlLimit", properties.getProperty("dataSource.prepStmtCacheSqlLimit"));
    HIKARI_CONFIG.addDataSourceProperty(
        "useServerPrepStmts", properties.getProperty("dataSource.useServerPrepStmts"));
    HIKARI_CONFIG.addDataSourceProperty(
        "useLocalSessionState", properties.getProperty("dataSource.useLocalSessionState"));
    HIKARI_CONFIG.addDataSourceProperty(
        "rewriteBatchedStatements", properties.getProperty("dataSource.rewriteBatchedStatements"));
    HIKARI_CONFIG.addDataSourceProperty(
        "cacheResultSetMetadata", properties.getProperty("dataSource.cacheResultSetMetadata"));
    HIKARI_CONFIG.addDataSourceProperty(
        "cacheServerConfiguration", properties.getProperty("dataSource.cacheServerConfiguration"));
    HIKARI_CONFIG.addDataSourceProperty(
        "elideSetAutoCommits", properties.getProperty("dataSource.elideSetAutoCommits"));
    HIKARI_CONFIG.addDataSourceProperty(
        "maintainTimeStats", properties.getProperty("dataSource.maintainTimeStats"));

    HIKARI_DATA_SOURCE = new HikariDataSource(HIKARI_CONFIG);
  }

  private DataSource() {}

  public static Connection getConnection() throws SQLException {
    return HIKARI_DATA_SOURCE.getConnection();
  }
}
