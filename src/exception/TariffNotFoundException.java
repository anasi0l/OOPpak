package exception; // Пакет для винятків, пов'язаних із тарифами.

/**
 * Виникає, коли тариф не знайдено.
 */
public class TariffNotFoundException extends Exception {

    /**
     * Конструктор із повідомленням про помилку.
     */
    public TariffNotFoundException(String message) {
        super(message);
    }
}
