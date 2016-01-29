package com.example.jwetesko.travelapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jwetesko on 16.01.16.
 */
public class XMLReader {

    public static ArrayList<Country> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Country> countries = null;
        int eventType = parser.getEventType();
        String currentName = null;
        Country currentCountry = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList<Country>();
                    break;
                case XmlPullParser.START_TAG:
                    System.out.println("oho");
                    String tag = parser.getName();
                    if (tag.equals("country")) {
                        currentCountry = new Country();
                    }
                    else if (currentCountry != null) {
                        switch (tag) {
                            case ("dest"):
                                currentName = parser.nextText();;
                                currentCountry.setProperty("name", currentName);
                            case ("url"):
                                currentName = parser.nextText();
                                System.out.println(currentName);
                                currentCountry.setProperty("url", currentName);
                            case ("currency"):
                                currentName = parser.nextText();
                                currentCountry.setProperty("currency", currentName);
                            case ("language"):
                                currentName = parser.nextText();
                                currentCountry.setProperty("language", currentName);
                            case ("capital"):
                                currentName = parser.nextText();
                                currentCountry.setProperty("capital", currentName);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tag = parser.getName();
                    System.out.println(tag);
                    if (currentCountry != null && tag.equalsIgnoreCase("country")) {
                        countries.add(currentCountry);
                    }
                    break;
            }
            eventType = parser.next();
        }
        return countries;
    }
}
