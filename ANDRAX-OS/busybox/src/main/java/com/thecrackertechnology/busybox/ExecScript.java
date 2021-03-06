package com.thecrackertechnology.busybox;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 19.09.15.
 */
class ExecScript extends Thread {

    private Context context;
    private Context btncontext;
    private String command;

    ExecScript(Context btn, Context c, String command) {
        this.context = c;
        this.command = command;
        this.btncontext = btn;
    }

    private void info() {
        String envDir = PrefStore.getEnvDir(context);
        List<String> params = new ArrayList<>();
        params.add("ENV_DIR=" + envDir);
        params.add("INSTALL_DIR=" + PrefStore.getInstallDir(context));
        params.add(". " + envDir + "/scripts/info.sh");
        EnvUtils.exec(context, "sh", params);
    }

    private void install() {
        // check root
        if (!EnvUtils.isRooted(context)) return;
        String envDir = PrefStore.getEnvDir(context);
        List<String> params = new ArrayList<>();
        params.add("ENV_DIR=" + envDir);
        params.add("INSTALL_DIR=" + PrefStore.getInstallDir(context));
        params.add("INSTALL_APPLETS=" + PrefStore.isInstallApplets(context));
        params.add("REPLACE_APPLETS=" + PrefStore.isReplaceApplets(context));
        params.add(". " + envDir + "/scripts/install.sh");
        EnvUtils.exec(context, "su", params);
    }

    private void remove() {
        // check root
        if (!EnvUtils.isRooted(context)) return;
        String envDir = PrefStore.getEnvDir(context);
        List<String> params = new ArrayList<>();
        params.add("INSTALL_DIR=" + PrefStore.getInstallDir(context));
        params.add(". " + envDir + "/scripts/remove.sh");
        EnvUtils.exec(context, "su", params);
    }

    @Override
    public void run() {
        Logger.clear();
        // update env
        if (!EnvUtils.update(context)) return;
        // exec command
        switch (command) {
            case "info":
                info();
                break;
            case "install":

                Logger.log(context, "### BEGIN FORCE REMOVE\n");
                Logger.log(context, " \n");
                remove();
                Logger.log(context, "\n### END FORCE REMOVE\n");

                Logger.log(context, "\n### BEGIN INSTALL\n");
                Logger.log(context, " \n");
                install();
                Logger.log(context, "\n### END INSTALL \n");
                Logger.log(context, "\n\n\t\t[ PRESS BACK BUTTON ]\n");
                break;
            case "remove":
                Logger.log(context, "### BEGIN FORCE REMOVE\n");
                remove();
                Logger.log(context, "### END\n");
                break;
        }
    }

}
