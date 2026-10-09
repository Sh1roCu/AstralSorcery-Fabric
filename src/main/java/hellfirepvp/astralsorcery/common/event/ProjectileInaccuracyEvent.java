/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event;

import cn.sh1rocu.astralsorcery.api.event.EntityEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ProjectileInaccuracyEvent
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ProjectileInaccuracyEvent extends EntityEvent {

    private final Projectile projectile;
    private float inaccuracy;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public ProjectileInaccuracyEvent(Projectile projectile, float inaccuracy) {
        super(projectile);
        this.projectile = projectile;
        this.inaccuracy = inaccuracy;
    }

    public final Projectile getProjectile() {
        return projectile;
    }

    public float getInaccuracy() {
        return this.inaccuracy;
    }

    public void setInaccuracy(float inaccuracy) {
        this.inaccuracy = inaccuracy;
    }

    public interface Callback {
        void post(ProjectileInaccuracyEvent event);
    }
}
