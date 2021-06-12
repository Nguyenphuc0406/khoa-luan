package com.hust.medtech.base.request;

import java.util.List;

public class FCMRequest {
    public List<String> registration_ids;
    public Notification notification;

    public static class Notification{
        public String body;
        public String title;

    }
}
