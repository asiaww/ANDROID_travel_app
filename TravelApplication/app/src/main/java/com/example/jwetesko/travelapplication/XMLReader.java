package com.example.jwetesko.travelapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jwetesko on 16.01.16.
 */
public class XMLReader {

    public static ArrayList<String> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<String> destinationsList = null;
        int eventType = parser.getEventType();
        String currentName = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    destinationsList = new ArrayList<String>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    if (tag.equals("dest")) {
                        try {
                            currentName = parser.nextText();
                            destinationsList.add(currentName);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        return destinationsList;
    }
}
