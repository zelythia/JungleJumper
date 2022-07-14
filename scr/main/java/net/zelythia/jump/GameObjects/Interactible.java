package net.zelythia.jump.GameObjects;

public interface Interactible {
    void onInteraction(Player interactor);
    GameObject getGameObject();

}
