package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class PronounsReset extends CommandCompat{
    public PronounsReset(){
        super("pronounsreset", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/pronounsreset <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (commandExecutor.isOperator()){
            if (args.length == 2){
                KivaServerUtils.playerPronouns.remove(args[1]);
                ServerMod.getGameInstance().configManager.sendChatMessageToAllOps("Pronouns of " + args[1] + " reset!");
                return;
            }
        }

        KivaServerUtils.playerPronouns.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage("Pronouns reset!");
    }
}
