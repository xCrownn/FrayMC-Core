package xyz.xcrownn.fraymccore;

import de.leonhard.storage.Yaml;

public class ConfigBuilder {

    public static void buildMessageFile() {
        Yaml messages = new Yaml("Messages", "plugins/FrayMC-Core");
        messages.getOrSetDefault("Coins.CHECK_COIN_BALANCE", "You have %FrayMC-Core_coins% coins!");
        messages.getOrSetDefault("Coins.INVALID_PLAYER_ARGUMENT", "&c&lThat player does not exist!");
        messages.getOrSetDefault("Coins.INVALID_NUMBER_ARGUMENT", "&c&lYou did not enter a valid number!");
        messages.getOrSetDefault("Coins.ADDED_COINS_MESSAGE", "&c&lYou did not enter a valid number!");
        messages.getOrSetDefault("Coins.REMOVED_COINS_MESSAGE", "&c&lYou did not enter a valid number!");

        // Clan stuff
        messages.getOrSetDefault("Clan.INVALID_PLAYER_INVITE", "&c&lYou did not enter a player who is online!");
        messages.getOrSetDefault("Clan.CLAN_IS_FULL_JOIN", "&c&lThe clan you are trying to join is full!");
        messages.getOrSetDefault("Clan.CLAN_IS_FULL_INVITE", "&c&lYou cannot invite any more players! Your clan is full.");
        messages.getOrSetDefault("Clan.NOT_IN_A_CLAN", "&c&lYou are not in a clan!");
        messages.getOrSetDefault("Clan.INVITE_RECEIVED", "&aYou have received an invite from %FrayMC-Core_player_get_inviter% to join %FrayMC-Core_player_get_clan_from_invite%!");
        messages.getOrSetDefault("Clan.INVITE_SENT", "&aYou have successfully invited that player to your clan!");
        messages.getOrSetDefault("Clan.PLAYER_IS_ALREADY_IN_CLAN", "&cThat player is already in a clan!");
        messages.getOrSetDefault("Clan.ACCEPT_INVITE", "You have joined %FrayMC-Core_player_get_clan_from_invite%!");
        messages.getOrSetDefault("Clan.LEAVE", "You have left your clan!");
        messages.getOrSetDefault("Clan.NOT_CLAN_OWNER", "You are not the owner of the clan!");
        messages.getOrSetDefault("Clan.CANT_CREATE_CLAN_IN_CLAN", "You cannot create a clan while already in a clan!");
        messages.getOrSetDefault("Clan.CANT_CREATE_CLAN_NOT_ENOUGH_COINS", "You don't have enough coins to make a clan!");


    }

    public static void buildSettingsFile() {
        Yaml config = new Yaml("Settings", "plugins/FrayMC-Core");
        config.getOrSetDefault("Clans.Clan_Creation_Cost", 100);
    }

}
