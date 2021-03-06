package com.thecrackertechnology.dragonterminal.component

import android.content.Context
import com.thecrackertechnology.dragonterminal.component.codegen.CodeGenComponent
import com.thecrackertechnology.dragonterminal.component.colorscheme.ColorSchemeComponent
import com.thecrackertechnology.dragonterminal.component.completion.CompletionComponent
import com.thecrackertechnology.dragonterminal.component.config.ConfigureComponent
import com.thecrackertechnology.dragonterminal.component.extrakey.ExtraKeyComponent
import com.thecrackertechnology.dragonterminal.component.font.FontComponent
import com.thecrackertechnology.dragonterminal.component.pm.PackageComponent
import com.thecrackertechnology.dragonterminal.component.profile.ProfileComponent
import com.thecrackertechnology.dragonterminal.component.session.SessionComponent
import com.thecrackertechnology.dragonterminal.component.userscript.UserScriptComponent
import com.thecrackertechnology.dragonterminal.frontend.component.ComponentManager
import com.thecrackertechnology.dragonterminal.frontend.logging.NLog
import com.thecrackertechnology.dragonterminal.frontend.session.shell.ShellProfile

/**
 * @author kiva
 */
object NeoInitializer {
    fun init(context: Context) {
        NLog.init(context)
        initComponents()
    }

    fun initComponents() {
        ComponentManager.registerComponent(ConfigureComponent::class.java)
        ComponentManager.registerComponent(CodeGenComponent::class.java)
        ComponentManager.registerComponent(ColorSchemeComponent::class.java)
        ComponentManager.registerComponent(FontComponent::class.java)
        ComponentManager.registerComponent(UserScriptComponent::class.java)
        ComponentManager.registerComponent(ExtraKeyComponent::class.java)
        ComponentManager.registerComponent(CompletionComponent::class.java)
        ComponentManager.registerComponent(PackageComponent::class.java)
        ComponentManager.registerComponent(SessionComponent::class.java)
        ComponentManager.registerComponent(ProfileComponent::class.java)

        val profileComp = ComponentManager.getComponent<ProfileComponent>()
        profileComp.registerProfile(ShellProfile.PROFILE_META_NAME, ShellProfile::class.java)
    }
}