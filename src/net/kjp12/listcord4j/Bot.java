package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Bot {
    private transient final long idLong;
    @NotNull
    private final String id, username, discriminator, invite;
    @NotNull
    private final List<String> owners;
    @NotNull
    private final List<Long> ownersLong;
    @Nullable
    private final String description, website, support, avatar;
    private final boolean online, premium;
    private final int servers, invites, votes;
    @NotNull
    private final OffsetDateTime nextVote;

    public Bot(@NotNull JSONObject obj) {
        idLong = Long.parseUnsignedLong(id = obj.optString("id", "0"));
        username = obj.optString("username", "null");
        discriminator = obj.optString("discriminator", "0000");
        avatar = obj.optString("avatar");

        JSONArray jaowners = obj.getJSONArray("owners");
        String[] saowners = new String[jaowners.length()];
        Long[] laowners = new Long[saowners.length];
        for (int i = 0; i < saowners.length; i++) {
            laowners[i] = Long.parseLong(saowners[i] = jaowners.getString(i));
        }
        owners = Arrays.asList(saowners);
        ownersLong = Arrays.asList(laowners);

        description = obj.optString("description");
        website = obj.optString("website");
        support = obj.optString("support");
        invite = obj.optString("invite", "https://discordapp.com/api/oauth2/authorize?client_id=" + id + "&scope=bot");

        online = obj.optBoolean("online", false);
        premium = obj.optBoolean("premium", false);

        servers = obj.optInt("servers", -1);
        invites = obj.optInt("invites", -1);
        votes = obj.optInt("votes", -1);

        nextVote = OffsetDateTime.ofInstant(Instant.ofEpochMilli(obj.optLong("nextVote", 0)), ZoneId.systemDefault());
    }

    public int hashCode() {
        return (int) idLong ^ (int) (idLong >> 32);
    }

    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject().put("id", id).put("username", username).put("discriminator", discriminator)
                .put("invite", invite).put("owners", owners).put("online", online).put("premium", premium).put("servers", servers)
                .put("invites", invites).put("votes", votes).put("nextVote", nextVote.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        if (description != null) object.put("description", description);
        if (website != null) object.put("website", website);
        if (support != null) object.put("support", support);
        if (avatar != null) object.put("avatar", avatar);
        return object;
    }

    public long getIdLong() {
        return idLong;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @NotNull
    public String getDiscriminator() {
        return discriminator;
    }

    @NotNull
    public String getInvite() {
        return invite;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }

    @Nullable
    public String getSupport() {
        return support;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    @NotNull
    public List<String> getOwnerIds() {
        return owners;
    }

    @NotNull
    public List<Long> getOwnerIdsLong() {
        return ownersLong;
    }

    public boolean getOnline() {
        return online;
    }

    public boolean getPremium() {
        return premium;
    }

    public int getServers() {
        return servers;
    }

    public int getInvites() {
        return invites;
    }

    public int getVotes() {
        return votes;
    }

    @NotNull
    public OffsetDateTime getNextVote() {
        return nextVote;
    }
}
