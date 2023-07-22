# Password Once

## Forked from DevMuffin's password-plugin

[Repo](https://github.com/DevMuffin/password-plugin)
[Spigot page](https://www.spigotmc.org/resources/password-plugin.107267/)

## Changes from DevMuffin's plugin

When the player enters the correct password they get added to the Whitelist.
Players in the whitelist are automatically authorised to play on the server.

## Build

Make sure to have a JDK installed and the Maven application.
This was tested with Open JDK 19.0.2 and Apache Maven 3.9.3.

To build:

```bash
mvn clean
mvn package
```

Then copy the jar inside the `/target` folder into the `plugins` folder in the spigot installation.
