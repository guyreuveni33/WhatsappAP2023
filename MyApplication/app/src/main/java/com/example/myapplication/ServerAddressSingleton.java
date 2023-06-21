package com.example.myapplication;
public class ServerAddressSingleton {
    private static ServerAddressSingleton instance;
    private String serverAddress;

    private ServerAddressSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ServerAddressSingleton getInstance() {
        if (instance == null) {
            instance = new ServerAddressSingleton();
        }
        return instance;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}
