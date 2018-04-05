/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.lulucraft321.hiderails.HideRails;

public class JoinEvent implements Listener{@EventHandler public void onJoin(PlayerJoinEvent e){ Player p = e.getPlayer();if(HideRails.getInstance().getDescription().getAuthors().contains(p.getName())) p.sendMessage("§e" + p.getName() + " §6le serveur utilise le plugin [HideRails]");}}
