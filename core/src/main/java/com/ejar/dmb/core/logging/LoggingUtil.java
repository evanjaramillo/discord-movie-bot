package com.ejar.dmb.core.logging;

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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public final class LoggingUtil {

    private LoggingUtil() {  }

    public static void configureRootLoggingLevel(String level) throws IllegalArgumentException {

        Level l = null;

        switch (level.toLowerCase()) {

            case "off":

                l = Level.OFF;
                break;

            case "fatal":

                l= Level.FATAL;
                break;

            case "error":

                l = Level.ERROR;
                break;

            case "warn":

                l = Level.WARN;
                break;

            case "info":

                l = Level.INFO;
                break;

            case "debug":

                l = Level.DEBUG;
                break;

            case "trace":

                l = Level.TRACE;
                break;

            case "all":

                l = Level.ALL;
                break;

        }

        if (l == null) {

            throw new IllegalArgumentException("Provided logging level '" + level + "' was invalid.");

        }

        Configurator.setAllLevels(LogManager.getRootLogger().getName(), l);

    }

}
