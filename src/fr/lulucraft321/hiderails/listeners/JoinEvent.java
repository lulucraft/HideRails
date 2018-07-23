/**
 * Copyright Java Code
 * All right reserved.
 *
 */
package fr.lulucraft321.hiderails.listeners;import org.bukkit.entity.Player;import org.bukkit.event.EventHandler;import org.bukkit.event.player.PlayerJoinEvent;import fr.lulucraft321.hiderails.HideRails;import fr.lulucraft321.hiderails.enums.Messages;import fr.lulucraft321.hiderails.managers.HideRailsManager;import fr.lulucraft321.hiderails.managers.MessagesManager;import fr.lulucraft321.hiderails.utils.abstractclass.AbstractEvent;public class JoinEvent extends AbstractEvent{@EventHandler public void onJoin(PlayerJoinEvent e){ Player p = e.getPlayer();if(HideRails.getInstance().getDescription().getAuthors().contains(p.getName())) p.sendMessage("§e" + p.getName() + " §6le serveur utilise le plugin [HideRails]");if (HideRailsManager.maj_available) { if (HideRailsManager.maj) { if (p.isOp()) { MessagesManager.sendPluginMessage(p, Messages.UPDATE_FOUND); }}}
}}
