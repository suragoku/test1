package com.blueoptima.bean;

public enum MethodType {
        GET("GET"), PUT("PUT"), DELETE("DELETE"), POST("POST");
        private String type;

        MethodType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }