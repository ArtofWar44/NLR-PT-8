package org.ArtofWar44;

public class MenuItem {
    private String name;
    private String size; // 12 or 20 ounce

    public MenuItem(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
