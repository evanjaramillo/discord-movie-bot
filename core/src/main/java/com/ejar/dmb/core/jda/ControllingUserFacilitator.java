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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllingUserFacilitator {

    private final Logger logger = LogManager.getLogger();

    private User user;
    private ExecutorService executorService;
    private ControllingUserTimeout timeout;
    private Future<?> timeoutFuture;

    public ControllingUserFacilitator() {

       this.init();

    }

    private void init() {

        this.executorService = Executors.newSingleThreadExecutor();
        this.timeout = new ControllingUserTimeout();

        this.timeoutFuture = this.executorService.submit(this.timeout);

    }

    public User getUser() {

        return user;

    }

    private void scheduleNewUser(User user) {

        this.user = user;
        this.timeoutFuture.cancel(true);
        this.timeout = new ControllingUserTimeout();
        this.timeoutFuture = this.executorService.submit(this.timeout);

    }

    public UserControlCaveats setUser(User user) {

        // the user has not been set yet. Set the user.
        if (this.user == null) {

            this.scheduleNewUser(user);
            return UserControlCaveats.USER_CONTROL_SUCCESS;

        }

        // If the user we get is the current user, lets extend the time.
        if (this.user == user && !this.timeoutFuture.isDone()) {

            this.timeout.counter.set(0);
            return UserControlCaveats.USER_CONTROL_FAILURE_ALREADY_SET;

        }

        if (this.user != user && !this.timeoutFuture.isDone()) {

            return UserControlCaveats.USER_CONTROL_FAILURE_TIME_NOT_YIELDED;

        }

        if (this.user != user && this.timeoutFuture.isDone()) {

            this.scheduleNewUser(user);
            return UserControlCaveats.USER_CONTROL_SUCCESS;

        }

        return UserControlCaveats.USER_CONTROL_FAILURE_UNKNOWN;

    }

    private class ControllingUserTimeout implements Runnable {

        private volatile int timeoutSeconds;
        private volatile AtomicInteger counter;

        public ControllingUserTimeout() {

            this.init();

        }

        public ControllingUserTimeout(int timeoutSeconds) {

            this.init();
            this.timeoutSeconds = timeoutSeconds;

        }

        private void init() {

            this.counter = new AtomicInteger();
            this.timeoutSeconds = 60;

        }

        @Override
        public void run() {

            try {

                counter.set(0);

                while (counter.get() != this.timeoutSeconds) {

                    Thread.sleep(1000);
                    counter.addAndGet(1);

                }

            }
            catch (InterruptedException e) {  } // Don't care about this one.
            catch (Exception e) {

                logger.error("Caught exception in timer: {}", e.getMessage());
                logger.debug("Stack trace: \n{}", ExceptionUtils.getStackTrace(e));

            }

        }

    }

}
