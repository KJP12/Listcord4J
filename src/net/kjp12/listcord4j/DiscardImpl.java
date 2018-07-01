package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Collections;
import java.util.List;

public final class DiscardImpl implements Listcord4J {
    @Nullable
    @Override
    public Bot getBot(String id) {
        return null;
    }

    @NotNull
    @Override
    public List<Vote> getVotes(String id) {
        return Collections.emptyList();
    }

    @Override
    public void updateGuilds(@NotNull String id, int guilds, int shard) {
    }
}
