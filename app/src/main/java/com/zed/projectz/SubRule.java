package com.zed.projectz;

import android.support.annotation.NonNull;

/**
 * Created by Mathilda on 2018-02-08.
 */

public class SubRule extends Rule implements Comparable {
    public int Id;

    public String Title;

    public String Text;

    @Override
    public int compareTo(@NonNull Object o) {
        Rule ruleToCompareTo = (Rule) o;
        if (ruleToCompareTo == null) {
            throw new ClassCastException("Object isn't of class 'Rule'.");
        }
        return Title.toLowerCase().compareTo(ruleToCompareTo.Title.toLowerCase());
    }
}
