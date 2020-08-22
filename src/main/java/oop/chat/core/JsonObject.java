package oop.chat.core;

import csl.dataproc.tgls.util.JsonReader;
import csl.dataproc.tgls.util.JsonWriter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonObject {
    private Map<String, Object> obj;

    public JsonObject() {
        this.obj = new LinkedHashMap<>();
    }

    JsonObject(Map<String, Object> obj) {
        this.obj = obj;
    }

    public Map<String, Object> getObj() {
        return obj;
    }

    public void putString(String name, String value) {
        obj.put(name, value);
    }

    public void putLong(String name, long value) {
        obj.put(name, value);
    }

    public void putBoolean(String name, boolean value) {
        obj.put(name, value);
    }

    public void putTime(String name, OffsetDateTime time) {
        obj.put(name, timeToString(time));
    }

    public JsonList putList(String name) {
        JsonList l = new JsonList();
        obj.put(name, l.getList());
        return l;
    }

    public void putObject(String name, JsonObject json) {
        obj.put(name, json.getObj());
    }

    public String getString(String name) {
        return (String) obj.get(name);
    }

    public long getLong(String name) {
        return ((Number) obj.get(name)).longValue();
    }

    public boolean getBoolean(String name) {
        return (Boolean) obj.get(name);
    }

    public OffsetDateTime getTime(String name) {
        return timeFromString((String) obj.get(name));
    }

    @SuppressWarnings("unchecked")
    public JsonObject getObject(String name) {
        Object o = obj.get(name);
        if (o == null) {
            return null;
        } else {
            return new JsonObject((Map<String, Object>) o);
        }
    }

    @SuppressWarnings("unchecked")
    public JsonList getList(String name) {
        return new JsonList((List<Object>) obj.get(name));
    }

    @Override
    public String toString() {
        return obj.toString();
    }

    @SuppressWarnings("unchecked")
    public static JsonObject fromSource(String source) {
        Map<String,Object> obj = (Map<String,Object>) JsonReader.v(source).parseValue();
        return new JsonObject(obj);
    }

    public String toSource() {
        return JsonWriter.v().write(obj).toSource();
    }

    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static String timeToString(OffsetDateTime time) {
        return time.format(timeFormatter);
    }

    public static OffsetDateTime timeFromString(String timeStr) {
        return OffsetDateTime.parse(timeStr, timeFormatter);
    }
}
