package cn.sh1rocu.astralsorcery.util;

import net.minecraft.world.phys.AABB;

public class Constants {

    public static final AABB INFINITE_AABB = new AABB(
            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

}
