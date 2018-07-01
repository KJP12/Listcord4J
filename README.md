# Listcord4J
[![](https://jitpack.io/v/KJP12/Listcord4J.svg)](https://jitpack.io/#KJP12/Listcord4J) 
A library for https://listcord.com/developers/docs

<h2>Including it in your build</h2>
<h3>Maven (pom.xml)</h3>
Add the JitPack.io repository if you haven't already

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the Listcord4J dependency.

```xml
<dependencies>
    <dependency>
        <groupId>com.github.KJP12</groupId>
        <artifactId>Listcord4J</artifactId>
        <version>master-SNAPSHOT</version>
        <!--Optionally, you can replace branch-SNAPSHOT with a commit hash or a release-->
    </dependency>
</dependencies>
```

<h3>Gradle (build.gradle)</h3>
Add the JitPack.io repository if you haven't already

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
Add the Listcord4J dependency.

```groovy
dependencies {
    compile 'com.github.KJP12:Listcord4J:master-SNAPSHOT'
    //Optionally, you can replace branch-SNAPSHOT with a commit hash or a release
}
```

<h2>Examples</h2>
<h3>JDA</h3>
<h4>Java</h4>

```java
import net.kjp12.listcord4j.*;
import net.dv8tion.jda.bot.sharding.*;
import net.dv8tion.jda.core.*;

public class Example extends ListenerAdapter{
    public static Listcord4J listcord4J = new Listcord4JImpl("Insert Listcord Token Here" /*Token is required; authorized on every request*/);
    //This is a crudely initialized shard manager, please implement it better!
    public static ShardManager client = DefaultShardManagerBuilder().addEventListeners(new Main()).setShardsTotal(5).setShards(0, 4).setToken("Insert Discord Token Here").build();
    
    public void onGuildJoin(GuildJoinEvent event) {
        JDA jda = event.getJDA();
        ShardInfo shardInfo = jda.getShardInfo();
        listcord4J.updateGuilds(jda.getSelfUser().getId(), jda.getGuilds().size(), shardInfo == null ? 0 : shardInfo.getShardId());
    }
    public void onGuildLeave(GuildLeaveEvent event) {
        JDA jda = event.getJDA();
        ShardInfo shardInfo = jda.getShardInfo();
        listcord4J.updateGuilds(jda.getSelfUser().getId(), jda.getGuilds().size(), shardInfo == null ? 0 : shardInfo.getShardId());
    }
}
```
<h4>Groovy</h4>

```groovy
import net.kjp12.listcord4j.*
import net.dv8tion.jda.bot.sharding.*
import net.dv8tion.jda.core.*
import net.dv8tion.jda.core.JDA.ShardInfo

class Example extends ListenerAdapter{
    public static Listcord4J listcord4J = new Listcord4JImpl("Insert Listcord Token Here" /*Token is required; authorized on every request*/)
    //This is a crudely initialized shard manager, please implement it better!
    public static ShardManager client = DefaultShardManagerBuilder().addEventListeners(new Example()).setShardsTotal(5).setShards(0, 4).setToken("Insert Discord Token Here").build()
    
    //full java-like variant
    void onGuildJoin(GuildJoinEvent event) {
        JDA jda = event.getJDA()
        ShardInfo shardInfo = jda.getShardInfo()
        listcord4J.updateGuilds(jda.getSelfUser().getId(), jda.getGuilds().size(), shardInfo == null ? 0 : shardInfo.getShardId())
    }
    //compact variant
    void onGuildLeave(GuildLeaveEvent event) {
        JDA jda = event.JDA
        listcord4J.updateGuilds jda.selfUser.id, jda.guilds.size(), jda.shardInfo?.shardId ?: 0
    }
}
```