package MapEditor.Editor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyLayout1 extends LayoutAdapter {

    List<Component> components = new ArrayList<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        components.add(comp);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        components.remove(comp);
    }

    @Override
    public void layoutContainer(Container parent) {
        int width = parent.getWidth();
        int height = parent.getHeight();
        int x = 0;
        int y = 0;
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            c.setBounds(x, y, 48, 48);
            x += 48;
            if (x >= width) {
                x = 0;
                y += 48;
            }
        }
    }
}
