/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.enums.BlockReplacementType;

public class TabComplete implements TabCompleter
{
	private static final String[] COMMANDS1 = { "help", "reload", "hide", "unhide", "show", "hideone", "unhideone", "hideselection", "unhideselection", "display", "selectionmessage", "return", "undo", "waterprotection" }; // /hiderails "COMMANDS1"
	private static final String[] COMMANDS2 = { "hide", "hideselection", "unhideselection", "show", "unhide", "showone", "waterprotection" }; // /hiderails "COMMANDS2" "materialType"
	private static final String[] COMMANDS3 = { "waterprotection" }; // /hiderails "COMMANDS3" "world" "value"
	private static final String[] BOOLEAN = { "true", "false", "enable", "disable" };
	private static final List<String> BLOCK_TYPE = new ArrayList<>();

	static {
		for (BlockReplacementType bt : BlockReplacementType.values()) {
			BLOCK_TYPE.add(bt.name().toLowerCase());
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
	{
		if (sender instanceof Player)
		{
			Player p = (Player) sender;

			if (p.isOp())
			{
				List<String> commands1 = Arrays.asList(COMMANDS1);
				List<String> commands2 = Arrays.asList(COMMANDS2);
				List<String> commands3 = Arrays.asList(COMMANDS3);

				List<String> completions = new ArrayList<>();

				switch (args.length) {
				case 1:// args.length == 1
					for (String list : commands1)
					{
						if (list.startsWith(args[0])) {
							completions.add(list);
						}
					}
					break;

				case 2:// args.length == 2
					for (String list : commands2)
					{
						if (list.startsWith(args[0])) {
							completions.add("");
						}
					}

					for (String list : commands3)
					{
						if (list.startsWith(args[0])) {
							for (World world : Bukkit.getServer().getWorlds()) {
								completions.add(world.getName());
							}
							completions.add("all");
							completions.add("_all_");
							return completions;
						}
					}

					if (args[0].equalsIgnoreCase("hideselection")) {
						for (String list : BLOCK_TYPE) {
							if (list.startsWith(args[1])) {
								completions.add(list);
							}
						}
					}
					else if (args[0].equalsIgnoreCase("unhideselection")) {
						for (String list : BLOCK_TYPE) {
							if (list.startsWith(args[1])) {
								completions.add(list);
							}
						}
					}
					else if (args[0].startsWith("display")) {
						for (Player pls : Bukkit.getOnlinePlayers()) {
							completions.add(pls.getName());
						}
					}
					break;

				case 3:// args.length == 3
					for (World world : Bukkit.getServer().getWorlds())
					{
						for (String list : commands3)
						{
							if (list.startsWith(args[0])) {
								if (
										args[1].startsWith(world.getName())
										|| args[1].startsWith("all")
										|| args[1].startsWith("_all_")
										) {
									for (String b : BOOLEAN) {
										if (b.startsWith(args[2])) {
											completions.add(b);
										}
									}
								}
							}
						}
					}
					break;
				}

				return completions;
			}
		}

		return null;
	}
}
