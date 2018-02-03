package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        for (Rule rule : rules) {
            if (rule.Title.toLowerCase().contains(searchText.toLowerCase())) {
                titleMatches.add(rule);
            } else if (rule.Text.toLowerCase().contains(searchText.toLowerCase())) {
                textMatches.add(rule);
            }
        }
        for (Rule textRuleMatch : textMatches) {
            for (Rule titleRuleMatch : titleMatches) {
                if (textRuleMatch.Id == titleRuleMatch.Id) {
                    titleMatches.remove(titleRuleMatch);
                }
            }
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rulesContainer = this.findViewById(R.id.rulesLinearLayout);
        rulesContainer.removeAllViews();
        for (Rule rule : titleMatches) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            rulesContainer.addView(inflatedRulesLayout);
        }
        for (Rule rule : textMatches) {
            View inflatedRulesLayout = inflater.inflate(R.layout.rules_rule, null, false);
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            rulesContainer.addView(inflatedRulesLayout);
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
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleHeaderText)).setText(rule.Title);
            ((TextView) inflatedRulesLayout.findViewById(R.id.ruleDescriptionText)).setText(rule.Text);
            ruleViews.add(inflatedRulesLayout);
        }
        for (View ruleView : ruleViews) {
            rulesContainer.addView(ruleView);
        }
    }

    private List<Rule> listRules() {
        Document document = xmlHelper.parseXML("rules.xml", getAssets());
        return xmlHelper.createRulesFromDocument(document);
    }
}
