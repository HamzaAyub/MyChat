package com.example.hp.mychat.model;

import android.graphics.Bitmap;

/**
 * Created by hp on 7/7/2017.
 */

    public class ListItem {

        private String name;
        private String number;

        private Bitmap photo;

        public Bitmap getPhoto() {
            return photo;
        }

        public void setPhoto(Bitmap photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

    }
