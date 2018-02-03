package com.zed.projectz;

import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlHelper {
    public Document parseXML(String fileName, AssetManager assetManager) {
        try {
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open(fileName);
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Rule> createRulesFromDocument(Document document){
        List<Rule> rules = new ArrayList<>();
        NodeList ruleElements = document.getElementsByTagName("rule");
        for (int i = 0; i < ruleElements.getLength(); i++) {
            Rule rule = new Rule();
            NodeList ruleChildren = ruleElements.item(i).getChildNodes();
            for (int i2 = 0; i2 < ruleChildren.getLength(); i2++) {
                Node currentProperty = ruleChildren.item(i2);
                switch (currentProperty.getNodeName()){
                    case "id":
                    {
                        rule.Id = Integer.parseInt(currentProperty.getTextContent());
                    }
                    case "title":
                    {
                        rule.Title = currentProperty.getTextContent();
                    }
                    case "text":
                    {
                        rule.Text = currentProperty.getTextContent();
                    }
                }
            }
            rules.add(rule);
        }
        return rules;
    }
}
