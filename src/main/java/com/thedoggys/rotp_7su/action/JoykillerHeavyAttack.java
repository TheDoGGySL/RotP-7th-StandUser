package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class JoykillerHeavyAttack extends StandEntityHeavyAttack {
    private final Supplier<StandEntityHeavyAttack> noAxeAttack;

    public JoykillerHeavyAttack(StandEntityHeavyAttack.Builder builder, Supplier<StandEntityHeavyAttack> noAxeAttack) {
        super(builder.addExtraUnlockable(noAxeAttack));
        this.noAxeAttack = noAxeAttack != null ? noAxeAttack : () -> null;
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof JoykillerEntity && !((JoykillerEntity) stand).hasAxe()) {
            return conditionMessage("joykiller_axe");
        }
        return super.checkStandConditions(stand, power, target);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        return power.isActive() && power.getStandManifestation() instanceof JoykillerEntity
                && !((JoykillerEntity) power.getStandManifestation()).hasAxe() && noAxeAttack.get() != null
                ? noAxeAttack.get() : super.replaceAction(power, target);
    }

//    @Override
//    public StandAction[] getExtraUnlockable() {
//        if (noRapierAttack.get() != null) {
//            return new StandAction[] { noRapierAttack.get() };
//        }
//        
//        return super.getExtraUnlockable();
//    }

    @Override
    public Stream<SoundEvent> getSounds(StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task) {
        return getPhaseStandSounds(phase, standEntity);
    }
}