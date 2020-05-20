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

import com.ejar.dmb.core.license.LicenseUtils;

public class App
{

    public static void printLicense() {

        System.out.println(LicenseUtils.getLicensePrintout());

    }

    public static void main( String[] args ) {

        System.out.println( "Hello World!" );
        printLicense();

    }

}
