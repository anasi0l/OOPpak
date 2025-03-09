package main;

import model.Tariff; // Імпорт класу Tariff з пакету model, який представляє тарифний план
import service.MobileCompany; // Імпорт класу MobileCompany, що керує тарифами оператора
import factory.TariffFactory; // Імпорт фабрики TariffFactory, що створює екземпляри тарифів

// Імпорт власних винятків (Exception), що обробляють різні ситуації в роботі з тарифами
import exception.TariffNotFoundException; // Виняток для випадку, коли тариф не знайдено
import exception.DuplicateTariffException; // Виняток при спробі додати дубльований тариф
import exception.EmptyTariffListException; // Виняток, якщо список тарифів порожній
import exception.InvalidTariffException; // Виняток при створенні некоректного тарифу

import java.util.Scanner; // Імпорт класу Scanner для зчитування введених користувачем даних
import java.io.IOException; // Імпорт класу IOException для обробки помилок вводу/виводу

// Імпорт логера для запису повідомлень про роботу програми
import java.util.logging.Logger; // Основний клас для логування
import java.util.logging.ConsoleHandler; // Обробник логування для виводу в консоль
import java.util.logging.Level; // Рівні важливості повідомлень логування

import java.util.InputMismatchException; // Виняток для обробки некоректного вводу користувачем
import java.util.List; // Імпорт колекції List для роботи зі списками тарифів
import java.io.File; // Імпорт класу File для роботи з файлами (наприклад, збереження тарифів)

public class Menu {
    private final MobileCompany company; // Об'єкт компанії, що містить всі тарифи
    private Scanner scanner = new Scanner(System.in); // Сканер для зчитування введених користувачем даних
    private static final Logger logger = Logger.getLogger(Menu.class.getName()); // Логер для ведення журналу подій

    static {
        ConsoleHandler handler = new ConsoleHandler(); // Обробник логування для виводу повідомлень у консоль
        handler.setLevel(Level.ALL); // Встановлення рівня логування на всі події
        logger.addHandler(handler); // Додавання обробника до логера
        logger.setLevel(Level.ALL); // Встановлення рівня логування для логера
    }

    public Menu(MobileCompany company) {
        this.company = company; // Ініціалізація об'єкта компанії
    }

    public void showMenu() { // Метод для відображення головного меню
        while (true) {
            // Вивід меню з кольоровим оформленням
            System.out.println("\u001B[34m\uD83C\uDF1F Головне меню\u001B[0m");
            System.out.println("\u001B[32m1. ➕ Додати тариф\u001B[0m");
            System.out.println("\u001B[33m2. 📄 Вивести всі тарифи\u001B[0m");
            System.out.println("\u001B[36m3. 🔍 Шукати тариф\u001B[0m");
            System.out.println("\u001B[31m4. ❌ Видалити тариф\u001B[0m");
            System.out.println("\u001B[35m5. 💾 Зберегти тарифи у файл\u001B[0m");
            System.out.println("\u001B[37m6. 📂 Завантажити тарифи з файлу\u001B[0m");
            System.out.println("\u001B[91m0. 🚪 Вихід\u001B[0m");
            System.out.print("\u001B[34mВаш вибір: \u001B[0m");

            int choice;
            try {
                choice = scanner.nextInt(); // Зчитування вибору користувача
                scanner.nextLine(); // Очищення буфера після введення числа
            } catch (InputMismatchException e) {
                // Обробка помилки, якщо користувач ввів не ціле число
                System.out.println("\u001B[91m❌ Помилка: введіть ціле число (наприклад, 1)!\u001B[0m");
                scanner.nextLine(); // Очищення буфера після помилки
                continue; // Пропуск ітерації циклу
            }

            switch (choice) {
                case 1 -> addTariff(); // Виклик методу додавання тарифу
                case 2 -> {
                    try {
                        company.printTariffs(); // Виклик методу для виведення всіх тарифів
                    } catch (EmptyTariffListException e) {
                        // Обробка винятку, якщо список тарифів порожній
                        System.out.println("\u001B[91m❌ Помилка: " + e.getMessage() + "\u001B[0m");
                    }
                }
                case 3 -> searchTariff(); // Виклик методу пошуку тарифу
                case 4 -> deleteTariff(); // Виклик методу видалення тарифу
                case 5 -> saveTariffs(); // Виклик методу збереження тарифів у файл
                case 6 -> loadTariffs(); // Виклик методу завантаження тарифів з файлу
                case 0 -> {
                    // Завершення роботи програми
                    System.out.println("\u001B[92m👋 До побачення!\u001B[0m");
                    System.exit(0);
                }
                default ->
                        System.out.println("\u001B[91m❗ Невірний вибір. Спробуйте ще раз.\u001B[0m"); // Повідомлення про некоректний вибір
            }
        }
    }

    private void addTariff() {
        // Запитуємо у користувача назву тарифу
        System.out.print("\u001B[36mВведіть назву тарифу: \u001B[0m");
        String name = scanner.nextLine();

        // Запитуємо у користувача тип тарифу (prepaid, postpaid, student, business)
        System.out.print("\u001B[36mВведіть тип тарифу (prepaid/postpaid/student/business): \u001B[0m");
        String type = scanner.nextLine();

        // Запитуємо у користувача щомісячну плату та обробляємо можливі помилки введення
        System.out.print("\u001B[36mВведіть щомісячну плату: \u001B[0m");
        double fee;
        try {
            fee = scanner.nextDouble();

            // Перевірка на від’ємну плату
            if (fee < 0) {
                System.out.println("\u001B[91m❌ Помилка: щомісячна плата не може бути від’ємною!\u001B[0m");
                scanner.nextLine(); // Очищуємо буфер після введення
                return;
            }
        } catch (InputMismatchException e) {
            // Обробка помилки, якщо введено не числове значення
            System.out.println("\u001B[91m❌ Помилка: введіть числове значення для плати (наприклад, 10.50)!\u001B[0m");
            scanner.nextLine(); // Очищуємо буфер після введення
            return;
        }

        // Запитуємо у користувача кількість клієнтів та обробляємо можливі помилки введення
        System.out.print("\u001B[36mВведіть кількість клієнтів: \u001B[0m");
        int clients;
        try {
            clients = scanner.nextInt();

            // Перевірка на від’ємну кількість клієнтів
            if (clients < 0) {
                System.out.println("\u001B[91m❌ Помилка: кількість клієнтів не може бути від’ємною!\u001B[0m");
                scanner.nextLine(); // Очищуємо буфер після введення
                return;
            }
        } catch (InputMismatchException e) {
            // Обробка помилки, якщо введено не ціле число
            System.out.println("\u001B[91m❌ Помилка: введіть ціле число для кількості клієнтів (наприклад, 100)!\u001B[0m");
            scanner.nextLine(); // Очищуємо буфер після введення
            return;
        }
        scanner.nextLine(); // Очищуємо буфер після введення числа

        try {
            // Створюємо тариф за допомогою фабричного методу та додаємо його до компанії
            Tariff tariff = TariffFactory.createTariff(type, name, fee, clients);
            company.addTariff(tariff);
            logger.info("Користувач додав тариф: " + name);
            System.out.println("\u001B[92m✅ Тариф успішно додано!\u001B[0m");
        } catch (DuplicateTariffException e) {
            // Обробка помилки, якщо тариф вже існує
            logger.warning("Спроба додати дублікат тарифу: " + name);
            System.out.println("\u001B[91m❌ Помилка: " + e.getMessage() + "\u001B[0m");
        } catch (InvalidTariffException e) {
            // Обробка помилки, якщо тип тарифу невірний
            logger.warning("Невірний тип тарифу: " + type);
            System.out.println("\u001B[91m❌ Помилка: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void searchTariff() {
        try {
            // Запитуємо мінімальну абонплату
            System.out.print("\u001B[36mВведіть мінімальну абонплату: \u001B[0m");
            double min = scanner.nextDouble();

            // Запитуємо максимальну абонплату
            System.out.print("\u001B[36mВведіть максимальну абонплату: \u001B[0m");
            double max = scanner.nextDouble();
            scanner.nextLine(); // Очищуємо буфер після введення чисел

            // Шукаємо тарифи в заданому діапазоні
            List<Tariff> foundTariffs = company.findTariffsInRange(min, max);

            // Перевіряємо, чи знайдені тарифи, і виводимо результат
            if (foundTariffs.isEmpty()) {
                System.out.println("\u001B[33mℹ️ Тарифів у заданому діапазоні не знайдено.\u001B[0m");
            } else {
                foundTariffs.forEach(t -> System.out.println("\u001B[33m" + t + "\u001B[0m"));
            }
        } catch (InputMismatchException e) {
            // Обробка помилки, якщо введено не числове значення для абонплати
            System.out.println("\u001B[91m❌ Помилка: введіть числове значення (наприклад, 100.50)!\u001B[0m");
            scanner.nextLine(); // Очищуємо буфер після введення
        } catch (EmptyTariffListException e) {
            // Обробка помилки, якщо тарифи не знайдені
            System.out.println("\u001B[91m❌ Помилка: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void deleteTariff() {
        // Запитуємо у користувача назву тарифу для видалення
        System.out.print("\u001B[36mВведіть назву тарифу для видалення: \u001B[0m");
        String name = scanner.nextLine();

        try {
            // Викликаємо метод для видалення тарифу за назвою
            company.removeTariff(name);
            // Показуємо повідомлення про успішне видалення
            System.out.println("\u001B[92m✅ Тариф успішно видалено!\u001B[0m");
        } catch (TariffNotFoundException e) {
            // Обробка помилки, якщо тариф з такою назвою не знайдено
            System.out.println("\u001B[91m❌ Помилка: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void saveTariffs() {
        try {
            // Викликаємо метод для збереження тарифів у файл
            company.saveTariffsToFile("tariffs.dat");
            // Повідомлення про успішне збереження
            System.out.println("💾 Тарифи успішно збережено!");
        } catch (IOException e) {
            // Обробка помилки, якщо виникає проблема при збереженні файлу
            System.out.println("\u001B[91m❌ Помилка при збереженні: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void loadTariffs() {
        // Створюємо об'єкт файлу, який містить збережені тарифи
        File file = new File("tariffs.dat");

        // Перевіряємо, чи існує файл і чи він не порожній
        if (!file.exists() || file.length() == 0) {
            // Якщо файл відсутній або порожній, виводимо помилку
            System.out.println("\u001B[91m❌ Помилка: файл відсутній або порожній!\u001B[0m");
            return;
        }

        try {
            // Завантажуємо тарифи з файлу
            company.loadTariffsFromFile("tariffs.dat");
            // Повідомлення про успішне завантаження
            System.out.println("\u001B[92m📂 Тарифи успішно завантажено!\u001B[0m");
        } catch (IOException e) {
            // Обробка помилки, якщо виникає проблема при завантаженні файлу
            System.out.println("\u001B[91m❌ Помилка при завантаженні файлу: " + e.getMessage() + "\u001B[0m");
        } catch (ClassNotFoundException e) {
            // Обробка помилки, якщо клас тарифу не знайдено при завантаженні
            System.out.println("\u001B[91m❌ Помилка: клас тарифу не знайдено. " + e.getMessage() + "\u001B[0m");
        }
    }
}