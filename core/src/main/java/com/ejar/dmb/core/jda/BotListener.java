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

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class BotListener extends ListenerAdapter {

    private final Logger logger = LogManager.getLogger();

    public BotListener() { }

    @Override
    public void onReady(ReadyEvent event) {

        super.onReady(event);

        logger.info("Ready. Logged in as: {}", event.getJDA().getSelfUser());

        DiscordConnector.getInstance().setSleepState();

    }



    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        super.onGuildMessageReceived(event);





    }

}
