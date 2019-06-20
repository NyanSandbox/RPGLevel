/**
 * This file is the part of RPG Level plug-in.
 *
 * Copyright (c) 2019 Vasily
 */

package nyanguymf.bukkit.rpglevel.storage

import lombok.Getter

/** @author NyanGuyMF - Vasiliy Bely */
data class PlayerGroup(@field:Getter var prefix: String?, @field:Getter val minLevel: Int, @field:Getter val maxLevel: Int) {
    init {
        prefix = prefix ?: ""
    }
}
