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

public enum UserControlCaveats {

    USER_CONTROL_SUCCESS                  (0),
    USER_CONTROL_FAILURE_TIME_NOT_YIELDED (-1),
    USER_CONTROL_FAILURE_ALREADY_SET      (-2),
    USER_CONTROL_FAILURE_UNKNOWN          (-100);

    private int lvl;

    private UserControlCaveats(int lvl) {

        this.lvl = lvl;

    }

    public int getLvl() {

        return lvl;

    }

    public static boolean isFailure(UserControlCaveats c) {

        return (c.getLvl() < 0);

    }

    public static String getFailureContext(User userCandidate, User currentUser, UserControlCaveats ucc) {

        StringBuilder builder = new StringBuilder();

        String current = (currentUser == null ? "null" : currentUser.getName());
        String candidate = (userCandidate == null ? "null" : userCandidate.getName());

        switch (ucc) {

            case USER_CONTROL_SUCCESS:

                builder
                        .append("Successfully switched control from '")
                        .append(current)
                        .append("' to '")
                        .append(candidate)
                        .append(".");

                break;

            case USER_CONTROL_FAILURE_TIME_NOT_YIELDED:

                builder
                        .append("Failed to switch control from '")
                        .append(current)
                        .append("' to '")
                        .append(candidate)
                        .append("'. ")
                        .append(current)
                        .append(" has not yielded control.");

                break;

            case USER_CONTROL_FAILURE_ALREADY_SET:

                builder
                        .append("Failed to switch control from '")
                        .append(current)
                        .append("' to '")
                        .append(candidate)
                        .append("'. Already set.");

                break;

            case USER_CONTROL_FAILURE_UNKNOWN:
            default:

                builder
                        .append("Failed to switch control from '")
                        .append(current)
                        .append("' to '")
                        .append(candidate)
                        .append("'. Unknown problem.");

        }

        return builder.toString();

    }

}
