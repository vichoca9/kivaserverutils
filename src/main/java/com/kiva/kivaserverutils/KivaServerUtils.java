package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.network.ChatColors;

import java.util.*;

public class KivaServerUtils extends Mod {
    public static HashMap<String, String> playerNicknames = new HashMap<>();
    public static HashMap<String, String> playerPronouns = new HashMap<>();
    public static HashMap<String, String> playerNameColors = new HashMap<>();
    public static HashMap<String, Coordinate> playerHomes = new HashMap<>();
    public static Set<String> playersInRestrictiveMode;
    public static Set<String> playersExcludedFromRestrictiveMode;
    public static Set<String> playersMuted;

    public static HashMap<String, ArrayList<String>> tpaRequests = new HashMap<>(); // String to, List<String> from
    public static HashMap<String, ProtectedRegion> protectedRegions = new HashMap<>();

    public static HashMap<String, Boolean> config = new HashMap<>();
    public static Coordinate spawnCommandLocation = null;

    public static String defaultPlayerNameColor = ChatColors.AQUA;
    public static HashMap<String, String> nameColorChoicesNames = new HashMap<>();
    public static String version = "1.5.1";
    public static String KSUBroadcastPrefix = ChatColors.DARK_GRAY + "[" + ChatColors.GRAY + "KSU" + ChatColors.DARK_GRAY + "] " + ChatColors.RESET;

    public static String handleWindowClickLatestPlayerUsername;

    public static String getPlayerNameColor(final String playerName){
        if (playerNameColors == null)
            return defaultPlayerNameColor;

        String color = playerNameColors.get(playerName);
        return color == null ? defaultPlayerNameColor : color;
    }

    public static Boolean getConfigValue(String key){
        Boolean value = config.get(key);
        return value == null ? false : value;
    }

    public static String notifyPlayerIsInRestrictiveMode = ChatColors.RED + "You are currently in restrictive mode";
    public static String notifyProtectedRegion = ChatColors.RED + "Protected region: ";

    public static Boolean isPlayerInRestrictiveMode(final String username){
        if (!KivaServerUtils.getConfigValue("restrictbydefault"))
            return KivaServerUtils.playersInRestrictiveMode.contains(username);
        else
            return !KivaServerUtils.playersExcludedFromRestrictiveMode.contains(username);
    }

    public static Boolean togglePlayerRestrictiveMode(final String username){
        if (isPlayerInRestrictiveMode(username))
            KivaServerUtils.playersInRestrictiveMode.remove(username);
        else
            KivaServerUtils.playersInRestrictiveMode.add(username);

        return isPlayerInRestrictiveMode(username);
    }

    public static Boolean isPlayerExcludedFromRestrictiveMode(final String username){
        return KivaServerUtils.playersExcludedFromRestrictiveMode.contains(username);
    }

    public static Boolean toggleExcludePlayerFromRestrictiveMode(final String username){
        if (isPlayerExcludedFromRestrictiveMode(username))
            KivaServerUtils.playersExcludedFromRestrictiveMode.remove(username);
        else
            KivaServerUtils.playersExcludedFromRestrictiveMode.add(username);

        return isPlayerExcludedFromRestrictiveMode(username);
    }

    public static Boolean isPlayerMuted(final String username){
        return KivaServerUtils.playersMuted.contains(username);
    }

    public static Boolean togglePlayerMuted(final String username){
        if (isPlayerMuted(username))
            KivaServerUtils.playersMuted.remove(username);
        else
            KivaServerUtils.playersMuted.add(username);

        return isPlayerMuted(username);
    }

    public static Pair<String, Boolean> inProtectedRegion(final int x, final int y, final int z, final int dimension){
        if (KivaServerUtils.protectedRegions.isEmpty())
            return new Pair<String, Boolean>("", false);

        // TODO Calculate distance to region (taking into account (cached?) "radiuses" (max to prevent false-positives)) to ignore some regions, saving on performance
        // TODO Could separate out into 2 hashmaps based on dimension to speed it up even more
        for (Map.Entry<String, ProtectedRegion> regionEntry : KivaServerUtils.protectedRegions.entrySet()){
            ProtectedRegion region = regionEntry.getValue();

            // worldObj.dimension in MixinExplosion.java is 1 for some reason in the nether
            if (dimension == 0){ // Overworld
                if (region.dimension != 0)
                    continue;
            } else { // Nether
                if (region.dimension == 0)
                    continue;
            }

            if ((x >= region.xMin) && (x <= region.xMax)
                    && (y >= region.yMin) && (y <= region.yMax)
                    && (z >= region.zMin) && (z <= region.zMax))
                return new Pair<String, Boolean>(regionEntry.getKey() + ChatColors.GRAY + " (" + Coordinate.dimensionToString(region.dimension) + ")", true);
        }

        return new Pair<String, Boolean>("", false);
    }

    @Override
    public void onPreInit(){
        System.out.println("KivaServerUtils initializing...");
    }
}
