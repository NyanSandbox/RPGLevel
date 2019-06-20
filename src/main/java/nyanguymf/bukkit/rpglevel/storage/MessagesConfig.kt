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
import org.bukkit.plugin.Plugin

/** @author NyanGuyMF - Vasiliy Bely */
class MessagesConfig(private val messagesFile: File) {

    private val messages: MutableMap<String, String>

    init {
        messages = HashMap()
    }

    fun load(plugin: Plugin) {
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", true)
        }

        val messagesConfig = loadConfiguration(messagesFile)

        for (key in messagesConfig.getKeys(false)) {
            messages[key] = messagesConfig.getString(key)
        }
    }

    fun getMessage(key: String, vararg strings: String): String {
        val message = messages[key]

        if (message == null) {
            System.err.printf("Message with key «%s» doesn't exists.\n", key)
            return key
        }

        for (c in strings.indices) {
            message.replace("{$c}", strings[c].toString())
        }

        return message.replace('&', '\u00a7').replace("\\n", "\n")
    }
}
