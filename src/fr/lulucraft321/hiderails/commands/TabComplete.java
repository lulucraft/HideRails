/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.utils.Checker;

public class TabComplete implements TabCompleter
{
	private static final String[] COMMANDS1 = { "help", "reload", "hide", "unhide", "hiderails", "show", "waterprotection", "waterprotect", "return", "undo" }; // /hiderails "COMMANDS1"
	private static final String[] COMMANDS2 = { "hide", "unhide", "hiderail", "show", "waterprotection", "waterprotect" }; // /hiderails "COMMANDS2" "materialType"
	private static final String[] COMMANDS3 = { "waterprotection", "waterprotect" }; // /hiderails "COMMANDS3" "world" "value"

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;

			if(p.isOp())
			{
				List<String> commands1 = Arrays.asList(COMMANDS1);
				List<String> commands2 = Arrays.asList(COMMANDS2);
				List<String> commands3 = Arrays.asList(COMMANDS3);

				List<String> completions = new ArrayList<>();

				if(args.length == 1)
				{
					for(String list : commands1)
					{
						if(list.startsWith(args[0]))
						{
							completions.add(list);
						}
					}
				}

				if(args.length == 2)
				{
					for(String list : commands2)
					{
						if(list.startsWith(args[0]))
						{
							completions.add("");
						}
					}

					for(String list : commands3)
					{
						if(list.startsWith(args[0]))
						{
							for(World world : Bukkit.getServer().getWorlds())
							{
								completions.add(world.getName());
							}
							return completions;
						}
					}
				}

				if(args.length == 3)
				{
					for(World world : Bukkit.getServer().getWorlds())
					{
						for(String list : commands3)
						{
							if(list.startsWith(args[0]))
							{
								if(args[1].startsWith(world.getName()))
								{
									String b = Checker.getBoolean(args[2]);

									if(b != null)
										completions.add(b);
								}
							}
						}
					}
				}

				return completions;
			}
		}

		return null;
	}
}
