package com.thecrackertechnology.dragonterminal.frontend.session.shell.client

import com.thecrackertechnology.dragonterminal.backend.TerminalSession
import com.thecrackertechnology.dragonterminal.frontend.terminal.TerminalView

/**
 * @author kiva
 */
open class BasicSessionCallback(var terminalView: TerminalView) : TerminalSession.SessionChangedCallback {
    override fun onTextChanged(changedSession: TerminalSession?) {
        if (changedSession != null) {
            terminalView.onScreenUpdated()
        }
    }

    override fun onTitleChanged(changedSession: TerminalSession?) {
    }

    override fun onSessionFinished(finishedSession: TerminalSession?) {
    }

    override fun onClipboardText(session: TerminalSession?, text: String?) {
    }

    override fun onBell(session: TerminalSession?) {
    }

    override fun onColorsChanged(session: TerminalSession?) {
        if (session != null) {
            terminalView.onScreenUpdated()
        }
    }
}
