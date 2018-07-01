package net.kjp12.listcord4j;//Created on 7/1/18.

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Listcord4J {
    private static final String USER_AGENT = "Listcord4J/0.0.0 (https://github.com/KJP12/Listcord4J)";
    protected final Logger LOGGER = LoggerFactory.getLogger(Listcord4J.class);
    private final OkHttpClient CLIENT = new OkHttpClient();
    private final String TOKEN;

    public Listcord4J(String token) {
        TOKEN = token;
    }

    @Nullable
    public Bot getBot(String id) {
        try {
            Response r = get(new URL("https://listcord.com/api/bot/" + id), headers().build());
            if (!r.isSuccessful()) {
                LOGGER.warn("Couldn't get bot " + id + "; Got " + r.code());
                return null;
            }
            ResponseBody rb = r.body();
            if (rb == null) {
                LOGGER.warn("Null body for bot " + id + "; Got " + r.code());
                return null;
            }
            return new Bot(new JSONObject(new JSONTokener(rb.charStream())));
        } catch (IOException ioe) {
            LOGGER.error("Couldn't get bot " + id, ioe);
        }
        return null;
    }

    @NotNull
    public List<Bot> getBots(int limit, int offset, @Nullable String search) {
        if (limit < 0) throw new IllegalArgumentException("Limit < 0");
        if (offset < 0) throw new IndexOutOfBoundsException("Offset < 0");
        try {
            Headers.Builder builder = headers();
            if (search != null) builder.add("q", search);
            Response r = get(new URL("https://listcord.com/api/bots/" + limit + "/" + offset), builder.build());
            if (!r.isSuccessful()) {
                LOGGER.warn("Couldn't get bots with l:" + limit + ", o:" + offset + ", q:" + search + "; Got " + r.code());
                return Collections.emptyList();
            }
            ResponseBody rb = r.body();
            if (rb == null) {
                LOGGER.warn("Null body with l:" + limit + ", o:" + offset + ", q:" + search + "; Got " + r.code());
                return Collections.emptyList();
            }
            JSONArray jsona = new JSONArray(new JSONTokener(rb.charStream()));
            int length = jsona.length();
            ArrayList<Bot> bots = new ArrayList<>(length);
            for (int i = 0; i < length; i++) bots.add(i, new Bot(jsona.getJSONObject(i)));
            return Collections.unmodifiableList(bots);
        } catch (IOException ioe) {
            LOGGER.error("Couldn't get anything?!", ioe);
        }
        return Collections.emptyList();
    }

    public void updateGuilds(long id, int guilds, int shard) {
        if (guilds < 0) throw new IllegalArgumentException("Guilds < 0");
        try {
            FormBody.Builder builder = new FormBody.Builder().add("guilds", Integer.toString(guilds));
            if (shard >= 0) builder.add("shard", Integer.toString(shard));
            Response r = post(new URL("https://listcord.com/api/bot/" + Long.toUnsignedString(id) + "/guilds"), headers().build(), builder.build());
            if (!r.isSuccessful())
                LOGGER.warn("Couldn't post count for " + id + " (" + shard + ") Got " + r.code());
        } catch (IOException ioe) {
            LOGGER.error("");
        }
    }

    private Response get(URL url, Headers headers) throws IOException {
        return CLIENT.newCall(builder(url, headers).get().build()).execute();
    }

    private Response post(URL url, Headers headers, FormBody data) throws IOException {
        return CLIENT.newCall(builder(url, headers).post(data).build()).execute();
    }

    private Headers.Builder headers() {
        return new Headers.Builder().add("User-agent", USER_AGENT).add("Authorization", TOKEN).add("Content-Type", "application/json");
    }

    private Request.Builder builder(URL url, Headers headers) {
        return new Request.Builder().url(url).headers(headers);
    }

    protected void finalize() {
    }
}
