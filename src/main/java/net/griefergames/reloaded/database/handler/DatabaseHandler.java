package net.griefergames.reloaded.database.handler;

import lombok.Getter;
import net.griefergames.reloaded.database.registry.*;

@Getter
public class DatabaseHandler {

    private final BankRegistry bankRegistry;
    private final BoosterRegistry boosterRegistry;
    private final ClansRegistry clansRegistry;
    private final CooldownRegistry cooldownRegistry;
    private final TransactionsRegistry transactionsRegistry;

    public DatabaseHandler() {
        this.bankRegistry = new BankRegistry();
        this.boosterRegistry = new BoosterRegistry();
        this.clansRegistry = new ClansRegistry();
        this.cooldownRegistry = new CooldownRegistry();
        this.transactionsRegistry = new TransactionsRegistry();
    }
}
