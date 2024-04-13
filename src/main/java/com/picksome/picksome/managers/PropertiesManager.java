package com.picksome.picksome.managers;

import com.picksome.picksome.MainApp;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {
    Properties userProperties;
    Properties languageProperties;

    public PropertiesManager() {
        loadUserProperties();
        loadLanguageProperties();
    }

    public void loadUserProperties() {
        try (InputStream input = new FileInputStream("./keys.properties")) {

            Properties prop = new Properties();
            this.userProperties = prop;

            prop.load(input);

        } catch (FileNotFoundException fileNotFoundException) {
            Properties properties = new Properties();

            properties.setProperty("app.language", "EN");
            properties.setProperty("key.games", "");

            String filePath = "./keys.properties";

            try {
                OutputStream outputStream = new FileOutputStream(filePath);

                properties.store(outputStream, null);

                outputStream.close();

            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setUserProperties(String gameKey, String languageAbbr) {
        try (OutputStream output = new FileOutputStream("./keys.properties")) {

            userProperties.setProperty("key.games", gameKey);
            userProperties.setProperty("app.language", languageAbbr);

            userProperties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void loadLanguageProperties() {
        try (InputStream input = MainApp.class.getResourceAsStream("language.properties")) {

            Properties prop = new Properties();
            this.languageProperties = prop;

            prop.load(input);


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getLanguageProperty(String property) {
        return languageProperties.getProperty(property);
    }

    public String getTextinLanguage(String textId) {
        return getLanguageProperty(getCurrentLanguageAbbr() + "." + textId);
    }

    public Properties getUserProperties() {
        return userProperties;
    }
    public String getCurrentLanguageAbbr() {
        return getUserProperties().getProperty("app.language", "EN");
    }

    public String getRawgKey() {
        return getUserProperties().getProperty("key.games");
    }
}
