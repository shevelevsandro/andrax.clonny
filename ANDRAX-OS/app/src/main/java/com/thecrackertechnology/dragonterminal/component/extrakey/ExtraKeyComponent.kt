package com.thecrackertechnology.dragonterminal.component.extrakey

import android.content.Context
import io.neolang.visitor.ConfigVisitor
import com.thecrackertechnology.andrax.AndraxApp
import com.thecrackertechnology.dragonterminal.frontend.component.helper.ConfigFileBasedComponent
import com.thecrackertechnology.dragonterminal.frontend.config.NeoTermPath
import com.thecrackertechnology.dragonterminal.frontend.logging.NLog
import com.thecrackertechnology.dragonterminal.frontend.terminal.extrakey.ExtraKeysView
import com.thecrackertechnology.dragonterminal.utils.AssetsUtils
import java.io.File

/**
 * @author kiva
 */
class ExtraKeyComponent : ConfigFileBasedComponent<NeoExtraKey>(NeoTermPath.EKS_PATH) {
    override val checkComponentFileWhenObtained
            get() = true

    private val extraKeys: MutableMap<String, NeoExtraKey> = mutableMapOf()

    override fun onCheckComponentFiles() {
        val defaultFile = File(NeoTermPath.EKS_DEFAULT_FILE)
        if (!defaultFile.exists()) {
            extractDefaultConfig(AndraxApp.get())
        }
        reloadExtraKeyConfig()
    }

    override fun onCreateComponentObject(configVisitor: ConfigVisitor): NeoExtraKey {
        return NeoExtraKey()
    }

    fun showShortcutKeys(program: String, extraKeysView: ExtraKeysView?) {
        if (extraKeysView == null) {
            return
        }

        val extraKey = extraKeys[program]
        if (extraKey != null) {
            extraKey.applyExtraKeys(extraKeysView)
            return
        }

        extraKeysView.loadDefaultUserKeys()
    }

    private fun registerShortcutKeys(extraKey: NeoExtraKey) =
            extraKey.programNames.forEach {
                extraKeys[it] = extraKey
            }

    private fun extractDefaultConfig(context: Context) {
        try {
            AssetsUtils.extractAssetsDir(context, "eks", baseDir)
        } catch (e: Exception) {
            NLog.e("ExtraKey", "Failed to extract configure: ${e.localizedMessage}")
        }
    }

    private fun reloadExtraKeyConfig() {
        extraKeys.clear()
        File(baseDir)
                .listFiles(NEOLANG_FILTER)
                .filter { it.absolutePath != NeoTermPath.EKS_DEFAULT_FILE }
                .mapNotNull { this.loadConfigure(it) }
                .forEach {
                    registerShortcutKeys(it)
                }
    }
}