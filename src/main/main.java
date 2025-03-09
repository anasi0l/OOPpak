package main; // Оголошення пакету для основного класу програми

/*
Лабораторна робота 4

Формула: 12 % 4 + 1 = 1 → List (список).

1. Визначити власну систему класів Exception (мінімум 2) і реалізувати їх обробку.
2. Створити консольне меню для роботи з класами з можливістю збереження та
   зчитування інформації у файл (через механізм серіалізації).
3. Використовувати колекцію List для збереження множини об'єктів.
*/

// Імпорт необхідних класів
import service.MobileCompany; // Клас для управління тарифами мобільного оператора
import factory.TariffFactory; // Фабрика для створення тарифів
import exception.DuplicateTariffException; // Виняток для дублювання тарифів
import exception.InvalidTariffException; // Виняток для некоректних тарифів
import exception.EmptyTariffListException; // Виняток для операцій над порожнім списком тарифів

// Основний клас програми
public class main {

    public static void main(String[] args) { // Головний метод програми
        MobileCompany company = new MobileCompany(); // Створення компанії для роботи з тарифами

        // Додавання тарифів через фабрику
        try {
            company.addTariff(TariffFactory.createTariff("prepaid", "Basic", 10.0, 1000)); // Тариф передплати
            company.addTariff(TariffFactory.createTariff("postpaid", "Standard", 20.0, 500)); // Контрактний тариф
            company.addTariff(TariffFactory.createTariff("student", "Student", 5.0, 800)); // Студентський тариф
            company.addTariff(TariffFactory.createTariff("business", "Business", 50.0, 200)); // Бізнес-тариф
        } catch (InvalidTariffException | DuplicateTariffException e) {
            // Обробка помилок некоректного або дубльованого тарифу
            System.out.println("Помилка при створенні тарифу: " + e.getMessage());
        }

        // Виведення загальної кількості клієнтів у компанії
        try {
            System.out.println("Total clients: " + company.getTotalClients()); // Підрахунок клієнтів
        } catch (EmptyTariffListException e) {
            // Обробка помилки, якщо список тарифів порожній
            System.out.println("Помилка при підрахунку клієнтів: " + e.getMessage());
        }

        // Виведення відсортованих тарифів
        System.out.println("\nSorted tariffs by monthly fee:");
        try {
            company.sortTariffs(); // Виклик методу сортування тарифів
            company.printTariffs(); // Виведення списку тарифів
        } catch (EmptyTariffListException e) {
            // Обробка помилки, якщо список тарифів порожній
            System.out.println("Помилка при сортуванні або виведенні тарифів: " + e.getMessage());
        }

        // Виведення тарифів у заданому діапазоні цін
        System.out.println("\nTariffs in range 5-20 USD:");
        try {
            company.findTariffsInRange(5, 20).forEach(System.out::println); // Пошук та виведення тарифів у діапазоні
        } catch (EmptyTariffListException e) {
            // Обробка помилки, якщо список тарифів порожній
            System.out.println("Помилка при пошуку тарифів: " + e.getMessage());
        }

        // Створення та запуск консольного меню
        Menu menu = new Menu(company);
        menu.showMenu(); // Відображення меню для взаємодії з користувачем
    }
}
