package com.ejar.dmb.app;

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

import com.ejar.dmb.core.jda.BotOptions;
import com.ejar.dmb.core.jda.DiscordConnector;
import com.ejar.dmb.core.license.LicenseUtils;
import com.ejar.dmb.core.logging.LoggingUtil;
import com.ejar.dmb.core.version.Version;
import com.ejar.dmb.core.version.VersionUtils;

import org.apache.commons.cli.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class App
{
    private static final Logger logger = LogManager.getLogger();
    private static CommandLine cli;
    private static Options options;

    private static void printVersion() {

        System.out.println(VersionUtils.getVersionPrintout() + "\n");

    }

    private static void printLicense() {

        System.out.println(LicenseUtils.getLicensePrintout() + "\n");

    }

    private static void printHelp() {

        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(
                "java -jar dmb.jar [OPTIONS]",
                Version.PROJECT_NAME,
                options,
                "Check out the source: <https://github.com/evanjaramillo/discord-movie-bot>"
        );

    }

    private static Options getCommandlineOptions() {

        Options opts = new Options();

        Option help = Option.builder("h")
                .hasArg(false)
                .required(false)
                .argName("Display Help")
                .longOpt("help")
                .desc("Displays the help menu.")
                .build();

        opts.addOption(help);

        Option version = Option.builder("v")
                .hasArg(false)
                .required(false)
                .argName("Display Version")
                .longOpt("version")
                .desc("Displays the application name and version.")
                .build();

        opts.addOption(version);

        Option license = Option.builder("l")
                .hasArg(false)
                .required(false)
                .argName("Display License")
                .longOpt("license")
                .desc("Displays the application license.")
                .build();

        opts.addOption(license);

        Option logging = Option.builder("log")
                .hasArg(true)
                .required(false)
                .argName("level")
                .longOpt("logging")
                .desc("Sets the root logging level for the application. [error | warn | info | debug | trace].")
                .build();

        opts.addOption(logging);

        return opts;

    }

    private static boolean checkForPrintables() {

        boolean didHavePrintable = false;

        if (cli.hasOption("h")) {

            printHelp();
            didHavePrintable = true;

        }

        if (cli.hasOption("v")) {

            printVersion();
            didHavePrintable = true;

        }

        if (cli.hasOption("l")) {

            printLicense();
            didHavePrintable = true;

        }

        return didHavePrintable;

    }

    public static void main( String[] args ) {

        cli = null;
        options = getCommandlineOptions();

        try {

            if (args.length == 0) {

                printHelp();
                return;

            }

            cli = new DefaultParser().parse(options, args);

            if (checkForPrintables()) {

                return;

            }

            if (cli.hasOption("log")) {

                LoggingUtil.configureRootLoggingLevel(cli.getOptionValue("log"));

            }

            logger.info("Started {}", Version.PROJECT_NAME);

            File configuration = new File("./dmb_cfg.yml");

            BotOptions botOptions = new BotOptions();

            if (!configuration.exists()) {

                logger.info("No configuration file found. Creating from default options: {}",
                        configuration.getAbsolutePath());

                botOptions.writeCurrentConfiguration(configuration);


            } else {

                logger.info("Reading in configuration file: {}",
                        configuration.getAbsolutePath());

            }

            botOptions = new BotOptions()
                    .fromConfigurationFile(configuration);

            DiscordConnector c = new DiscordConnector(botOptions);
            c.connect();


        } catch (ParseException e) {

            logger.error("Exception encountered while processing command-line arguments. \n{}",
                    e.getMessage());

            logger.debug("Stack trace: \n{}", ExceptionUtils.getStackTrace(e));

        } catch (Throwable e) {

            logger.error("Unhandled Exception: {}",
                    e.getMessage());

            logger.debug("Stack trace: \n{}", ExceptionUtils.getStackTrace(e));

        }

    }

}
