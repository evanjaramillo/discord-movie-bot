package com.ejar.dmb.core.license;

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

import com.ejar.dmb.core.version.Version;
import com.ejar.dmb.core.version.VersionUtils;

public final class LicenseUtils {

    private LicenseUtils() {  }

    public static String getLicensePrintout() {

        return  "    " + Version.PROJECT_NAME + "\n" +
                "    Copyright (C) " + VersionUtils.getVersionLocalDateTime().getYear() + " Evan R. Jaramillo\n\n" +
                "    This program comes with ABSOLUTELY NO WARRANTY.\n" +
                "    This is free software, and you are welcome to redistribute it\n" +
                "    under certain conditions. For more information, see <https://www.gnu.org/licenses/>.";

    }

}
