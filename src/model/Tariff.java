package model; // Оголошення пакету model
import java.io.Serializable;

public abstract class Tariff implements Comparable<Tariff>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name; // Назва тарифу
    protected double monthlyFee; // Щомісячна плата за тариф
    protected int numberOfClients; // Кількість клієнтів тарифу

    public Tariff(String name, double monthlyFee, int numberOfClients) { // Конструктор класу Tariff
        this.name = name; // Ініціалізація назви тарифу
        this.monthlyFee = monthlyFee; // Ініціалізація щомісячної плати
        this.numberOfClients = numberOfClients; // Ініціалізація кількості клієнтів
    }

    public String getName() { // Геттер для отримання назви тарифу
        return name;
    }

    public void setName(String name) { // Сеттер для встановлення назви тарифу
        this.name = name;
    }

    public double getMonthlyFee() { // Геттер для отримання щомісячної плати
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) { // Сеттер для встановлення щомісячної плати
        this.monthlyFee = monthlyFee;
    }

    public int getNumberOfClients() { // Геттер для отримання кількості клієнтів
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) { // Сеттер для встановлення кількості клієнтів
        this.numberOfClients = numberOfClients;
    }

    @Override
    public int compareTo(Tariff other) { // Метод для порівняння тарифів за щомісячною платою
        return Double.compare(this.monthlyFee, other.monthlyFee);
    }

    // Додатковий метод для сортування по кількості клієнтів
    public static int compareByClients(Tariff t1, Tariff t2) { // Метод для порівняння тарифів за кількістю клієнтів
        return Integer.compare(t1.getNumberOfClients(), t2.getNumberOfClients());
    }

    @Override
    public String toString() { // Перевизначений метод toString для виводу інформації про тариф
        return name + ": " + monthlyFee + " USD, Clients: " + numberOfClients;
    }

    public abstract void showTariffDetails(); // Абстрактний метод для відображення деталей тарифу
}
