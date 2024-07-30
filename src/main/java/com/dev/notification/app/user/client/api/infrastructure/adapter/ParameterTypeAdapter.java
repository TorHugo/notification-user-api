package com.dev.notification.app.user.client.api.infrastructure.adapter;

import com.dev.notification.app.user.client.api.domain.value.object.Parameter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ParameterTypeAdapter extends TypeAdapter<Parameter> {

    @Override
    public void write(JsonWriter out, Parameter value) throws IOException {
        out.beginObject();
        out.name("name").value(value.name());
        out.name("value").value(value.value());
        out.endObject();
    }

    @Override
    public Parameter read(final JsonReader in) {
        JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String value = jsonObject.get("value").getAsString();
        return new Parameter(name, value);
    }
}
