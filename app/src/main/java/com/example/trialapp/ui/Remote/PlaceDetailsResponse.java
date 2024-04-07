package com.example.trialapp.ui.Remote;

public class PlaceDetailsResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private AddressComponent[] address_components;

        public AddressComponent[] getAddress_components() {
            return address_components;
        }

        public void setAddress_components(AddressComponent[] address_components) {
            this.address_components = address_components;
        }
    }

    public static class AddressComponent {
        private String long_name;
        private String short_name;
        private String[] types;

        public String getLong_name() {
            return long_name;
        }

        public void setLong_name(String long_name) {
            this.long_name = long_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String[] getTypes() {
            return types;
        }

        public void setTypes(String[] types) {
            this.types = types;
        }
    }
}
