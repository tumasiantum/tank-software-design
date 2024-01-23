package ru.mipt.bit.platformer.util.GameEngine.Commands;

import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

public class ToggleHealthBarCommand implements Command {
    private final GraphicsController graphicsController;

    public ToggleHealthBarCommand(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }

    @Override
    public void execute() {
        this.graphicsController.toggleLiveBar();
    }
}