import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {

    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (item == null || item.getName().isEmpty() || item.getRarity() == null) {
            System.out.println("Error: Invalid item cannot be added to inventory.");
            return;
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void displayInventory() {
        if (items.isEmpty()) {
            System.out.println("\nInventory is empty.");
            return;
        }
        System.out.println("\n=== Current Inventory ===");
        for (Rarity rarity : Rarity.values()) {
            List<Item> group = items.stream()
                                    .filter(i -> i.getRarity() == rarity)
                                    .collect(Collectors.toList());
            if (!group.isEmpty()) {
                System.out.println("\n" + rarity + ":");
                if (rarity == Rarity.EPIC) {
                    group.sort((a, b) -> Integer.compare(a.getEpicUpgradeLevel(), b.getEpicUpgradeLevel()));
                }
                for (Item item : group) {
                    System.out.println("  - " + item);
                }
            }
        }
    }


    private List<Item> getItemsByNameAndRarity(String name, Rarity rarity) {
        return items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name) && i.getRarity() == rarity)
                .collect(Collectors.toList());
    }

    private List<Item> getEpicItemsByName(String name, Integer epicLevelFilter) {
        return items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name) && i.getRarity() == Rarity.EPIC)
                .filter(i -> epicLevelFilter == null || i.getEpicUpgradeLevel() == epicLevelFilter)
                .collect(Collectors.toList());
    }

    public boolean upgradeNonEpic(String name, Rarity currentRarity, Rarity nextRarity) {
        List<Item> available = getItemsByNameAndRarity(name, currentRarity);
        if (available.size() < 3) {
            System.out.printf("Error: Not enough %s '%s' items to upgrade. (Need 3, have %d)%n",
                    currentRarity, name, available.size());
            return false;
        }
        for (int i = 0; i < 3; i++) {
            removeItem(available.get(i));
        }
        Item upgraded = new Item(name, nextRarity);
        addItem(upgraded);

        System.out.printf("Upgraded three %s '%s' items into one %s '%s'.%n",
                currentRarity, name, nextRarity, name);
        return true;
    }

    public boolean upgradeEpicToEpic1(String name) {
        List<Item> baseEpics = getEpicItemsByName(name, 0);
        if (baseEpics.isEmpty()) {
            System.out.println("Error: No base EPIC '" + name + "' found to upgrade.");
            return false;
        }
        Item target = baseEpics.get(0);

        List<Item> allEpics = getEpicItemsByName(name, null);
        allEpics.remove(target);

        if (allEpics.isEmpty()) {
            System.out.println("Error: Not enough EPIC items to upgrade '" + name + "' to Epic1.");
            return false;
        }

        Item ingredient = allEpics.get(0);
        removeItem(ingredient);

        target.setEpicUpgradeLevel(1);
        System.out.println("Upgraded EPIC '" + name + "' to EPIC 1 '" + name + "'.");
        return true;
    }

    public boolean upgradeEpic1ToEpic2(String name) {
        List<Item> epic1Items = getEpicItemsByName(name, 1);
        if (epic1Items.isEmpty()) {
            System.out.println("Error: No Epic1 '" + name + "' found to upgrade.");
            return false;
        }
        Item target = epic1Items.get(0);

        List<Item> allEpics = getEpicItemsByName(name, null);
        allEpics.remove(target);

        if (allEpics.isEmpty()) {
            System.out.println("Error: Not enough EPIC items to upgrade '" + name + "' from Epic1 to Epic2.");
            return false;
        }

        Item ingredient = allEpics.get(0);
        removeItem(ingredient);

        target.setEpicUpgradeLevel(2);
        System.out.println("Upgraded EPIC1 '" + name + "' to EPIC2 '" + name + "'.");
        return true;
    }

    public boolean upgradeEpic2ToLegendary(String name) {
        List<Item> epic2Items = getEpicItemsByName(name, 2);
        if (epic2Items.size() < 3) {
            System.out.printf("Error: Not enough Epic2 '%s' items to create a LEGENDARY item. (Need 3, have %d)%n",
                    name, epic2Items.size());
            return false;
        }
        for (int i = 0; i < 3; i++) {
            removeItem(epic2Items.get(i));
        }
        Item legendary = new Item(name, Rarity.LEGENDARY);
        addItem(legendary);
        System.out.printf("Upgraded three Epic2 '%s' items into one LEGENDARY '%s'.%n", name, name);
        return true;
    }
}
