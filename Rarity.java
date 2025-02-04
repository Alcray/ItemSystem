public enum Rarity {
    COMMON,
    GREAT,
    RARE,
    EPIC,
    LEGENDARY;

    public Rarity getNextRarity() {
        switch (this) {
            case COMMON:
                return GREAT;
            case GREAT:
                return RARE;
            case RARE:
                return EPIC; 
            default:
                return null; // EPIC or LEGENDARY doesn't have a direct "next" via this method
        }
    }
}
