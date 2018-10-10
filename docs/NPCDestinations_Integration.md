# NPC Destinations Integration

[Download NPC Destionations](https://www.spigotmc.org/resources/nunpcdestinations-create-living-npcs-1-8-3-1-12.13863/)

## Enable Integration

Enable integration by:
1. Select the NPC in citizens
2. Configure NPC for npc destinations
3. Type `/nd info` and click `emcs` to enable
4. Now hover over the 'ṁ' for documentation on configuration items.

## Customization

You can customize the following attributes of a shop based upon the NPC Destinations options of time/weather/chance etc

* If shop is opened or closed. Have your shopkeeper close up at night.
* The size of the shop
* The tier range of the shop. Have your shopkeeper open a higher tier shop at another location.
* How often the shop updates


## Commands
Customization is by setting the shopkeeper up how you like and then saving those settings for a specific NPCDestination location. Any locations without a setting configured will use the last configured settings.

### Set NPC Dest Settings
Format: `/nd locemcs <loc#> set`

Save existing settings to the specific location

### Clear NPC Dest SEttings
Format: `/nd locemcs <loc#> clear`

Clear all emcs settings from the specific location

