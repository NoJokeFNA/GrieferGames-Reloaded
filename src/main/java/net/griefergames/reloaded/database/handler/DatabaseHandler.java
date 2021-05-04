package net.griefergames.reloaded.database.handler;

import lombok.Getter;
import net.griefergames.reloaded.database.api.*;

@Getter
public class DatabaseHandler {

    private final BankAPI bankAPI;
    private final BoosterAPI boosterAPI;
    private final ClansAPI clansAPI;
    private final CooldownAPI cooldownAPI;
    private final TransactionsAPI transactionsAPI;

    public DatabaseHandler() {
        this.bankAPI = new BankAPI();
        this.boosterAPI = new BoosterAPI();
        this.clansAPI = new ClansAPI();
        this.cooldownAPI = new CooldownAPI();
        this.transactionsAPI = new TransactionsAPI();
    }
}
