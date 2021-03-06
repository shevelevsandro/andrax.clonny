package com.thecrackertechnology.dragonterminal.frontend.completion.listener

import com.thecrackertechnology.dragonterminal.frontend.completion.model.CompletionCandidate

/**
 * @author kiva
 */
interface OnCandidateSelectedListener {
    fun onCandidateSelected(candidate: CompletionCandidate)
}