/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.lulucraft321.hiderails.commands.execution.DisplayCommand;
import fr.lulucraft321.hiderails.commands.execution.DisplayOtherCommand;
import fr.lulucraft321.hiderails.commands.execution.HelpCommand;
import fr.lulucraft321.hiderails.commands.execution.HideCommand;
import fr.lulucraft321.hiderails.commands.execution.HideSelectionBlockTypeCommand;
import fr.lulucraft321.hiderails.commands.execution.HideSelectionCommand;
import fr.lulucraft321.hiderails.commands.execution.HideSingleCommand;
import fr.lulucraft321.hiderails.commands.execution.ReloadCommand;
import fr.lulucraft321.hiderails.commands.execution.RestoreBackupCommand;
import fr.lulucraft321.hiderails.commands.execution.SelectionMessageCommand;
import fr.lulucraft321.hiderails.commands.execution.UnHideCommand;
import fr.lulucraft321.hiderails.commands.execution.UnHideSelectionBlockTypeCommand;
import fr.lulucraft321.hiderails.commands.execution.UnHideSelectionCommand;
import fr.lulucraft321.hiderails.commands.execution.UnHideSingleCommand;
import fr.lulucraft321.hiderails.commands.execution.WaterProtectionCommand;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class CommandsHandle implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		AbstractCommand a = new HelpCommand(sender);

		if (args.length == 1) {

			switch (args[0].toLowerCase())
			{
				case "help":
					a = new HelpCommand(sender);
					break;

				case "reload":
				case "rel":
				case "rl":
					a = new ReloadCommand(sender);
					break;

				case "display":
					a = new DisplayCommand(sender);
					break;

				case "selectionmessage":
					a = new SelectionMessageCommand(sender);
					break;

				case "return":
				case "undo":
					a = new RestoreBackupCommand(sender);
					break;

				case "unhideselection":
				case "unhideselect":
				case "unhidesel" :
				case "showselection":
				case "showselect":
				case "showsel":
					a = new UnHideSelectionCommand(sender);
					break;

				case "unhide":
				case "show":
					a = new UnHideCommand(sender);
					break;

				case "singleunhide":
				case "singleshow":
				case "solounhide":
				case "soloshow":
				case "oneunhide":
				case "oneshow":
				case "showone":
				case "showsingle":
				case "showsolo":
				case "unhideone":
				case "unhidesingle":
				case "unhidesolo":
					a = new UnHideSingleCommand(sender);
					break;


				default:
					a = new HelpCommand(sender);
					break;
			}

		}

		if (args.length == 2) {

			switch (args[0].toLowerCase())
			{
				case "display":
					a = new DisplayOtherCommand(sender);
					break;

				case "hideselection":
				case "hideselect":
				case "hidesel":
					a = new HideSelectionCommand(sender);
					break;

				case "unhideselection":
				case "unhideselect":
				case "unhidesel" :
				case "showselection":
				case "showselect":
				case "showsel":
					a = new UnHideSelectionBlockTypeCommand(sender);
					break;

				case "hide":
				case "hiderails":
					a = new HideCommand(sender);
					break;

				case "singlehide":
				case "solohide":
				case "onehide":
				case "hideone":
				case "hidesingle":
				case "hidesolo":
					a = new HideSingleCommand(sender);
					break;


				default:
					a = new HelpCommand(sender);
					break;
			}

		}

		if (args.length == 3) {

			switch (args[0].toLowerCase())
			{
				case "hideselection":
				case "hideselect":
				case "hidesel":
					a = new HideSelectionBlockTypeCommand(sender);
					break;

				case "waterprotection":
				case "waterprotect":
					a = new WaterProtectionCommand(sender);
					break;

				default:
					a = new HelpCommand(sender);
					break;
			}
		}

		// Send help page
		a.execute(sender, cmd, args);
		return true;
	}
}
