package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

public interface Listcord4J {
    @Nullable
    Bot getBot(@NotNull String id);

    @NotNull
    List<Vote> getVotes(@NotNull String id);

    void updateGuilds(@NotNull String id, int guilds, int shard);
}
