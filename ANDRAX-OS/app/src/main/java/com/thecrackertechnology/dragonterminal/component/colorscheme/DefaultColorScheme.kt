package com.thecrackertechnology.dragonterminal.component.colorscheme

/**
 * @author kiva
 */
object DefaultColorScheme : NeoColorScheme() {
    init {
        /* NOTE: Keep in sync with assets/colors/Default.nl */
        colorName = "Default"

        foregroundColor = "#00ff00"
        backgroundColor = "#000000"
        cursorColor = "#00A000"
    }
}