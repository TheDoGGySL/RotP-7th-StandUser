package com.thedoggys.rotp_7su.client.ui.screen.specials;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum FormationType {
    FIRST(new TranslationTextComponent("gui.rotp_7su.first"), 0),
    SECOND(new TranslationTextComponent("gui.rotp_7su.second"),  1),
    THIRD(new TranslationTextComponent("gui.rotp_7su.third"),  2),
    FOURTH(new TranslationTextComponent("gui.rotp_7su.fourth"), 3),
    FIFTH(new TranslationTextComponent("gui.rotp_7su.fifth"),  4),
    SIXTH(new TranslationTextComponent("gui.rotp_7su.sixth"),  5),


    ;
    public static final FormationType[] VALUES = values();
    final ITextComponent name;
    final int formationType;
    public void drawIcon(FormationType type, int x, int y) {
        FormationIcon.renderIcon(type, new MatrixStack(), x, y);
    }

    FormationType(ITextComponent name, int FormationType){
        this.name = name;
        this.formationType = FormationType;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public int getFormationType() {
        return this.formationType;
    }
    public static FormationType getByFormationType(int FormationType){
        List<FormationType> types = Arrays.stream(values()).filter(type -> type.getFormationType() == FormationType).collect(Collectors.toList());
        Optional<FormationType> matchType = types.stream().findFirst();
        System.out.println(types.stream().count());
        return matchType.orElse(null);
    }

    public static Optional<FormationType> getFromFormationType(FormationType type) {
        if (type == null) {
            return Optional.empty();
        }

        switch (type) {
            case SECOND:
                return Optional.of(SECOND);
            case THIRD:
                return Optional.of(THIRD);
            case FOURTH:
                return Optional.of(FOURTH);
            case FIFTH:
                return Optional.of(FIFTH);
            case SIXTH:
                return Optional.of(SIXTH);
            default:
                return Optional.of(FIRST);
        }
    }
}