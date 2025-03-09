package service;

// Імпорт класу Tariff з пакету model
import model.Tariff;
// Імпорт класу TariffNotFoundException з пакету exception для обробки помилок, коли тариф не знайдено
import exception.TariffNotFoundException;
// Імпорт класу DuplicateTariffException з пакету exception для обробки помилок, коли намагаються додати дубль тарифу
import exception.DuplicateTariffException;
// Імпорт класу EmptyTariffListException з пакету exception для обробки помилок, коли список тарифів порожній
import exception.EmptyTariffListException;
// Імпорт класу для роботи з файлами та введенням/виведенням в/з файлів
import java.io.*;
// Імпорт класів для роботи з колекціями, включаючи списки та їх сортування
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// Імпорт класу Logger для ведення журналу логів у додатку (наприклад, для запису подій)
import java.util.logging.Logger;


public class MobileCompany {
    private static final Logger logger = Logger.getLogger(MobileCompany.class.getName());
    private List<Tariff> tariffs = new ArrayList<>();

    // Додавання тарифу
    public void addTariff(Tariff tariff) throws DuplicateTariffException {
        // Перевірка на від’ємні значення
        if (tariff.getMonthlyFee() < 0 || tariff.getNumberOfClients() < 0) {
            throw new IllegalArgumentException("Помилка: плата та кількість клієнтів не можуть бути від’ємними!");
        }

        // Перевірка на дублікат
        for (Tariff existingTariff : tariffs) {
            if (existingTariff.getName().equals(tariff.getName())) {
                throw new DuplicateTariffException(existingTariff.getName(), existingTariff.getMonthlyFee(), existingTariff.getNumberOfClients());
            }
        }

        // Додавання тарифу
        tariffs.add(tariff);
        logger.info("Додано тариф: " + tariff.getName());
    }

    // Збереження тарифів у файл
    public void saveTariffsToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tariffs);
            logger.info("Тарифи збережено у файл: " + filename);
        } catch (FileNotFoundException e) {
            logger.severe("Файл не знайдено: " + e.getMessage());
            throw new IOException("Файл не знайдено: " + e.getMessage());
        } catch (IOException e) {
            logger.severe("Помилка при збереженні тарифів: " + e.getMessage());
            throw new IOException("Не вдалося зберегти тарифи у файл: " + e.getMessage());
        }
    }

    // Завантаження тарифів з файлу
    @SuppressWarnings("unchecked")
    public void loadTariffsFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            tariffs = (List<Tariff>) ois.readObject();
            logger.info("Тарифи завантажено з файлу: " + filename);
        } catch (IOException e) {
            logger.severe("Помилка при читанні файлу: " + e.getMessage());
            throw new IOException("Не вдалося завантажити тарифи з файлу: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.severe("Невірний формат даних у файлі: " + e.getMessage());
            throw new ClassNotFoundException("Неможливо знайти клас тарифу: " + e.getMessage());
        }
    }

    // Пошук тарифів у заданому діапазоні
    public List<Tariff> findTariffsInRange(double min, double max) throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, пошук неможливий.");
        }
        List<Tariff> result = new ArrayList<>();
        for (Tariff tariff : tariffs) {
            if (tariff.getMonthlyFee() >= min && tariff.getMonthlyFee() <= max) {
                result.add(tariff);
            }
        }
        logger.info("Found tariffs in range " + min + " - " + max);
        return result;
    }

    // Оновлення тарифу за назвою
    public void updateTariff(String name, double newFee, int newClients) throws TariffNotFoundException {
        boolean found = false;
        for (Tariff tariff : tariffs) {
            if (tariff.getName().equals(name)) {
                tariff.setMonthlyFee(newFee);
                tariff.setNumberOfClients(newClients);
                logger.info("Updated tariff: " + tariff.getName());
                found = true;
                break;
            }
        }
        if (!found) {
            logger.warning("Спроба оновити неіснуючий тариф: " + name);
            throw new TariffNotFoundException("Тариф із назвою " + name + " не знайдено.");
        }
    }

    // Видалення тарифу
    public void removeTariff(String name) throws TariffNotFoundException {
        if (!tariffs.removeIf(t -> t.getName().equals(name))) {
            logger.warning("Спроба видалити неіснуючий тариф: " + name);
            throw new TariffNotFoundException("Тариф із назвою " + name + " не знайдено.");
        }
        logger.info("Видалено тариф: " + name);
    }

    // Підрахунок загальної кількості клієнтів
    public int getTotalClients() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, немає клієнтів для підрахунку.");
        }
        return tariffs.stream().mapToInt(Tariff::getNumberOfClients).sum();
    }

    // Підрахунок середньої абонентської плати
    public double getAverageMonthlyFee() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, середня плата не може бути обчислена.");
        }
        return tariffs.stream().mapToDouble(Tariff::getMonthlyFee).average().orElse(0);
    }

    // Тариф з найбільшою кількістю клієнтів
    public Tariff getTariffWithMaxClients() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, немає тарифів для порівняння.");
        }
        return tariffs.stream()
                .max((t1, t2) -> Integer.compare(t1.getNumberOfClients(), t2.getNumberOfClients()))
                .orElse(null);
    }

    // Сортування тарифів за абонплатою
    public void sortTariffs() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, сортування неможливе.");
        }
        Collections.sort(tariffs);
        logger.info("Tariffs sorted by monthly fee.");
    }

    // Виведення тарифів
    public void printTariffs() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, немає що виводити.");
        }
        tariffs.forEach(System.out::println);
    }

    // Сортування тарифів за кількістю клієнтів
    public void sortTariffsByClients() throws EmptyTariffListException {
        if (tariffs.isEmpty()) {
            throw new EmptyTariffListException("Список тарифів порожній, сортування неможливе.");
        }
        tariffs.sort((t1, t2) -> Integer.compare(t1.getNumberOfClients(), t2.getNumberOfClients()));
        logger.info("Tariffs sorted by number of clients.");
    }
}