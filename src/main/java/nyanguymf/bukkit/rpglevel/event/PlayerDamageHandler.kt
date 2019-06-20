/**
 * This file is the part of RPG Level plug-in.
 *
 * Copyright (c) 2019 Vasily
 */

package nyanguymf.bukkit.rpglevel.event

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin

import nyanguymf.bukkit.rpglevel.storage.GroupsConfig
import nyanguymf.bukkit.rpglevel.storage.MessagesConfig
import nyanguymf.bukkit.rpglevel.storage.PlayerGroup
import org.bukkit.Bukkit
import org.bukkit.event.EventPriority

/** @author NyanGuyMF - Vasiliy Bely */
class PlayerDamageHandler(private val groupsConfig: GroupsConfig, private val messages: MessagesConfig) : Listener {

    @EventHandler(priority=EventPriority.LOWEST)
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (!isPlayerDamagedByPlayer(event))
            return

        val damager = event.damager as Player
        var damagerGroup: PlayerGroup? = groupsConfig.getGroup(damager.level)

        if (damagerGroup == null) {
            Bukkit.getConsoleSender().sendMessage(
                messages.getMessage("level-does-not-exist", damager.level.toString())
            )
            return
        }

        val damaged = event.entity as Player
        var damagedGroup: PlayerGroup? = groupsConfig.getGroup(damaged.level)

        if (damagedGroup == null) {
            Bukkit.getConsoleSender().sendMessage(
                messages.getMessage("level-does-not-exist", damaged.level.toString())
            )
            return
        }

        if (damagerGroup != damagedGroup) {
            damager.sendMessage(messages.getMessage("not-your-group"))
            event.isCancelled = true
            return
        }
    }

    fun register(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    private fun isPlayerDamagedByPlayer(event: EntityDamageByEntityEvent): Boolean {
        return event.damager is Player && event.entity is Player
    }
}
