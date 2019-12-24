package com.thecrackertechnology.dragonterminal.component.config

import com.thecrackertechnology.dragonterminal.frontend.config.NeoConfigureFile

/**
 * @author kiva
 */
interface IConfigureLoader {
    fun loadConfigure() : NeoConfigureFile?
}
