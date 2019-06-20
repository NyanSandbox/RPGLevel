/**
 * This file is the part of RPG Level plug-in.
 *
 * Copyright (c) 2019 Vasily
 */

package nyanguymf.bukkit.rpglevel.ppapi

import org.bukkit.OfflinePlayer

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import nyanguymf.bukkit.rpglevel.storage.GroupsConfig
import nyanguymf.bukkit.rpglevel.storage.PlayerGroup

/** @author NyanGuyMF - Vasiliy Bely */
class GroupPlaceholderExpansion(private var groupsConfig: GroupsConfig) : PlaceholderExpansion() {

    init {
        super.register()
    }

    override fun onRequest(player: OfflinePlayer, identifier: String): String {
        val group = groupsConfig.getGroup(
                if (player.player == null) 0 else player.player.level
        )

        return group!!.prefix!!.replace('&', '\u00a7')
    }

    override fun canRegister(): Boolean {
        return true
    }

    override fun getAuthor(): String {
        return "NyanGuyMF"
    }

    override fun getIdentifier(): String {
        return "rpglevel"
    }

    override fun getVersion(): String {
        return "1.0.0-RELEASE"
    }
}
