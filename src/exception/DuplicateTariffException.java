package exception; // Оголошення пакета exception, що містить виняткові ситуації для роботи з тарифами.

/**
 * Виняток DuplicateTariffException використовується для обробки ситуації,
 * коли спроба додати новий тариф призводить до дублювання вже існуючого тарифу.
 * Успадковує клас {@link Exception}, що робить його перевіреним винятком (checked exception).
 * Це означає, що виклик цього винятку вимагає явного оброблення (try-catch) або декларації в сигнатурі методу (throws).
 */
public class DuplicateTariffException extends Exception {

    /**
     * Конструктор винятку, який приймає інформацію про вже існуючий тариф.
     *
     * @param existingTariffName Назва тарифу, який вже існує.
     * @param existingFee        Абонентська плата за тарифом.
     * @param existingClients    Кількість клієнтів, що вже використовують цей тариф.
     */
    public DuplicateTariffException(String existingTariffName, double existingFee, int existingClients) {
        // Виклик конструктора суперкласу Exception із деталізованим повідомленням про помилку.
        super("Тариф із назвою '" + existingTariffName + "' уже існує (абонплата: "
                + existingFee + " USD, клієнтів: " + existingClients + ").");
    }
}
