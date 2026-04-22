package com.healthclaimx.common.config;

import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ZoneConfig {
    
    public static final Set<String> VALID_ZONES = new HashSet<>(Arrays.asList(
        "NORTH",
        "SOUTH",
        "EAST",
        "WEST",
        "CENTRAL",
        "NORTHEAST",
        "NORTHWEST",
        "SOUTHEAST",
        "SOUTHWEST"
    ));
    
    public static boolean isValidZone(String zone) {
        return zone != null && VALID_ZONES.contains(zone.toUpperCase());
    }
}
