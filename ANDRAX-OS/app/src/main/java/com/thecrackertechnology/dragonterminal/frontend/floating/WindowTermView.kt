package com.thecrackertechnology.dragonterminal.frontend.floating

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.thecrackertechnology.andrax.R
import com.thecrackertechnology.dragonterminal.backend.TerminalSession
import com.thecrackertechnology.dragonterminal.frontend.terminal.TerminalView
import com.thecrackertechnology.dragonterminal.frontend.terminal.TerminalViewClient
import com.thecrackertechnology.dragonterminal.utils.TerminalUtils

/**
 * @author kiva
 */
class WindowTermView(val context: Context) {
    @SuppressLint("InflateParams")
    var rootView: View = LayoutInflater.from(context).inflate(R.layout.ui_term_dialog, null, false)
        private set
    var terminalView: TerminalView = rootView.findViewById<TerminalView>(R.id.terminal_view_dialog)
        private set

    init {
        TerminalUtils.setupTerminalView(terminalView)
    }

    fun setTerminalViewClient(terminalViewClient: TerminalViewClient?) {
        terminalView.setTerminalViewClient(terminalViewClient)
    }

    fun attachSession(terminalSession: TerminalSession?) {
        terminalView.attachSession(terminalSession)
    }

    fun setInputMethodEnabled(enabled: Boolean) {
        terminalView.isFocusable = enabled
        terminalView.isFocusableInTouchMode = enabled
    }
}