package com.thecrackertechnology.dragonterminal.component.font

import android.content.Context
import android.graphics.Typeface
import com.thecrackertechnology.andrax.AndraxApp
import com.thecrackertechnology.andrax.R
import com.thecrackertechnology.dragonterminal.frontend.component.NeoComponent
import com.thecrackertechnology.dragonterminal.frontend.config.DefaultValues
import com.thecrackertechnology.dragonterminal.frontend.config.NeoPreference
import com.thecrackertechnology.dragonterminal.frontend.config.NeoTermPath
import com.thecrackertechnology.dragonterminal.frontend.terminal.TerminalView
import com.thecrackertechnology.dragonterminal.frontend.terminal.extrakey.ExtraKeysView
import com.thecrackertechnology.dragonterminal.utils.AssetsUtils
import java.io.File

/**
 * @author kiva
 */
class FontComponent : NeoComponent {
    private lateinit var DEFAULT_FONT: NeoFont
    private lateinit var fonts: MutableMap<String, NeoFont>

    fun applyFont(terminalView: TerminalView?, extraKeysView: ExtraKeysView?, font: NeoFont?) {
        font?.applyFont(terminalView, extraKeysView)
    }

    fun getCurrentFont(): NeoFont {
        return fonts[getCurrentFontName()]!!
    }

    fun setCurrentFont(fontName: String) {
        NeoPreference.store(R.string.key_customization_font, fontName)
    }

    fun getCurrentFontName(): String {
        val defaultFont = DefaultValues.defaultFont
        var currentFontName = NeoPreference.loadString(R.string.key_customization_font, defaultFont)
        if (!fonts.containsKey(currentFontName)) {
            currentFontName = defaultFont
            NeoPreference.store(R.string.key_customization_font, defaultFont)
        }
        return currentFontName
    }

    fun getFont(fontName: String): NeoFont {
        return if (fonts.containsKey(fontName)) fonts[fontName]!! else getCurrentFont()
    }

    fun getFontNames(): List<String> {
        val list = ArrayList<String>()
        list += fonts.keys
        return list
    }

    fun reloadFonts(): Boolean {
        fonts.clear()
        fonts.put("Monospace", NeoFont(Typeface.MONOSPACE))
        fonts.put("Sans Serif", NeoFont(Typeface.SANS_SERIF))
        fonts.put("Serif", NeoFont(Typeface.SERIF))
        val fontDir = File(NeoTermPath.FONT_PATH)
        for (file in fontDir.listFiles({ pathname -> pathname.name.endsWith(".ttf") })) {
            val fontName = fontName(file)
            val font = NeoFont(file)
            fonts.put(fontName, font)
        }

        val defaultFont = DefaultValues.defaultFont
        if (fonts.containsKey(defaultFont)) {
            DEFAULT_FONT = fonts[defaultFont]!!
            return true
        }
        return false
    }

    override fun onServiceInit() {
        checkForFiles()
    }

    override fun onServiceDestroy() {
    }

    override fun onServiceObtained() {
        checkForFiles()
    }

    private fun loadDefaultFontFromAsset(context: Context): NeoFont {
        val defaultFont = DefaultValues.defaultFont
        return NeoFont(Typeface.createFromAsset(context.assets, "fonts/$defaultFont.ttf"))
    }

    private fun extractDefaultFont(context: Context): Boolean {
        try {
            AssetsUtils.extractAssetsDir(context, "fonts", NeoTermPath.FONT_PATH)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun fontFile(fontName: String): File {
        return File("${NeoTermPath.FONT_PATH}/$fontName.ttf")
    }

    private fun fontName(fontFile: File): String {
        return fontFile.nameWithoutExtension
    }

    private fun checkForFiles() {
        File(NeoTermPath.FONT_PATH).mkdirs()
        fonts = mutableMapOf()

        val context = AndraxApp.get()
        val defaultFont = DefaultValues.defaultFont
        val defaultFontFile = fontFile(defaultFont)

        if (!defaultFontFile.exists()) {
            if (!extractDefaultFont(context)) {
                DEFAULT_FONT = loadDefaultFontFromAsset(context)
                fonts.put(defaultFont, DEFAULT_FONT)
                return
            }
        }

        if (!reloadFonts()) {
            DEFAULT_FONT = loadDefaultFontFromAsset(context)
            fonts.put(defaultFont, DEFAULT_FONT)
        }
    }
}