package log.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public final class SwingUtils {
    public static CompoundBorder getPanelBorder() {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        return BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    }
}
