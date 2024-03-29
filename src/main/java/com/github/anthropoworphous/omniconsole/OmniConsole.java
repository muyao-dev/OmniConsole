package com.github.anthropoworphous.omniconsole;

import com.github.anthropoworphous.cmdlib.processor.CMDRegister;
import com.github.anthropoworphous.omniconsole.cmd.LogStuffCMD;
import com.github.anthropoworphous.omniconsole.cmd.ReloadConfigCMD;
import com.github.anthropoworphous.omniconsole.cmd.HostSocketCMD;
import io.javalin.websocket.WsContext;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class OmniConsole extends JavaPlugin {
    private static final List<WsContext> clients = new ArrayList<>();
    private static OmniConsole plugin = null;

    public static OmniConsole getPlugin() {
        return plugin;
    }
    public static List<WsContext> getClients() {
        return clients;
    }

    @Override
    public void onEnable() {
        //set plugin instance
        plugin = this;
        saveDefaultConfig();
        reloadConfig();

        //register cmds
        CMDRegister.registerCMD(new LogStuffCMD(), this);
        CMDRegister.registerCMD(new ReloadConfigCMD(), this);
        CMDRegister.registerCMD(new HostSocketCMD.StartCMD(), this);
    }

    public static void sendLog(String log) {
        clients.forEach(c -> c.send(log));
    }
}
