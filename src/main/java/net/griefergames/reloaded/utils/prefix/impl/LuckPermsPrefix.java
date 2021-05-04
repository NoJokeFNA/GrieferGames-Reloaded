package net.griefergames.reloaded.utils.prefix.impl;

import lombok.NonNull;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.prefix.IPrefix;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

public class LuckPermsPrefix implements IPrefix {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final LuckPerms luckPerms = LuckPermsProvider.get();
        final User user = luckPerms.getUserManager().getUser( player.getUniqueId() );
        if ( user == null )
            return "Unknown user data";

        final ContextManager contextManager = luckPerms.getContextManager();
        final QueryOptions queryOptions = contextManager.getQueryOptions( user ).orElse( contextManager.getStaticQueryOptions() );
        final CachedMetaData cachedMetaData = user.getCachedData().getMetaData( queryOptions );
        final String prefix = cachedMetaData.getPrefix();
        if ( prefix == null )
            return "Unknown meta data (prefix)";

        return ChatUtil.sendColoredMessage( prefix );
    }
}
