package net.instantgratification.item_clumps;

public interface MegaCountData {
    int item_clumps$getMegaCount();
    void item_clumps$setMegaCount(int count);
    void item_clumps$addMegaCount(int count);
    void item_clumps$shrinkMegaCount(int count);
    boolean item_clumps$shouldRenderLabels();
}
