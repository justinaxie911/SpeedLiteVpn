/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.view;

import android.content.Context;
import androidx.preference.R;
import android.util.AttributeSet;
import androidx.preference.EditTextPreference;

public class PasswordEditTextPreference extends EditTextPreference {
    private CharSequence mDefaultSummary = getSummary();

    public PasswordEditTextPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextPreferenceStyle);
        mDefaultSummary = getSummary();
    }

    public PasswordEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        setSummary(text);
    }

    @Override
    public void setSummary(CharSequence summary) {
        if (summary.toString().isEmpty()) {
            super.setSummary(mDefaultSummary);
        } else {
            StringBuilder sb = new StringBuilder();
            int length = summary.toString().length();
            for(int i = 0; i < length; i++) {
                sb.append("*");
            }
            super.setSummary(sb.toString());
        }
    }
}
