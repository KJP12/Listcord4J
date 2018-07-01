package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import org.json.JSONObject;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

public class Vote {
    @NotNull
    private final String id;
    private transient final long idLong;
    private final int count;
    @NotNull
    private final OffsetDateTime lastVote, nextVote;

    public Vote(@NotNull JSONObject obj) {
        idLong = Long.parseUnsignedLong(id = obj.optString("id", "0"));
        count = obj.optInt("count", -1);

        lastVote = OffsetDateTime.ofInstant(Instant.ofEpochMilli(obj.optLong("lastVote", 0)), ZoneId.systemDefault());
        nextVote = OffsetDateTime.ofInstant(Instant.ofEpochMilli(obj.optLong("nextVote", 0)), ZoneId.systemDefault());
    }

    public int hashCode() {
        return (int) idLong ^ (int) (idLong >> 32);
    }

    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        return new JSONObject().put("id", id).put("count", count)
                .put("lastVote", lastVote.toInstant().getLong(ChronoField.MILLI_OF_DAY))
                .put("nextVote", nextVote.toInstant().getLong(ChronoField.MILLI_OF_DAY));
    }


    public long getIdLong() {
        return idLong;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    @NotNull
    public OffsetDateTime getLastVote() {
        return lastVote;
    }

    @NotNull
    public OffsetDateTime getNextVote() {
        return nextVote;
    }
}
