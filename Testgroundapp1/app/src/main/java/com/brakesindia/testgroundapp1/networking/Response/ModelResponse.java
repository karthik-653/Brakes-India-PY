package com.brakesindia.testgroundapp1.networking.Response;

import androidx.annotation.NonNull;

import java.util.List;

public class ModelResponse {
    public List<String> getModels() {
        return models;
    }

    @NonNull
    @Override
    public String toString() {
        return models.toString();
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    private List<String> models;
}
