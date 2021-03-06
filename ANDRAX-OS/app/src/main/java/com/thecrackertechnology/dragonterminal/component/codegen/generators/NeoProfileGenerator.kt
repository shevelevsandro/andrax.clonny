package com.thecrackertechnology.dragonterminal.component.codegen.generators

import com.thecrackertechnology.dragonterminal.component.codegen.CodeGenParameter
import com.thecrackertechnology.dragonterminal.component.codegen.interfaces.CodeGenObject
import com.thecrackertechnology.dragonterminal.component.codegen.interfaces.CodeGenerator

/**
 * @author kiva
 */
class NeoProfileGenerator(parameter: CodeGenParameter) : CodeGenerator(parameter) {
    override fun getGeneratorName(): String {
        return "NeoProfile-Generator"
    }

    override fun generateCode(codeGenObject: CodeGenObject): String {
        return ""
    }
}