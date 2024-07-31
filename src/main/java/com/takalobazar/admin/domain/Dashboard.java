package com.takalobazar.admin.domain;

import java.util.List;

public class Dashboard {
    private int ongoingExchanges;
    private int acceptedExchanges;
    private int refusedExchanges;
    private int cancelledExchanges;
    private List<ObjectByCategory> objectsByCategory;
    private List<ExchangeBetweenDates> exchangesBetweenDates;
    private List<ExchangeByUser> exchangesByUser;

    public int getAcceptedExchanges() {
        return acceptedExchanges;
    }

    public int getCancelledExchanges() {
        return cancelledExchanges;
    }

    public int getRefusedExchanges() {
        return refusedExchanges;
    }

    public void setAcceptedExchanges(int acceptedExchanges) {
        this.acceptedExchanges = acceptedExchanges;
    }

    public void setCancelledExchanges(int cancelledExchanges) {
        this.cancelledExchanges = cancelledExchanges;
    }

    public void setRefusedExchanges(int refusedExchanges) {
        this.refusedExchanges = refusedExchanges;
    }

    public int getOngoingExchanges (){
        return this.ongoingExchanges;
    }

    public List<ExchangeBetweenDates> getExchangesBetweenDates() {
        return exchangesBetweenDates;
    }

    public List<ObjectByCategory> getObjectsByCategory() {
        return objectsByCategory;
    }

    public List<ExchangeByUser> getExchangesByUser() {
        return exchangesByUser;
    }

    public void setExchangesBetweenDates(List<ExchangeBetweenDates> exchangesBetweenDates) {
        this.exchangesBetweenDates = exchangesBetweenDates;
    }

    public void setOngoingExchanges(int ongoingExchanges) {
        this.ongoingExchanges = ongoingExchanges;
    }

    public void setObjectsByCategory(List<ObjectByCategory> objectsByCategory) {
        this.objectsByCategory = objectsByCategory;
    }

    public void setExchangesByUser(List<ExchangeByUser> exchangesByUser) {
        this.exchangesByUser = exchangesByUser;
    }

    public static class ObjectByCategory {
        private int id;
        private String name;
        private int object_count;

        public int getId() {
            return id;
        }

        public int getObject_count() {
            return object_count;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setObject_count(int object_count) {
            this.object_count = object_count;
        }
    }

    public static class ExchangeBetweenDates {
        private String type;
        private String period;
        private int exchange_count;

        public int getExchange_count() {
            return exchange_count;
        }

        public void setExchange_count(int exchange_count) {
            this.exchange_count = exchange_count;
        }

        public String getPeriod() {
            return period;
        }

        public String getType() {
            return type;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ExchangeByUser {
        private int user_id;
        private String username;
        private int exchange_count;
        private double percentage;

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public int getExchange_count() {
            return exchange_count;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setExchange_count(int exchange_count) {
            this.exchange_count = exchange_count;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
