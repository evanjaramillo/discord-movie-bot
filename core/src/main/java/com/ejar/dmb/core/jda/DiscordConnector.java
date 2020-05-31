package com.ejar.dmb.core.jda;

/*
 * Discord Movie Bot
 * Copyright (C) 2020  Evan R. Jaramillo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.Presence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class DiscordConnector {

    private final Logger logger = LogManager.getLogger();

    private static final DiscordConnector instance = new DiscordConnector();

    private final String[] wittySleep = {
            "my eyelids ...",
            "the sheep ...",
            "my dreams ..."
    };

    private final String[] wittyWake = {
            "whoms't awoke me from my slumber?!"
    };

    private BotOptions options;
    private BotListener listener;
    private BotMessageListener messageListener;
    private JDA jda;

    private ControllingUserFacilitator controllingUserFacilitator;

    private ReentrantLock controllingUserLock;

    private DiscordConnector() {

        this.init();

    }

    public static DiscordConnector getInstance() {

        return instance;

    }

    private void init() {

        this.options = new BotOptions();

        this.controllingUserLock = new ReentrantLock();

        this.controllingUserFacilitator = new ControllingUserFacilitator();

    }

    public void connect() throws LoginException,
                                 InterruptedException
    {

        logger.info("Creating JDA instance...");

        this.jda = new JDABuilder()
                    .setToken(this.options.getToken())
                    .setCompressionEnabled(this.options.isCompressionEnabled())
                    .setAutoReconnect(this.options.isAutoReconnect())
                    .build()
                    .awaitReady();

        this.setSleepState();

        this.messageListener = new BotMessageListener();
        this.jda.addEventListener(this.messageListener);

    }

    public BotOptions getOptions() {

        return options;

    }

    public void setOptions(BotOptions options) {

        this.options = options;

    }

    public JDA getJda() {

        return jda;

    }

    public User getControllingUser() {

        controllingUserLock.lock();

        try {

            return this.controllingUserFacilitator.getUser();

        } finally {

            controllingUserLock.unlock();

        }

    }

    public UserControlCaveats setControllingUser(User controllingUser) {

        controllingUserLock.lock();

        UserControlCaveats c = UserControlCaveats.USER_CONTROL_FAILURE_UNKNOWN;

        try {

            User current = this.getControllingUser();

            c = this.controllingUserFacilitator.setUser(controllingUser);

            logger.info("{}", UserControlCaveats.getFailureContext(controllingUser, current, c));



        } finally {

            controllingUserLock.unlock();

        }

        return c;

    }

    public void setSleepState() {

        Presence p = this.getJda().getPresence();
        p.setPresence(OnlineStatus.IDLE, Game.watching(
                this.wittySleep[ThreadLocalRandom.current().nextInt(0, this.wittySleep.length)]
        ));

    }

    public void setWakeState(User u) {

        Presence p = this.getJda().getPresence();
        p.setPresence(OnlineStatus.ONLINE, Game.listening(u.getName() + "'s command!"));

    }

}
