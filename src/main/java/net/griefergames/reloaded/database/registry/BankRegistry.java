package net.griefergames.reloaded.database.registry;

import lombok.val;
import net.griefergames.reloaded.database.executor.HikariSqlExecutor;
import net.griefergames.reloaded.exception.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BankRegistry extends HikariSqlExecutor {
  /**
   * Check if the player exists in the bank database, or not.
   *
   * @param playerUuid the uuid of the player
   * @return true, if the player exists, false if not
   */
  public boolean playerExists(@NotNull final UUID playerUuid) {
    val value = new AtomicBoolean(false);

    val sqlQuery = "SELECT * FROM `gg_bank` WHERE `bank_player_uuid` = ?";
    super.executeQuery(
        sqlQuery,
        new Object[] {playerUuid.toString()},
        new SqlType[] {SqlType.STRING},
        resultSet -> {
          try {
            value.set(resultSet.next());
          } catch (SQLException exception) {
            ExceptionHandler.handleException(exception, "Error while executing sql-query", false);
          }
        });

    return value.get();
  }

  /**
   * Get the current bank amount of the player.
   *
   * @param playerUuid the uuid of the player
   * @return the player's bank amount
   */
  public BigDecimal getBankAmount(@NotNull final UUID playerUuid) {
    AtomicReference<BigDecimal> coins = new AtomicReference<>(BigDecimal.ZERO);

    val sqlQuery = "SELECT `bank_amount` FROM `gg_bank` WHERE `bank_player_uuid` = ?";
    super.executeQuery(
            sqlQuery,
            new Object[] {playerUuid.toString()},
            new SqlType[] {SqlType.STRING},
            resultSet -> {
              try {
                if (resultSet.next()) {
                  coins.set(resultSet.getBigDecimal("bank_amount"));
                }
              } catch (SQLException exception) {
                ExceptionHandler.handleException(exception, "Error while executing sql-query", false);
              }
            });

    return coins.get();
  }

  /**
   * Update the bank amount of the player specified by the {@code bankType}.
   * You can choose between {@code ADD}, {@code REMOVE} and {@code SET}.
   *
   * @param playerUuid the uuid of the player
   * @param coins the coins amount
   * @param bankType the update type
   * 
   * @see #getBankAmount(UUID)
   * @see BankType
   */
  public void updateBank(@NotNull final UUID playerUuid, final BigDecimal coins,
                         final BankType bankType) {
    val currentCoins = this.getBankAmount(playerUuid);
    var newCoins = BigDecimal.ZERO;
    switch (bankType) {
      case ADD -> newCoins = currentCoins.add(coins);
      case REMOVE -> newCoins = currentCoins.subtract(coins);
      case SET -> newCoins = coins;
    }

    val sqlQuery = "UPDATE `gg_bank` SET `bank_amount` = ? WHERE `bank_player_uuid` = ?";
    super.executeQuery(
            sqlQuery,
            new Object[] {newCoins, playerUuid.toString()},
            new SqlType[] {SqlType.BIG_DECIMAL, SqlType.STRING},
            null);
  }

  private enum BankType {
    ADD,
    REMOVE,
    SET;
  }
}
