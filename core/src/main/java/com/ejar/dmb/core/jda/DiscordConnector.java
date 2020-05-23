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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

public class DiscordConnector {

    private final Logger logger = LogManager.getLogger();

    private BotOptions options;
    private BotListener listener;
    private JDA jda;

    public DiscordConnector() {

        this.init();

    }

    public DiscordConnector(BotOptions options) {

        this.init();
        this.options = options;

    }

    private void init() {

        this.options = new BotOptions();

    }

    public void connect() throws LoginException {

        logger.info("Creating JDA instance...");

        new JDABuilder(this.options.getToken())
                .build();

        logger.info("Created JDA instance: {}", this.jda.toString());
    }

}
