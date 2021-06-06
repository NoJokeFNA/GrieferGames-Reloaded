package net.griefergames.reloaded.utils.permissionssystem.impl;

import org.jetbrains.annotations.NotNull;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LuckPermsImpl implements IPermissionsSystem {

    @Override
    public String getPrefix( @NotNull Player player ) {
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
    public void setGroup( final Player player, @NotNull final Player targetPlayer, @NotNull String groupName, final long duration, @NotNull final TimeUnit timeUnit ) {
        final var luckPerms = LuckPermsProvider.get();

        final var userManager = luckPerms.getUserManager();
        final var user = userManager.getUser( targetPlayer.getUniqueId() );
        if ( user == null )
            throw new UnsupportedOperationException( "User cannot be null" );

        final var group = luckPerms.getGroupManager().getGroup( groupName );
        if ( group == null )
            throw new UnsupportedOperationException( "Group cannot be null" );

        final var userGroups = user
                .getNodes( NodeType.INHERITANCE )
                .stream()
                .map( InheritanceNode::getGroupName )
                .collect( Collectors.toSet() );

        if ( userGroups.contains( group.getName() ) ) {
            if ( player != null )
                player.sendMessage( "Der Spieler hat bereits den " + groupName + " Rang!" );

            GrieferGamesLogger.log( Level.INFO, "Player already has the rank", null );
            return;
        }

        final var inheritanceNode = InheritanceNode
                .builder( group )
                .expiry( duration, timeUnit )
                .value( true )
                .build();

        final var dataMutateResult = user.data().add( inheritanceNode );
        if ( !dataMutateResult.wasSuccessful() ) {
            GrieferGamesLogger.log( Level.WARNING, "An internal error occurred within LuckPerms", null );
            return;
        }

        userManager.saveUser( user );

        GrieferGamesLogger.log( Level.INFO, "Successfully modified %s's rank to %s.", new Object[] { player.getName(), groupName } );
    }
}
