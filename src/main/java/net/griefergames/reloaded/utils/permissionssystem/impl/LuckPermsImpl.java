package net.griefergames.reloaded.utils.permissionssystem.impl;

import lombok.NonNull;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LuckPermsImpl implements IPermissionsSystem {

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

    @Override
    public void setGroup( final Player player, @NonNull final Player targetPlayer, @NonNull String groupName, final long duration, @NonNull final TimeUnit timeUnit ) {
        final LuckPerms luckPerms = LuckPermsProvider.get();

        final UserManager userManager = luckPerms.getUserManager();
        final User user = userManager.getUser( targetPlayer.getUniqueId() );
        if ( user == null )
            throw new UnsupportedOperationException( "User cannot be null" );

        final Group group = luckPerms.getGroupManager().getGroup( groupName );
        if ( group == null )
            throw new UnsupportedOperationException( "Group cannot be null" );

        final Set<String> userGroups = user
                .getNodes( NodeType.INHERITANCE )
                .stream()
                .map( InheritanceNode::getGroupName )
                .collect( Collectors.toSet() );

        if ( userGroups.contains( group.getName() ) ) {
            if ( player != null )
                player.sendMessage( "Der Spieler hat bereits den " + groupName + " Rang!" );

            GrieferGamesLogger.log( Level.INFO, "Player already has the rank" );
            return;
        }

        final InheritanceNode inheritanceNode = InheritanceNode
                .builder( group )
                .expiry( duration, timeUnit )
                .value( true )
                .build();

        final DataMutateResult dataMutateResult = user.data().add( inheritanceNode );
        if ( !dataMutateResult.wasSuccessful() ) {
            GrieferGamesLogger.log( Level.WARNING, "An internal error occurred within LuckPerms" );
            return;
        }

        userManager.saveUser( user );

        GrieferGamesLogger.log( Level.INFO, "Successfully modified {0}'s rank to {1}.", player.getName(), groupName );
    }
}
