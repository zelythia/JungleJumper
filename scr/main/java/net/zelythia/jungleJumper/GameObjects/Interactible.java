package net.zelythia.jungleJumper.GameObjects;

public interface Interactible {
    void onInteraction(Player interactor);
    GameObject getGameObject();

}
