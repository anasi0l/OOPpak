package exception; // Пакет для виняткових ситуацій, пов'язаних із тарифами.

/**
 * Виняток EmptyTariffListException виникає, коли виконується операція над порожнім списком тарифів.
 * Це перевірений (checked) виняток, оскільки наслідується від {@link Exception}.
 */
public class EmptyTariffListException extends Exception {

    /**
     * Конструктор, який приймає повідомлення про помилку.
     *
     * @param message Опис проблеми, що сталася.
     */
    public EmptyTariffListException(String message) {
        super(message); // Передача повідомлення до суперкласу Exception.
    }
}
