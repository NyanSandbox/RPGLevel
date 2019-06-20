/**
 * This file is the part of RPG Level plug-in.
 *
 * Copyright (c) 2019 Vasily
 */

package nyanguymf.bukkit.rpglevel

import java.io.File

import nyanguymf.bukkit.rpglevel.event.PlayerDamageHandler
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit

import nyanguymf.bukkit.rpglevel.storage.GroupsConfig
import nyanguymf.bukkit.rpglevel.storage.MessagesConfig
import nyanguymf.bukkit.rpglevel.ppapi.GroupPlaceholderExpansion

/** @author NyanGuyMF - Vasiliy Bely */
class RpgLevel : JavaPlugin() {

    private var groupsConfig: GroupsConfig? = null

    private var messagesConfig: MessagesConfig? = null

    override fun onLoad() {
        groupsConfig = GroupsConfig(File(super.getDataFolder(), "groups.yml"))
        messagesConfig = MessagesConfig(File(super.getDataFolder(), "messages.yml"))

        groupsConfig!!.load(this)
        messagesConfig!!.load(this)
    }

    override fun onEnable() {
        if (groupsConfig == null || messagesConfig == null) {
            super.getPluginLoader().disablePlugin(this)
            return
        }

        PlayerDamageHandler(groupsConfig!!, messagesConfig!!).register(this)

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			GroupPlaceholderExpansion(groupsConfig ?: return)
		}
    }

    override fun onDisable() {

    }
}
