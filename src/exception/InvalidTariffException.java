package exception; // Пакет для винятків, пов'язаних із тарифами.

/**
 * Виникає при спробі створення або використання некоректного тарифу.
 */
public class InvalidTariffException extends Exception {

    /**
     * Конструктор із повідомленням про помилку.
     */
    public InvalidTariffException(String message) {
        super(message);
    }
}
