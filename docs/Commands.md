# Commands

Format of the command is:

    /emcs {subcommand}

An NPC must be selected using the citizen command

    /npc sel

## Help
Format: `/emcs help`

Lists the available commands

## Shopkeeper Info
Format: `/emcs info`

Shows information about the selected shopkeeper

Example Output:
```
=== [ EMC Shopkeeper Info ] ===
Shop Name: The Merchant Venturer
Shop Size: 54 - 54
Tier Range: 0 - 10
Update Interval (ticks): 120000
Enabled: True
```

## Set Shop Size Range
Format: `/emcs size <min> <max>`

Sets the minimum and maximum size of the shop. It will be picked randomly from between those numbers inclusive.

## Set Show Tier Range
Format: `/emcs tier <min> <max>`

Sets the minimum and maximum tier of the shop. It will be picked randomly from between those numbers inclusive.

## Enable shop
Format: `/emcs enable <true|false>`

Open or close the shop.

## Update Interval
Format: `/emcs update_ticks <ticks>`

The number of ticks before the shop is refreshed. This allows a shopkeeper to only rarely actually update the shop.


