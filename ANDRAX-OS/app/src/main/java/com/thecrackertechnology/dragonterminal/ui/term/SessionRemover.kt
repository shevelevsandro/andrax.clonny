package com.thecrackertechnology.dragonterminal.ui.term

import com.thecrackertechnology.dragonterminal.backend.TerminalSession
import com.thecrackertechnology.dragonterminal.frontend.session.xorg.XSession
import com.thecrackertechnology.dragonterminal.services.NeoTermService
import com.thecrackertechnology.dragonterminal.ui.term.tab.TermTab
import com.thecrackertechnology.dragonterminal.ui.term.tab.XSessionTab

/**
 * @author kiva
 */
object SessionRemover {
    fun removeSession(termService: NeoTermService?, tab: TermTab) {
        tab.termData.termSession?.finishIfRunning()
        removeFinishedSession(termService, tab.termData.termSession)
        tab.cleanup()
    }

    fun removeXSession(termService: NeoTermService?, tab: XSessionTab?) {
        removeFinishedSession(termService, tab?.session)
    }

    private fun removeFinishedSession(termService: NeoTermService?, finishedSession: TerminalSession?) {
        if (termService == null || finishedSession == null) {
            return
        }

        termService.removeTermSession(finishedSession)
    }

    private fun removeFinishedSession(termService: NeoTermService?, finishedSession: XSession?) {
        if (termService == null || finishedSession == null) {
            return
        }

        termService.removeXSession(finishedSession)
    }
}
