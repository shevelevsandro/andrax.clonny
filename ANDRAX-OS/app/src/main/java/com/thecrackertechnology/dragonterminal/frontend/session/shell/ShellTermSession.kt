package com.thecrackertechnology.dragonterminal.frontend.session.shell

import android.content.Context
import android.os.Handler
import com.thecrackertechnology.andrax.AndraxApp
import com.thecrackertechnology.andrax.R
import com.thecrackertechnology.dragonterminal.backend.TerminalSession
import com.thecrackertechnology.dragonterminal.frontend.config.NeoTermPath
import com.thecrackertechnology.dragonterminal.frontend.session.shell.client.TermSessionCallback
import java.io.File

/**
 * @author kiva
 */
open class ShellTermSession private constructor(shellPath: String, cwd: String,
                                                args: Array<String>, env: Array<String>,
                                                changeCallback: SessionChangedCallback,
                                                private val initialCommand: String?,
                                                val shellProfile: ShellProfile)
    : TerminalSession(shellPath, cwd, args, env, changeCallback) {

    var exitPrompt = AndraxApp.get().getString(R.string.process_exit_prompt)

    override fun initializeEmulator(columns: Int, rows: Int) {
        super.initializeEmulator(columns, rows)
        sendInitialCommand(shellProfile.initialCommand)
        sendInitialCommand(initialCommand)
    }

    override fun getExitDescription(exitCode: Int): String {
        val builder = StringBuilder("\r\n[ ")
        val context = AndraxApp.get()
        builder.append(context.getString(R.string.process_exit_info))
        if (exitCode > 0) {
            // Non-zero process exit.
            builder.append(" (")
            builder.append(context.getString(R.string.process_exit_code, exitCode))
            builder.append(")")
        } else if (exitCode < 0) {
            // Negated signal.
            builder.append(" (")
            builder.append(context.getString(R.string.process_exit_signal, -exitCode))
            builder.append(")")
        }
        builder.append(" - $exitPrompt ]")
        return builder.toString()
    }

    private fun sendInitialCommand(command: String?) {
        if (command?.isNotEmpty() == true) {

            Handler().postDelayed({

                write("clear\r$command\r")

            }, 700)
        }
    }

    class Builder {
        private var executablePath: String? = null
        private var cwd: String? = null
        private var args: MutableList<String>? = null
        private var env: MutableList<Pair<String, String>>? = null
        private var changeCallback: SessionChangedCallback? = null
        private var systemShell = false
        private var initialCommand: String? = null
        private var shellProfile = ShellProfile()

        fun profile(shellProfile: ShellProfile?): Builder {
            if (shellProfile != null) {
                this.shellProfile = shellProfile
            }
            return this
        }

        fun initialCommand(command: String?): Builder {
            this.initialCommand = command
            return this
        }

        fun executablePath(shell: String?): Builder {
            this.executablePath = shell
            return this
        }

        fun currentWorkingDirectory(cwd: String?): Builder {
            this.cwd = cwd
            return this
        }

        fun arg(arg: String?): Builder {
            if (arg != null) {
                if (args == null) {
                    args = mutableListOf(arg)
                } else {
                    args!!.add(arg)
                }
            } else {
                this.args = null
            }
            return this
        }

        fun argArray(args: Array<String>?): Builder {
            if (args != null) {
                if (args.isEmpty()) {
                    this.args = null
                    return this
                }
                args.forEach { arg(it) }
            } else {
                this.args = null
            }
            return this
        }

        fun env(env: Pair<String, String>?): Builder {
            if (env != null) {
                if (this.env == null) {
                    this.env = mutableListOf(env)
                } else {
                    this.env!!.add(env)
                }
            } else {
                this.env = null
            }
            return this
        }

        fun envArray(env: Array<Pair<String, String>>?): Builder {
            if (env != null) {
                if (env.isEmpty()) {
                    this.env = null
                    return this
                }
                env.forEach { env(it) }
            } else {
                this.env = null
            }
            return this
        }

        fun callback(callback: SessionChangedCallback?): Builder {
            this.changeCallback = callback
            return this
        }

        fun systemShell(systemShell: Boolean): Builder {
            this.systemShell = systemShell
            return this
        }

        fun create(context: Context): ShellTermSession {
            val cwd = this.cwd ?: "/data/data/com.thecrackertechnology.andrax/ANDRAX"

            val shell = this.executablePath ?:
                    if (systemShell)
                        "/system/xbin/andraxzsh"
                    else
                        shellProfile.loginShell

            val args = this.args ?: mutableListOf(shell)
            val env = transformEnvironment(this.env) ?: buildEnvironment(cwd, systemShell)
            val callback = changeCallback ?: TermSessionCallback()
            return ShellTermSession(shell, cwd, args.toTypedArray(), env, callback,
                    initialCommand ?: "", shellProfile)
        }

        private fun transformEnvironment(env: MutableList<Pair<String, String>>?): Array<String>? {
            if (env == null) {
                return null
            }

            val result = mutableListOf<String>()
            return env.mapTo(result, { "${it.first}=${it.second}" })
                    .toTypedArray()
        }


        private fun buildEnvironment(cwd: String?, systemShell: Boolean): Array<String> {
            val selectedCwd = cwd ?: "/data/data/com.thecrackertechnology.andrax/ANDRAX"
            File(NeoTermPath.HOME_PATH).mkdirs()

            val termEnv = "TERM=xterm-color"
            val homeEnv = "HOME=/data/data/com.thecrackertechnology.andrax/ANDRAX"
            //val prefixEnv = "PREFIX=" + NeoTermPath.USR_PATH
            val androidRootEnv = "ANDROID_ROOT=" + System.getenv("ANDROID_ROOT")
            val androidDataEnv = "ANDROID_DATA=" + System.getenv("ANDROID_DATA")
            val externalStorageEnv = "EXTERNAL_STORAGE=" + System.getenv("EXTERNAL_STORAGE")

            // PY Trade: Some programs support NeoTerm in a special way.
            val neotermIdEnv = "__NEOTERM=1"
            val originPathEnv = "__NEOTERM_ORIGIN_PATH=" + buildOriginPathEnv()
            val originLdEnv = "__NEOTERM_ORIGIN_LD_LIBRARY_PATH=" + buildOriginLdLibEnv()

            return if (systemShell) {
                val pathEnv = "PATH=" + System.getenv("PATH")
                arrayOf(termEnv, homeEnv, pathEnv, androidRootEnv, androidDataEnv,
                        externalStorageEnv, neotermIdEnv,
                        originLdEnv, originPathEnv)

            } else {

                val ps1Env = "PS1=$ "
                val langEnv = "LANG=en_US.UTF-8"
                val pathEnv = "PATH=" + System.getenv("PATH")
                val ldEnv = "LD_LIBRARY_PATH=" + buildLdLibraryEnv()
                val pwdEnv = "PWD=$selectedCwd"
                val tmpdirEnv = "TMPDIR=/data/data/com.thecrackertechnology.andrax/ANDRAX/tmp"


                // execve(2) wrapper to avoid incorrect shebang
                val ldPreloadEnv = if (shellProfile.enableExecveWrapper) {
                    "LD_PRELOAD=${AndraxApp.get().applicationInfo.nativeLibraryDir}/libnexec.so"
                } else {
                    ""
                }

                arrayOf(termEnv, homeEnv, pathEnv, ps1Env, ldEnv, langEnv, pwdEnv,
                        androidRootEnv, androidDataEnv, externalStorageEnv,
                        tmpdirEnv, neotermIdEnv, originPathEnv, originLdEnv,
                        ldPreloadEnv)
            }
                    .filter { it.isNotEmpty() }
                    .toTypedArray()
        }

        private fun buildOriginPathEnv(): String {
            val path = System.getenv("PATH")
            return path ?: ""
        }

        private fun buildOriginLdLibEnv(): String {
            val path = System.getenv("LD_LIBRARY_PATH")
            return path ?: ""
        }

        private fun buildLdLibraryEnv(): String {
            return "/vendor/lib*/:/system/lib*/"//"${NeoTermPath.USR_PATH}/lib"
        }

        private fun buildPathEnv(): String {
            return "/system/xbin:/system/bin"//"${NeoTermPath.USR_PATH}/bin:${NeoTermPath.USR_PATH}/bin/applets"
        }
    }
}