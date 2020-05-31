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

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class BotMessageListener extends ListenerAdapter {

    private final Logger logger = LogManager.getLogger();

    private final DiscordConnector connector;

    public BotMessageListener() {

        this.connector = DiscordConnector.getInstance();

    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        super.onGuildMessageReceived(event);

        if (event.getAuthor().isBot()) {

            return;

        }

        String[] msg = event.getMessage().getContentRaw().split("\\s+");

        User userCandidate = event.getAuthor();

        logger.debug("Guild message received from: {}, member: {}, content: {}",
                userCandidate.getName(),
                event.getMember(),
                event.getMessage());

        if (msg.length >= 1 && !msg[0].toLowerCase().equals("%dmb%")) {

            return;

        }

        User current = this.connector.getControllingUser();

        UserControlCaveats c = this.connector.setControllingUser(userCandidate);

        String s = UserControlCaveats.getFailureContext(userCandidate, current, c);

        event.getGuild().getTextChannelById(event.getMessage().getTextChannel().getId())
                .sendMessage(s).queue();

        this.connector.setWakeState(userCandidate);

        String[] arguments = Arrays.copyOfRange(msg, 1, msg.length);

    }


}
