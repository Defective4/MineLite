package io.github.defective4.minelite.core.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * This class contains methods used to parse chat messages
 * 
 * @author Defective4
 *
 */
public class Messages {

    private static final Map<String, String> KEYS = new ConcurrentHashMap<String, String>();

    static {
        try (InputStreamReader reader = new InputStreamReader(Messages.class.getResourceAsStream("/lang.json"))) {
            final JsonElement el = new JsonParser().parse(reader);
            if (el instanceof JsonObject) {
                for (final Entry<String, JsonElement> e : el.getAsJsonObject().entrySet()) {
                    final String key = e.getKey();
                    if (e.getValue() instanceof JsonPrimitive) {
                        KEYS.put(key, e.getValue().getAsString());
                    }
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String translate(final String key) {
        return KEYS.getOrDefault(key, key);
    }

    /**
     * Parse JSON message to user readable format.
     * 
     * @param json
     * @return parsed message
     */
    public static String parse(final String json) {
        return parse(new JsonParser().parse(json));
    }

    private static String parse(final JsonElement json) {
        String text = "";
        if (json instanceof JsonObject) {
            final JsonObject obj = (JsonObject) json;
            if (obj.has("text")) {
                text = parse(obj.get("text"));
            }
            if (obj.has("translate")) {
                text = translate(parse(obj.get("translate")));
                if (obj.has("with")) {
                    final JsonElement withEl = obj.get("with");
                    if (withEl instanceof JsonArray) {
                        final List<String> with = new ArrayList<>();
                        for (final JsonElement withElement : (JsonArray) withEl) {
                            with.add(parse(withElement));
                        }
                        text = String.format(text, with.toArray(new Object[0]));
                    }
                }
            }
            if (obj.has("extra")) {
                text += parse(obj.get("extra"));
            }
        } else if (json instanceof JsonArray) {
            for (final JsonElement el : (JsonArray) json) {
                text += parse(el);
            }
        } else if (json instanceof JsonPrimitive) {
            text = json.getAsString();
        }
        return text;
    }
}
