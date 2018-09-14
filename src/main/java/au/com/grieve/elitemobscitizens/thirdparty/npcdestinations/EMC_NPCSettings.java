package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import lombok.Getter;
import net.citizensnpcs.api.npc.NPC;
import net.livecar.nuttyworks.npc_destinations.citizens.NPCDestinationsTrait;
import org.bukkit.Location;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EMC_NPCSettings {

    @Getter private NPC npc;
    @Getter private Map<UUID, EMC_LocationSettings> locations;
    @Getter private Location currentDestination;
    @Getter private NPCDestinationsTrait destinationsTrait;
    @Getter private Date lastAction;

    public EMC_NPCSettings(NPC npc) {
        this.npc = npc;
        destinationsTrait = npc.getTrait(NPCDestinationsTrait.class);
        locations = new HashMap<>();
        lastAction = new Date();
    }

}
