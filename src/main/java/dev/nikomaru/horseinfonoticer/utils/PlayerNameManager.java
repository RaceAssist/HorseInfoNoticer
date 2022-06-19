package dev.nikomaru.horseinfonoticer.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerNameManager {

    private final String PROFILE_URL = "https://api.ashcon.app/mojang/v2/user/";
    private final Map<UUID, String> cache = new HashMap<>();
    private final List<UUID> requestList = new ArrayList<>();

    public String getPlayerName(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        if (!requestList.contains(uuid)) {
            requestList.add(uuid);

            new Thread(() -> {
                var suuid = uuid.toString().toLowerCase().replaceAll("-", "");

                try {
                    var url = new URL(PROFILE_URL + suuid);
                    var gson = new Gson();
                    var nameJson = IOUtils.toString(url, StandardCharsets.UTF_8);
                    var nameValue = gson.fromJson(nameJson, JsonObject.class);
                    var name =  nameValue.get("username").toString().replace("\"", "");
                    cache.put(uuid, name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                var index = requestList.indexOf(uuid);
                if (index >= 0) {
                    requestList.remove(index);
                }
            }).start();
        }

        return uuid.toString().substring(0, 13);
    }
}