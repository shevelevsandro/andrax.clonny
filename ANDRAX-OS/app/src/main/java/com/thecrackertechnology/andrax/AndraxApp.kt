package com.thecrackertechnology.andrax

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.widget.Toast

import com.onesignal.OneSignal
import com.thecrackertechnology.dragonterminal.component.NeoInitializer
import com.thecrackertechnology.dragonterminal.frontend.config.NeoPreference
import com.thecrackertechnology.dragonterminal.ui.bonus.BonusActivity

import org.acra.ACRA
import org.acra.annotation.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.support.v4.app.NotificationCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import android.app.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v4.app.NotificationManagerCompat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.app.NotificationManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.WindowManager


@AcraCore(buildConfigClass = BuildConfig::class)
@AcraMailSender(mailTo = "weidsom@thecrackertechnology.com")
@AcraDialog(resText = R.string.acra_dialog_text, resCommentPrompt = R.string.acra_dialog_comment, resTheme = R.style.AppCompatCrashDialog, resIcon = R.drawable.andraxicon, resTitle = R.string.acra_dialod_title)


class AndraxApp : Application() {


    override fun onCreate() {
        super.onCreate()

        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(false).init()

        val createsymlinkmnt = Runtime.getRuntime().exec("su -c ln -s " + this@AndraxApp.applicationInfo.dataDir + " /data/data/com.thecrackertechnology.andrax")
        createsymlinkmnt.waitFor()

        val mkdirandraxpath = Runtime.getRuntime().exec("su -c mkdir /data/data/com.thecrackertechnology.andrax/ANDRAX")
        mkdirandraxpath.waitFor()

        val rmdoublelink = Runtime.getRuntime().exec("su -c rm " + "/data/data/com.thecrackertechnology.andrax/com.thecrackertechnology.andrax")
        rmdoublelink.waitFor()

        val auth_battery_hack = Runtime.getRuntime().exec("su -c dumpsys deviceidle whitelist +com.thecrackertechnology.andrax")

        app = this

        NeoPreference.init(this)
        //CrashHandler.init()
        NeoInitializer.init(this)

        CheckVersion().execute(getString(R.string.andrax_version_link))


    }

        override fun attachBaseContext(base: Context) {

            super.attachBaseContext(base)

            ACRA.init(this)
    }

    fun errorDialog(context: Context, message: Int, dismissCallback: (() -> Unit)?) {
        errorDialog(context, getString(message), dismissCallback)
    }

    fun errorDialog(context: Context, message: String, dismissCallback: (() -> Unit)?) {
        AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(message)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(R.string.show_help, { _, _ ->
                    openHelpLink()
                })
                .setOnDismissListener {
                    dismissCallback?.invoke()
                }
                .show()
    }

    fun openHelpLink() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://neoterm.gitbooks.io/neoterm-wiki/content/"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun easterEgg(context: Context, message: String) {
        val happyCount = NeoPreference.loadInt(NeoPreference.KEY_HAPPY_EGG, 0) + 1
        NeoPreference.store(NeoPreference.KEY_HAPPY_EGG, happyCount)

        val trigger = NeoPreference.VALUE_HAPPY_EGG_TRIGGER

        if (happyCount == trigger / 2) {
            @SuppressLint("ShowToast")
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        } else if (happyCount > trigger) {
            NeoPreference.store(NeoPreference.KEY_HAPPY_EGG, 0)
            context.startActivity(Intent(context, BonusActivity::class.java))
        }
    }

    companion object {
        private var app: AndraxApp? = null

        fun get(): AndraxApp {
            return app!!
        }


    }


    class CheckVersion : AsyncTask<String, String, String>() {

        var VersionFromServer = 0
        var versiondefault = "433"

        override fun doInBackground(vararg fileUrl: String): String? {

            try {
                val url = URL(fileUrl[0])
                val urlConnection = url.openConnection()
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android ANDRAX; Mobile; rv:03) Gecko/67.0 Firefox/67.0")
                urlConnection.connect()

                val inputStream = urlConnection.getInputStream()
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)

                var resulversion = ""

                resulversion = bufferedReader.readLine()

                VersionFromServer = 0

                try {

                    VersionFromServer = Integer.parseInt(resulversion.replace("\\s+".toRegex(), ""))

                } catch (e: NumberFormatException) {

                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onProgressUpdate(vararg progress: String) {


        }

        override fun onPostExecute(result: String?) {

            if (VersionFromServer > Integer.parseInt(versiondefault)) {

                val notificationId = 9988
                val CHANNEL_ID = "NEWVERSION"
                val name = "New Version of ANDRAX"
                val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    NotificationManager.IMPORTANCE_HIGH
                } else {
                    TODO("VERSION.SDK_INT < N")
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel(CHANNEL_ID, name, importance)
                }

                val notificationIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://andrax.thecrackertechnology.com"))
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val pendingIntent: PendingIntent = PendingIntent.getActivity(AndraxApp.get(), 0, notificationIntent, 0)

                var builder = NotificationCompat.Builder(AndraxApp.get(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.andraxicon_svg)
                        .setChannelId(CHANNEL_ID)
                        .setContentTitle("NEW VERSION")
                        .setContentText("ANDRAX has a new version, DOWNLOAD NOW!")
                        .setStyle(NotificationCompat.BigTextStyle()
                                .bigText("ANDRAX has a new version, DOWNLOAD NOW!\nFor new tools, bug fixes and a lot of improvements"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(false)
                        .setVibrate(longArrayOf(1000, 1000, 1000))
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .setColor(0xFF000000.toInt())


                val notificationManager = NotificationManagerCompat.from(AndraxApp.get())

                notificationManager.notify(notificationId, builder.build())


            } else {

                val notificationManager = NotificationManagerCompat.from(AndraxApp.get())
                notificationManager.cancel(9988)

            }

        }
    }

}
