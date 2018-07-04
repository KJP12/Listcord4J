package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface Listcord4J {
    String LISTCORD_URL = "https://listcord.com/api/";

    @Nullable
    Bot getBot(@NotNull String id);

    @NotNull
    List<Vote> getVotes(@NotNull String id);

    @NotNull
    List<Integer> getBots(SortingType type, int limit, int offset, @Nullable String search);

    void updateGuilds(@NotNull String id, int guilds, int shard);

    enum SortingType {
        VOTES, NEWEST, SERVERS;

        public URL toUrl(int limit, int offset, @Nullable String query) throws IOException {
            StringBuilder sb = new StringBuilder(LISTCORD_URL).append("bots/").append(name().toLowerCase()).append('/');
            if (limit > 0 && offset >= 0) sb.append(limit).append('/').append(offset).append('/');
            if (query != null) sb.append("?q=").append(query);
            return new URL(sb.toString());
        }
    }
}
