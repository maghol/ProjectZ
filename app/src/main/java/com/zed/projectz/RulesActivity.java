package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class RulesActivity extends AppCompatActivity {
    private XmlHelper xmlHelper = new XmlHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        initialize();
    }

    private void initialize() {
        bindAllRules();
    }

    public void searchRule(View view) {
        View rootView = view.getRootView();
        String searchText = ((TextView) rootView.findViewById(R.id.searchRuleTextBox)).getText().toString();
        if (searchText == "") {
            bindAllRules();
            return;
        }
        List<Rule> rules = listRules();
        List<Rule> titleMatches = new ArrayList<>();
        List<Rule> textMatches = new ArrayList<>();
        List<Rule> subRuleMatches = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.Title.toLowerCase().contains(searchText.toLowerCase())) {
                titleMatches.add(rule);
            } else if (rule.Text.toLowerCase().contains(searchText.toLowerCase())) {
                textMatches.add(rule);
            }
            else if (rule.SubRules != null && rule.SubRules.size() > 0){
                for(SubRule subRule : rule.SubRules){
                    if(subRule.Text.toLowerCase().contains(searchText.toLowerCase())){
                        subRuleMatches.add(rule);
                        break;
                    }
                }
            }
        }
        for (Rule textRuleMatch : textMatches) {
            for (Rule titleRuleMatch : titleMatches) {
                if (textRuleMatch.Id == titleRuleMatch.Id) {
                    titleMatches.remove(titleRuleMatch);
                }
            }
        }

        for (Rule subRuleMatch : subRuleMatches){
            for (Rule titleRuleMatch : titleMatches){
                if(subRuleMatch.Id == titleRuleMatch.Id){
                    titleMatches.remove(titleRuleMatch);
                }
            }
            for(Rule textRuleMatch: textMatches){
                if(subRuleMatch.Id == textRuleMatch.Id){
                    textMatches.remove(textRuleMatch);
                }
            }
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rulesContainer = this.findViewById(R.id.rulesLinearLayout);
        rulesContainer.removeAllViews();
        for (Rule rule : titleMatches) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            rulesContainer.addView(inflatedRulesLayout);
            ViewGroup subRulesContainer = inflatedRulesLayout.findViewById(R.id.subRulesLinearLineout);
            subRulesContainer.removeAllViews();
            if(rule.SubRules != null && rule.SubRules.size() > 0){
                for(SubRule subRule : rule.SubRules){
                    View inflatedSubRuleLayout = inflater.inflate(R.layout.rules_rule_subrule, null, false);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleTitle)).setText(subRule.Title);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleText)).setText(subRule.Text);
                    subRulesContainer.addView(inflatedSubRuleLayout);
                }
            }
        }
        for (Rule rule : textMatches) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            rulesContainer.addView(inflatedRulesLayout);
            ViewGroup subRulesContainer = inflatedRulesLayout.findViewById(R.id.subRulesLinearLineout);
            subRulesContainer.removeAllViews();
            if(rule.SubRules != null && rule.SubRules.size() > 0){
                for(SubRule subRule : rule.SubRules){
                    View inflatedSubRuleLayout = inflater.inflate(R.layout.rules_rule_subrule, null, false);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleTitle)).setText(subRule.Title);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleText)).setText(subRule.Text);
                    subRulesContainer.addView(inflatedSubRuleLayout);
                }
            }
        }
        for (Rule rule : subRuleMatches) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            rulesContainer.addView(inflatedRulesLayout);
            ViewGroup subRulesContainer = inflatedRulesLayout.findViewById(R.id.subRulesLinearLineout);
            subRulesContainer.removeAllViews();
            if(rule.SubRules != null && rule.SubRules.size() > 0){
                for(SubRule subRule : rule.SubRules){
                    View inflatedSubRuleLayout = inflater.inflate(R.layout.rules_rule_subrule, null, false);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleTitle)).setText(subRule.Title);
                    ((TextView)inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleText)).setText(subRule.Text);
                    subRulesContainer.addView(inflatedSubRuleLayout);
                }
            }
        }
    }

    private void bindAllRules() {
        List<Rule> rules = listRules();
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rulesContainer = this.findViewById(R.id.rulesLinearLayout);
        rulesContainer.removeAllViews();
        List<View> ruleViews = new ArrayList<>();
        Collections.sort(rules);
        for (Rule rule : rules) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((ExpandableTextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            ruleViews.add(inflatedRulesLayout);

            if (rule.SubRules != null && rule.SubRules.size() > 0) {
                ViewGroup subRuleContainer = inflatedRulesLayout.findViewById(R.id.subRulesLinearLineout);
                subRuleContainer.removeAllViews();
                for (SubRule subRule : rule.SubRules) {
                    View inflatedSubRuleLayout = inflater.inflate(R.layout.rules_rule_subrule, null, false);
                    ((TextView) inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleTitle)).setText(subRule.Title);
                    ((TextView) inflatedSubRuleLayout.findViewById(R.id.ruleSubRuleText)).setText(subRule.Text);
                    subRuleContainer.addView(inflatedSubRuleLayout);
                }
            }
        }
        for (View ruleView : ruleViews) {
            rulesContainer.addView(ruleView);
        }
    }

    private List<Rule> listRules() {
        Document document = xmlHelper.parseXML("rules.xml", getAssets());
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
                    case "subrules": {
                        NodeList subRulesChildren = currentProperty.getChildNodes();
                        for (int i3 = 0; i3 < subRulesChildren.getLength(); i3++) {
                            NodeList currentChildElements = subRulesChildren.item(i3).getChildNodes();
                            SubRule subRule = new SubRule();
                            for(int i4 = 0; i4 < currentChildElements.getLength(); i4++) {
                                Node currentElement = currentChildElements.item(i4);

                                switch (currentElement.getNodeName()) {
                                    case "id": {
                                        subRule.Id = Integer.parseInt(currentElement.getTextContent());
                                    }
                                    case "title": {
                                        subRule.Title = currentElement.getTextContent();
                                    }
                                    case "text": {
                                        subRule.Text = currentElement.getTextContent();
                                    }
                                }
                            }
                            if(subRule.Title != null) {
                                rule.SubRules.add(subRule);
                            }
                        }
                    }
                    case "relatedtopics":
                    {
                        //TODO something with the realted topics tags
                    }
                }
            }
            rules.add(rule);
        }
        return rules;
    }
}
