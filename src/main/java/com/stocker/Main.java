package com.stocker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);

        WebAppContext webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/stocker");
        webapp.setParentLoaderPriority(true);
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
