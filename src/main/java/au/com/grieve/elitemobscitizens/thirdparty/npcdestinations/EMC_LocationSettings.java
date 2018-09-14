package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EMC_LocationSettings {
    private UUID locationId;

    private Date lastSet = new Date();

    private int minSize = 54;
    private int maxSize = 54;
    private int minTier = 0;
    private int maxTier = 100;
    private long updateTicks = 6000;
    private boolean enabled = true;
}
