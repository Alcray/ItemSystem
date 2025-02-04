
---

# Item Upgrade System

A simple Java console application demonstrating an **Item Upgrade System** with:
- **Rarities**: `COMMON`, `GREAT`, `RARE`, `EPIC`, `LEGENDARY`.
- **Upgrade Mechanics** for combining multiple items of the same type and rarity to form a higher-tier item.
- Special handling for `EPIC` items (`Epic → Epic1 → Epic2 → Legendary`).
- **Random Item Generation** with weighted probabilities.
- **Saving** and **loading** inventory to and from JSON format using **Gson**.

## Features

1. **Item Creation**: Manually create items with a specified name and rarity.  
2. **Inventory Management**: Add, remove, and display items grouped by rarity.  
3. **Upgrade System**:  
   - Common → Great → Rare → Epic → Epic1 → Epic2 → Legendary  
   - Combines items (3 or 2, depending on the step) into a single upgraded item.  
4. **Random Item Generation**:  
   - Weighted probabilities (50% Common, 25% Great, 15% Rare, 8% Epic, 2% Legendary).  
5. **Save/Load** using JSON (requires **Gson**).


## How to Compile and Run with `run.sh`

**Run the script**:
   ```bash
   ./run.sh
   ```
   This script:
   - Compiles all `.java` files, using `javac`.
   - If compilation succeeds, runs the `ItemSystem` main class via `java`.

If you encounter any errors indicating that the Gson package cannot be found, verify that:
- You have the correct jar file name in `run.sh`.
- The jar file exists in the same folder.
- The classpath in the script uses the correct path separator (colon `:` for macOS/Linux, semicolon `;` for Windows, though the script auto-detects this for basic cases).

## Usage

Once the program starts, you’ll see a menu with options to:

1. **Create a new item**  
2. **Display inventory**  
3. **Upgrade items**  
4. **Generate a random item**  
5. **Save inventory to JSON file**  
6. **Load inventory from JSON file**  
7. **Quit**  

Follow the prompts to interact with the system.

## Edge Cases

- **Insufficient items** for upgrades (e.g., fewer than 3 Common items).
- **Empty item name** checks.
- **Invalid menu selections** prompt a retry or an error message.

## Data Structures and Algorithms

- **ArrayList\<Item\>** for storing the inventory with memory complexity O(n).  
- **Java Streams** for filtering items by name, rarity, or epic level.  
- **Linear searches** (Each search and filter complexity is O(n) given small amount of item best memory/speed/eazy implementation balance).  
- **Random generation** uses a simple threshold-based approach (50%/25%/15%/8%/2%).

---

**Enjoy and have fun crafting your way to glory!**