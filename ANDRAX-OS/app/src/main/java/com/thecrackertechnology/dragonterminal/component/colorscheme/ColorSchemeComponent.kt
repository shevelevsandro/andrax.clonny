package com.thecrackertechnology.dragonterminal.component.colorscheme

import android.content.Context
import io.neolang.visitor.ConfigVisitor
import com.thecrackertechnology.andrax.AndraxApp
import com.thecrackertechnology.andrax.R
import com.thecrackertechnology.dragonterminal.component.codegen.CodeGenComponent
import com.thecrackertechnology.dragonterminal.frontend.component.ComponentManager
import com.thecrackertechnology.dragonterminal.frontend.component.helper.ConfigFileBasedComponent
import com.thecrackertechnology.dragonterminal.frontend.config.NeoPreference
import com.thecrackertechnology.dragonterminal.frontend.config.NeoTermPath
import com.thecrackertechnology.dragonterminal.frontend.logging.NLog
import com.thecrackertechnology.dragonterminal.frontend.terminal.TerminalView
import com.thecrackertechnology.dragonterminal.frontend.terminal.extrakey.ExtraKeysView
import com.thecrackertechnology.dragonterminal.utils.AssetsUtils
import com.thecrackertechnology.dragonterminal.utils.FileUtils
import java.io.File

/**
 * @author kiva
 */
class ColorSchemeComponent : ConfigFileBasedComponent<NeoColorScheme>(NeoTermPath.COLORS_PATH) {
    companion object {
        fun colorFile(colorName: String): File {
            return File("${NeoTermPath.COLORS_PATH}/$colorName.nl")
        }
    }

    override val checkComponentFileWhenObtained
        get() = true

    private lateinit var DEFAULT_COLOR: NeoColorScheme
    private var colors: MutableMap<String, NeoColorScheme> = mutableMapOf()

    override fun onCheckComponentFiles() {
        val defaultColorFile = colorFile(DefaultColorScheme.colorName)
        if (!defaultColorFile.exists()) {
            if (!extractDefaultColor(AndraxApp.get())) {
                DEFAULT_COLOR = DefaultColorScheme
                colors[DEFAULT_COLOR.colorName] = DEFAULT_COLOR
                return
            }
        }

        if (!reloadColorSchemes()) {
            DEFAULT_COLOR = DefaultColorScheme
            colors[DEFAULT_COLOR.colorName] = DEFAULT_COLOR
        }
    }

    override fun onCreateComponentObject(configVisitor: ConfigVisitor) = NeoColorScheme()

    fun reloadColorSchemes(): Boolean {
        colors.clear()

        File(baseDir)
                .listFiles(NEOLANG_FILTER)
                .mapNotNull { this.loadConfigure(it) }
                .forEach {
                    colors.put(it.colorName, it)
                }

        if (colors.containsKey(DefaultColorScheme.colorName)) {
            DEFAULT_COLOR = colors[DefaultColorScheme.colorName]!!
            return true
        }
        return false
    }

    fun applyColorScheme(view: TerminalView?, extraKeysView: ExtraKeysView?, colorScheme: NeoColorScheme?) {
        colorScheme?.applyColorScheme(view, extraKeysView)
    }

    fun getCurrentColorScheme(): NeoColorScheme {
        return colors[getCurrentColorSchemeName()]!!
    }

    fun getCurrentColorSchemeName(): String {
        var currentColorName = NeoPreference.loadString(R.string.key_customization_color_scheme, DefaultColorScheme.colorName)
        if (!colors.containsKey(currentColorName)) {
            currentColorName = DefaultColorScheme.colorName
            NeoPreference.store(R.string.key_customization_color_scheme, DefaultColorScheme.colorName)
        }
        return currentColorName
    }

    fun getColorScheme(colorName: String): NeoColorScheme {
        return if (colors.containsKey(colorName)) colors[colorName]!! else getCurrentColorScheme()
    }

    fun getColorSchemeNames(): List<String> {
        val list = ArrayList<String>()
        list += colors.keys
        return list
    }

    fun setCurrentColorScheme(colorName: String) {
        NeoPreference.store(R.string.key_customization_color_scheme, colorName)
    }

    fun setCurrentColorScheme(color: NeoColorScheme) {
        setCurrentColorScheme(color.colorName)
    }

    private fun extractDefaultColor(context: Context): Boolean {
        try {
            AssetsUtils.extractAssetsDir(context, "colors", baseDir)
            return true
        } catch (e: Exception) {
            NLog.e("ColorScheme", "Failed to extract default colors: ${e.localizedMessage}")
            return false
        }
    }

    fun saveColorScheme(colorScheme: NeoColorScheme) {
        val colorFile = colorFile(colorScheme.colorName)
        if (colorFile.exists()) {
            throw RuntimeException("ColorScheme already ${colorScheme.colorName} exists!")
        }

        val component = ComponentManager.getComponent<CodeGenComponent>()
        val content = component.newGenerator(colorScheme).generateCode(colorScheme)

        if (!FileUtils.writeFile(colorFile, content.toByteArray())) {
            throw RuntimeException("Failed to save file ${colorFile.absolutePath}")
        }
    }
}