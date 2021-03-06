package com.zed.projectz;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class Rule implements Comparable {
    public int Id;

    public String Title;

    public String Text;

    public List<SubRule> SubRules = new ArrayList<>();

    @Override
    public int compareTo(@NonNull Object o) {
        Rule ruleToCompareTo = (Rule) o;
        if (ruleToCompareTo == null) {
            throw new ClassCastException("Object isn't of class 'Rule'.");
        }
        return Title.toLowerCase().compareTo(ruleToCompareTo.Title.toLowerCase());
    }
}
