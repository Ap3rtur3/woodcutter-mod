# Woodcutter Mod

Enables stonecutter to provide recipes for wooden stairs, planks, slabs, etc.
Also has some QOL features listed below.

Useful commands:
* `./gradlew tasks` to see available commands.
* `./gradlew runDatagen` to generate recipes.
* `./gradlew build` to build project.

Useful Docs:
* https://fabricmc.net/develop/
* https://wiki.fabricmc.net/tutorial:migratemappings

## Features

* Woodcutter recipes
* Coordinates on screen without F3 or Xaeros minimap

#### Planned

Some ideas for future features, may not be implemented.

* Ruler HUD to display distance between blocks and other helpers for building
* More recipes, needs opt-in config
  * Reinforced Deepslate

## Setup

https://docs.fabricmc.net/develop/getting-started/introduction-to-fabric-and-modding
https://docs.fabricmc.net/develop/getting-started/setting-up-a-development-environment

TODO: Better documentation, not just rtfm. List requirements and install guide.

## Updating

1. Edit `gradle/wrapper/gradle-wrapper.properties` and update [gradle version](https://services.gradle.org/distributions/).

    ```properties
    # Replace 8.14.2 with latest stable version
    distributionUrl=https\://services.gradle.org/distributions/gradle-8.14.2-bin.zip
    ```

2. Migrate mappings to latest version, refer to the [docs](https://fabricmc.net/develop/). 
    This creates a folder named `remappedSrc`, which contains source files that adhere to core/API changes.
    Copy them and overrwite the original source files.
    ```bash
    # Replace 1.21.8+build.1 with latest recommended version
    ./gradlew migrateMappings --mappings "1.21.8+build.1"
    ```

3. Edit `gradle.properties` and copy-paste the properties from the [docs](https://fabricmc.net/develop/).

    ```properties
    minecraft_version=1.21.8
    yarn_mappings=1.21.8+build.1
    loader_version=0.17.2
    loom_version=1.11-SNAPSHOT
    
    # Fabric API
    fabric_version=0.133.0+1.21.8
    ```

4. You should be able to build the project now :)

   ```bash
   ./gradlew build
   ```

