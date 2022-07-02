package dev.arcticgaming.lunachatmc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

public class Utility {

    /** Resolves MiniMessage tags from strings
     *
     * @param string
     * @return Component
     */
    public static Component formatResolve(String string) {

        MiniMessage serializer = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .build()
                )
                .build();

        return serializer.deserialize(string);
    }




}
