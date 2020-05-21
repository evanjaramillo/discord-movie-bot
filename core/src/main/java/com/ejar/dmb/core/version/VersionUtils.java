package com.ejar.dmb.core.version;

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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class VersionUtils {

    static {

        BUILD_LOCAL_DATE_TIME = LocalDateTime.parse(
                Instant
                        .parse(Version.BUILD_TIME)
                        .atZone(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

    }

    private static final LocalDateTime BUILD_LOCAL_DATE_TIME;

    private VersionUtils() {  }

    /**
     * Gets the LocalDateTime object of the current build.
     *
     * @return
     *      LocalDateTime
     */
    public static LocalDateTime getVersionLocalDateTime() {

        return BUILD_LOCAL_DATE_TIME;

    }

    /**
     * Gets the version String to display to users.
     *
     * @return
     *      String
     */
    public static String getVersionPrintout() {

        return "    " + Version.PROJECT_NAME + " v" + Version.BUILD_VERSION;

    }

}
