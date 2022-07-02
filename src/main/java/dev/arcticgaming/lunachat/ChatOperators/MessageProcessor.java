package dev.arcticgaming.lunachat.ChatOperators;

import dev.arcticgaming.lunachat.LunaChat;
import dev.arcticgaming.lunachat.Utility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.io.*;
import java.util.ArrayList;

public class MessageProcessor {

    public static Component filterChat(Component message) {

        String serMessage = PlainTextComponentSerializer.plainText().serialize(message);
        String[] splitMessage = serMessage.split(" ");
        ArrayList<String> filteredMessageList = new ArrayList<>();

        try {
            File file = new File(LunaChat.getPlugin().getDataFolder().getAbsolutePath() + "/blacklist.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            ArrayList<String> blacklist = new ArrayList<String>();
            while (reader.readLine() != null) {
                blacklist.add(reader.readLine());
            }

            for (String word : splitMessage) {
                boolean filterThis;
                filterThis = blacklist.contains(word);

                if (filterThis) {
                    filteredMessageList.add("<yellow>**Censored**</yellow> ");
                } else {
                    filteredMessageList.add(word + " ");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder postFilterMessage = new StringBuilder();
        for (Object something : filteredMessageList) {
            postFilterMessage.append(something.toString());
        }

        return Utility.formatResolve(postFilterMessage.toString());
    }
}
