package com.thecrackertechnology.dragonterminal.component.config

import com.thecrackertechnology.dragonterminal.component.config.loaders.NeoLangConfigureLoader
import com.thecrackertechnology.dragonterminal.component.config.loaders.OldConfigureLoader
import com.thecrackertechnology.dragonterminal.frontend.component.NeoComponent
import java.io.File

/**
 * @author kiva
 */
class ConfigureComponent : NeoComponent {
    val CONFIG_LOADER_VERSION = 20

    override fun onServiceInit() {
    }

    override fun onServiceDestroy() {
    }

    override fun onServiceObtained() {
    }

    fun getLoaderVersion(): Int {
        return CONFIG_LOADER_VERSION
    }

    fun newLoader(configFile: File): IConfigureLoader {
        return when (configFile.extension) {
            "nl" -> NeoLangConfigureLoader(configFile)
            else -> OldConfigureLoader(configFile)
        }
    }
}