package factory; // Оголошення пакета factory, який містить класи для створення тарифів.

import model.*; // Імпорт усіх класів із пакета model, що містить реалізації тарифів.
import exception.InvalidTariffException; // Імпорт винятку, який кидається при неправильному типі тарифу.

/**
 * Фабричний клас для створення об'єктів тарифів різних типів.
 */
public class TariffFactory {

    /**
     * Створює тариф відповідного типу.
     *
     * @param type    Тип тарифу (prepaid, postpaid, student, business).
     * @param name    Назва тарифу.
     * @param fee     Абонентська плата.
     * @param clients Кількість клієнтів.
     * @return Об'єкт відповідного тарифу.
     * @throws InvalidTariffException Якщо переданий тип тарифу не підтримується.
     */
    public static Tariff createTariff(String type, String name, double fee, int clients) throws InvalidTariffException {
        return switch (type.toLowerCase()) { // Перетворюємо тип тарифу в нижній регістр для порівняння.
            case "prepaid" -> new PrepaidTariff(name, fee, clients); // Якщо тип "prepaid", створюємо PrepaidTariff.
            case "postpaid" -> new PostpaidTariff(name, fee, clients); // Якщо "postpaid", створюємо PostpaidTariff.
            case "student" -> new StudentTariff(name, fee, clients); // Якщо "student", створюємо StudentTariff.
            case "business" -> new BusinessTariff(name, fee, clients); // Якщо "business", створюємо BusinessTariff.
            default -> throw new InvalidTariffException("Невідомий тип тарифу: " + type);
            // Якщо переданий тип не відповідає жодному з перерахованих, кидаємо виняток.
        };
    }
}
