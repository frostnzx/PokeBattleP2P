package utils;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import entity.Antidote;
import entity.Awakening;
import entity.FullRestorePotion;
import entity.Item;
import entity.ParalyzeHeal;
import entity.Potion;
import entity.SuperPotion;
public class ItemTypeAdapter implements JsonDeserializer<Item>, JsonSerializer<Item> {
    @Override
    public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();   
        if (src != null) {
            jsonObject.addProperty("itemType", src.getItemType());
            jsonObject.add("data", context.serialize(src)); // Serialize the actual object
        } else {
            // You can log or handle null case here if needed.
            jsonObject.addProperty("itemType", "Unknown");
        }
        
        return jsonObject;
    }

    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String itemType = null;
        // Handle case when "itemType" might be missing or null
        if (jsonObject.has("itemType") && !jsonObject.get("itemType").isJsonNull()) {
            itemType = jsonObject.get("itemType").getAsString();
        }

        JsonElement data = jsonObject.get("data");

        if (itemType == null || data == null || data.isJsonNull()) {
            throw new JsonParseException("Missing or invalid 'itemType' or 'data' field.");
        }

        // Deserialize based on the itemType
        return context.deserialize(data, getItemClass(itemType));
    }
    private Class<? extends Item> getItemClass(String itemType) {
        switch (itemType) {
            case "Antidote": return Antidote.class;
            case "Awakening": return Awakening.class;
            case "FullRestorePotion": return FullRestorePotion.class;
            case "ParalyzeHeal": return ParalyzeHeal.class;
            case "Potion": return Potion.class;
            case "SuperPotion": return SuperPotion.class;
            default: throw new JsonParseException("Unknown item type: " + itemType);
        }
    }
}