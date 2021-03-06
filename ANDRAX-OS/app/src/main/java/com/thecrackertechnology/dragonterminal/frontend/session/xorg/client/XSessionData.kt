package com.thecrackertechnology.dragonterminal.frontend.session.xorg.client

import android.view.View
import android.widget.FrameLayout
import com.thecrackertechnology.dragonterminal.NeoAudioThread
import com.thecrackertechnology.dragonterminal.NeoGLView
import com.thecrackertechnology.dragonterminal.xorg.NeoXorgViewClient
import java.util.*

/**
 * @author kiva
 */
class XSessionData {
    var videoLayout: FrameLayout? = null
    var audioThread: NeoAudioThread? = null
    var screenKeyboard: View? = null
    var glView: NeoGLView? = null

    var isPaused = false
    var client: NeoXorgViewClient? = null

    var keyboardWithoutTextInputShown = false
    var screenKeyboardHintMessage: String? = null
    var textInput = LinkedList<Int>()
}