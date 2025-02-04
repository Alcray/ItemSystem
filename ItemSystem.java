import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ItemSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Welcome to the Item Upgrade System!");

        boolean quit = false;
        while (!quit) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createNewItem();
                    break;
                case "2":
                    inventory.displayInventory();
                    break;
                case "3":
                    performUpgrade();
                    break;
                case "4":
                    generateRandomItem();
                    break;
                case "5":
                    saveInventoryToJson();
                    break;
                case "6":
                    loadInventoryFromJson();
                    break;
                case "7":
                    quit = true;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n============ Main Menu ============");
        System.out.println("1. Create a new item");
        System.out.println("2. Display inventory");
        System.out.println("3. Upgrade items");
        System.out.println("4. Generate random item");
        System.out.println("5. Save inventory to JSON file");
        System.out.println("6. Load inventory from JSON file");
        System.out.println("7. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void createNewItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Item name cannot be empty.");
            return;
        }

        System.out.println("Select rarity:");
        System.out.println("1. COMMON");
        System.out.println("2. GREAT");
        System.out.println("3. RARE");
        System.out.println("4. EPIC");
        System.out.println("5. LEGENDARY");
        System.out.print("Enter your choice: ");
        String rarityChoice = scanner.nextLine().trim();

        Rarity rarity = null;
        switch (rarityChoice) {
            case "1": rarity = Rarity.COMMON;     break;
            case "2": rarity = Rarity.GREAT;      break;
            case "3": rarity = Rarity.RARE;       break;
            case "4": rarity = Rarity.EPIC;       break;
            case "5": rarity = Rarity.LEGENDARY;  break;
            default:
                System.out.println("Invalid rarity selection.");
                return;
        }

        Item newItem = new Item(name, rarity);
        inventory.addItem(newItem);
        System.out.println("Created item: " + newItem);
    }

    private static void performUpgrade() {
        System.out.println("\n===== Upgrade Menu =====");
        System.out.println("1. Upgrade Common to Great   (requires 3 of the same Common)");
        System.out.println("2. Upgrade Great to Rare     (requires 3 of the same Great)");
        System.out.println("3. Upgrade Rare to Epic      (requires 3 of the same Rare)");
        System.out.println("4. Upgrade Epic to Epic1     (requires 1 additional Epic item)");
        System.out.println("5. Upgrade Epic1 to Epic2    (requires 1 additional Epic item)");
        System.out.println("6. Upgrade Epic2 to Legendary (requires 3 of the same Epic2)");
        System.out.print("Enter your choice: ");
        String upgradeChoice = scanner.nextLine().trim();

        System.out.print("Enter the item name to upgrade: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Item name cannot be empty.");
            return;
        }

        boolean success = false;
        switch (upgradeChoice) {
            case "1":
                success = inventory.upgradeNonEpic(name, Rarity.COMMON, Rarity.GREAT);
                break;
            case "2":
                success = inventory.upgradeNonEpic(name, Rarity.GREAT, Rarity.RARE);
                break;
            case "3":
                success = inventory.upgradeNonEpic(name, Rarity.RARE, Rarity.EPIC);
                break;
            case "4":
                success = inventory.upgradeEpicToEpic1(name);
                break;
            case "5":
                success = inventory.upgradeEpic1ToEpic2(name);
                break;
            case "6":
                success = inventory.upgradeEpic2ToLegendary(name);
                break;
            default:
                System.out.println("Invalid upgrade option.");
        }

        if (success) {
            System.out.println("Upgrade completed successfully!");
        }
    }

    /*
     Generates a random item with weighted rarities:
     - Common:    50%
     - Great:     25%
     - Rare:      15%
     - Epic:      8%
     - Legendary: 2%
     */
    private static void generateRandomItem() {
        System.out.print("Enter the base name for the random item (e.g. 'Sword'): ");
        String baseName = scanner.nextLine().trim();
        if (baseName.isEmpty()) {
            System.out.println("Error: Item name cannot be empty.");
            return;
        }

        double roll = random.nextDouble() * 100.0; // from 0.0 to 100.0
        Rarity rarity;
        if      (roll < 50)  rarity = Rarity.COMMON;    // 50%
        else if (roll < 75)  rarity = Rarity.GREAT;     // 25%
        else if (roll < 90)  rarity = Rarity.RARE;      // 15%
        else if (roll < 98)  rarity = Rarity.EPIC;      // 8%
        else                 rarity = Rarity.LEGENDARY; // 2%

        Item randomItem = new Item(baseName, rarity);
        inventory.addItem(randomItem);
        System.out.println("Generated random item: " + randomItem);
    }

    private static void saveInventoryToJson() {
        System.out.print("Enter the file name to save (e.g. inventory.json): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Error: Invalid file name.");
            return;
        }

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(inventory.getItems(), writer);
            System.out.println("Inventory saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void loadInventoryFromJson() {
        System.out.print("Enter the file name to load (e.g. inventory.json): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Error: Invalid file name.");
            return;
        }

        try (FileReader reader = new FileReader(filename)) {
            Item[] loadedItems = gson.fromJson(reader, Item[].class);
            if (loadedItems == null) {
                System.out.println("Error: No valid items found in JSON.");
                return;
            }
            inventory.setItems(new ArrayList<>(Arrays.asList(loadedItems)));
            System.out.println("Inventory loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}
