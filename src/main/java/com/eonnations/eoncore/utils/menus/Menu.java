package com.eonnations.eoncore.utils.menus;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Structure;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

public class Menu {
    private Menu() { }

    private record Info(Player viewer, String title, Structure structure, Map<Character, AbstractItem> items, List<Runnable> openHandler, List<Runnable> closeHandler) { }

    private static Window createGui(Info info) {
        Gui.Builder.Normal builder = Gui.normal()
                .setStructure(info.structure);
        for (Tuple2<Character, AbstractItem> item : info.items) {
            builder.addIngredient(item._1, item._2);
        }
        Gui gui = builder.build();
        return Window.single()
                .setViewer(info.viewer)
                .setTitle(info.title)
                .setGui(gui)
                .setOpenHandlers(info.openHandler.toJavaList())
                .setCloseHandlers(info.closeHandler.toJavaList())
                .build();
    }

    public static class Builder {
        private Player viewer;
        private String title;
        private Structure structure;
        private Map<Character, AbstractItem> items = HashMap.empty();
        private List<Runnable> openHandlers = List.empty();
        private List<Runnable> closeHandlers = List.empty();

        public static Builder builder() {
            return new Builder();
        }

        public Builder viewer(Player viewer) {
            this.viewer = viewer;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addStructure(String... lines) {
            this.structure = new Structure(lines);
            return this;
        }

        public Builder addStructure(List<String> lines) {
            this.structure = new Structure(lines.toJavaArray(String[]::new));
            return this;
        }

        public Builder addItem(Character key, AbstractItem item) {
            items = items.put(key, item);
            return this;
        }

        public Builder addOpenHandler(Runnable runnable) {
            openHandlers = openHandlers.append(runnable);
            return this;
        }

        public Builder addCloseHandler(Runnable runnable) {
            closeHandlers = closeHandlers.append(runnable);
            return this;
        }

        public Window build() {
            Info info = new Info(viewer, title, structure, items, openHandlers, closeHandlers);
            return createGui(info);
        }
    }
}
