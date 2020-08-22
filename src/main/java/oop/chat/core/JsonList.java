package oop.chat.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonList {
    private List<Object> list;

    public JsonList() {
        this.list = new ArrayList<>();
    }

    JsonList(List<Object> list) {
        this.list = list;
    }

    public List<Object> getList() {
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<JsonObject> getListAsObjects() {
        List<JsonObject> os = new ArrayList<>(list.size());
        for (Object o : list) {
            os.add(new JsonObject((Map<String,Object>) o));
        }
        return os;
    }

    public void addObject(JsonObject obj) {
        list.add(obj.getObj());
    }
}
