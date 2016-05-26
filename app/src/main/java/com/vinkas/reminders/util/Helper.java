package com.vinkas.reminders.util;

import com.vinkas.reminders.Application;

import com.vinkas.firebase.reminders.List;

/**
 * Created by Vinoth on 19-5-16.
 */
public class Helper extends com.vinkas.util.Helper {

    public Helper(Boolean firebase_persistence_enabled) {
        super(firebase_persistence_enabled);
    }

    public static Helper getInstance() {
        return getApplication().getHelper();
    }

    public static Application getApplication() {
        return (Application) application;
    }

    private List list;

    public List getList() {
        return list;
    }

    public void setList(List items) {
        list = items;
    }

    @Override
    public void onReady(String userId) {
        super.onReady(userId);
        setList(new List("reminders", userId));
    }
}
