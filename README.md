# MineLite
MineLite is a lightweight library used for interacting with Minecraft servers. <br>
With it you can build simple Minecraft chat bots or get status of a server.

# Fetures
- Server List Ping - ping a server to receive its data
- Minecraft Client - create custom Minecraft bots with (almost) full chat support, many pre-implemented packets (and many more to come), with minimum effort!

## Using in your project
### Maven
```xml
<dependency>
  <groupId>io.github.defective4</groupId>
  <artifactId>minelite-protocol-(your Minecraft version)</artifactId>
  <version>0.9</version>
</dependency>
```

### Gradle
```
 repositories {
 	maven {
 		url "https://s01.oss.sonatype.org/content/repositories/releases"
 	}
 }
 
 dependencies {
 	implementation "io.github.defective4:minelite-protocol-(your Minecraft version):0.9"
 }
```

Alternatively you can download binaries and javadoc from [Releases](https://github.com/Defective4/MineLite/releases) page.
Note that you will need both minelite-core-x.x.jar and minelite-protocol-(Minecraft version)-x.x.jar to create a custom client.

## Currently supported versions
- 1.18.2

## Examples
### Server List Ping
#### Code
```
try {
    StatusResponse response = MinecraftStat.ping("localhost", 25565);
    System.out.println("Description: " + response.getDescription());
    System.out.println("Players: " + response.getOnlinePlayers() + "/" + response.getMaxPlayers());
    System.out.println("Version: " + response.getVersionName() + "(" + response.getProtocolNumber() + ")");
} catch (Exception ex) {
    ex.printStackTrace();
}
```

#### Example Output
```
Description: A Minecraft Server
Players: 1/20
Version: Spigot 1.12.2 (340)
````

### Joining a server (1.18.2)
#### Code
```
String host = "localhost";
int port = 25565;
String username = "Username";
try (MinecraftClient client = new MinecraftClient(host, port, username, new Protocol_1_18_2())) {
    client.addClientListener(new ClientAdapter() {

    @Override
    public void messageReceived(Message message, int position, UUID sender) {
        System.out.println(message.getText());
    }

    @Override
    public void disconnected(Message message) {
        System.err.println("I couldn't connect: " + message.getText());
    }

    @Override
    public void joined(int entityID) {
        System.out.println("I have joined the server!");
        try {
           client.sendMessage("Hello World!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    });
    client.connect();
} catch (Exception ex) {
    ex.printStackTrace();
}
```

This code will join the specified server (localhost:25565) under nick "Username", say "Hello World!" after successfully joining and will stay there until you disconnect it. <br>
It will also log all received messages to standard output.

## Building
To build this library from source clone it using git and then run:
```
mvn clean install
```
in the minelite directory.

## TODO
- [ ] - Server List Ping icon and players sample support <br>
- [ ] - Messages click and hover events, and color + formatting support <br>
- [ ] - More Minecraft versions (priorities: 1.12.2, 1.16.5, 1.8) <br>
- [ ] - More packets (inventory, world, movement) <br>
