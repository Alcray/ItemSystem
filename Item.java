public class Item {
    private String name;
    private Rarity rarity;
    private int epicUpgradeLevel;

    public Item(String name, Rarity rarity) {
        this.name = name.trim();
        this.rarity = rarity;
        this.epicUpgradeLevel = 0;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getEpicUpgradeLevel() {
        return epicUpgradeLevel;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public void setEpicUpgradeLevel(int epicUpgradeLevel) {
        this.epicUpgradeLevel = epicUpgradeLevel;
    }

    @Override
    public String toString() {
        if (rarity == Rarity.EPIC && epicUpgradeLevel > 0) {
            return String.format("%s (%s %d)", name, rarity, epicUpgradeLevel);
        } else {
            return String.format("%s (%s)", name, rarity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item other = (Item) o;
        return this.name.equalsIgnoreCase(other.name)
               && this.rarity == other.rarity
               && this.epicUpgradeLevel == other.epicUpgradeLevel;
    }

    @Override
    public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + rarity.hashCode();
        result = 31 * result + epicUpgradeLevel;
        return result;
    }
}
