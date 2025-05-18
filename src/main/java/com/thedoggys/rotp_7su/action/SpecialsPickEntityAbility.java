package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitStands;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import com.thedoggys.rotp_7su.specials.SpecialsStandType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

// Возможно, тут стоит скопипастить интерфейс из Bad Company, абилку SelectFormation, например
public class SpecialsPickEntityAbility extends StandAction {

    public SpecialsPickEntityAbility(AbstractBuilder<?> arg0) {
        super(arg0);
    }
    
    public static int test = 0;
    
    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            test = (test + 1) % InitStands.SPECIALS_ENTITY_TYPES.size();
            user.sendMessage(new StringTextComponent(String.valueOf(test)), Util.NIL_UUID);
            
            SpecialsEntities specialsEntities = SpecialsStandType.getSpecialsEntities(power).orElse(null);
            
            if (specialsEntities == null) {
                specialsEntities = new SpecialsEntities();
                power.getContinuousEffects().addEffect(specialsEntities);
            }
            
            specialsEntities.pickEntity(test);
        }
    }

}
