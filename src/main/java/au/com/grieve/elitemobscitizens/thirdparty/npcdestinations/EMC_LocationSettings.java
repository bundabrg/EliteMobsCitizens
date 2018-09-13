package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EMC_LocationSettings {
    public UUID locationId;

    public Date lasteSet = new Date();

    public int minSize = 54;
    public int maxSize = 54;
    public int minTier = 0;
    public int maxTier = 100;
    public long updateTicks = 6000;
    public boolean enabled = true;
}
