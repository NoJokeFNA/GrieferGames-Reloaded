package net.griefergames.reloaded.database.factory;

import lombok.NonNull;
import lombok.Setter;

public abstract class HikariSqlFactory {

    @Setter
    @NonNull
    private String table;

    public void keyExists() {

    }
}