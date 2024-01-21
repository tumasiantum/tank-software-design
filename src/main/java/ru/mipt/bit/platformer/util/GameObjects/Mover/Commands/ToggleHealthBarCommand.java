package ru.mipt.bit.platformer.util.GameObjects.Mover.Commands;

import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Tank;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

public class ToggleHealthBarCommand implements Command {
    private GraphicsController graphicsController;

    public ToggleHealthBarCommand(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }

    @Override
    public void execute() {
        this.graphicsController.toggleLiveBar();
    }
}