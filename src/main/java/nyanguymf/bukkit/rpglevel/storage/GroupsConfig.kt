/**
 * This file is the part of RPG Level plug-in.
 *
 * Copyright (c) 2019 Vasily
 */

package nyanguymf.bukkit.rpglevel.storage

import org.bukkit.configuration.file.YamlConfiguration.loadConfiguration

import java.io.File
import java.util.HashMap

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

/** @author NyanGuyMF - Vasiliy Bely */
class GroupsConfig(private val configurationFile: File) {

    private val groups: MutableMap<String, PlayerGroup>

    init {
        groups = HashMap()
    }

    fun load(plugin: JavaPlugin) {
        if (!configurationFile.exists()) {
            plugin.saveResource("groups.yml", true)
        }

        val config = loadConfiguration(configurationFile)

        for (groupName in config.getKeys(false)) {
            val prefix = config.getString("$groupName.prefix")
            val minLevel = config.getInt("$groupName.min-level")
            val maxLevel = config.getInt("$groupName.max-level")

            groups[prefix] = PlayerGroup(prefix, minLevel, maxLevel)
        }
    }

    fun getGroup(level: Int): PlayerGroup? {
        for (group in groups.values)
            if ((level <= group.maxLevel) && (level >= group.minLevel))
                return group

        return null
    }
}
