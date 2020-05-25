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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class BotOptions {

    static {

        YML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    }

    private String token;
    private boolean compressionEnabled;
    private boolean autoReconnect;

    private static final ObjectMapper YML_OBJECT_MAPPER;

    public BotOptions() {

        super();
        this.init();

    }

    public BotOptions fromConfigurationFile(File f) throws Exception {

        return YML_OBJECT_MAPPER.readValue(f, BotOptions.class);

    }

    private void init() {

        token = "NO TOKEN";
        this.compressionEnabled = true;
        this.autoReconnect = false;

    }

    public void writeCurrentConfiguration(File f) throws IOException {

        YML_OBJECT_MAPPER.writeValue(f, this);

    }

    public String getToken() {

        return token;

    }

    public void setToken(String token) {

        this.token = token;

    }

    public boolean isCompressionEnabled() {

        return compressionEnabled;

    }

    public void setCompressionEnabled(boolean compressionEnabled) {

        this.compressionEnabled = compressionEnabled;

    }

    public boolean isAutoReconnect() {

        return autoReconnect;

    }

    public void setAutoReconnect(boolean autoReconnect) {

        this.autoReconnect = autoReconnect;

    }

}
