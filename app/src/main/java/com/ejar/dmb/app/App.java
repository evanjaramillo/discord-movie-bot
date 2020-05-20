package com.ejar.dmb.app;

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
