package model;

import java.io.Serializable;

public class StudentTariff extends PrepaidTariff implements Serializable {
    private static final long serialVersionUID = 1L; // Додаємо унікальний ідентифікатор

    public StudentTariff(String name, double monthlyFee, int numberOfClients) { // Конструктор класу StudentTariff
        super(name, monthlyFee, numberOfClients);  // Виклик конструктора батьківського класу PrepaidTariff для ініціалізації властивостей
    }

    @Override
    public void showTariffDetails() { // Перевизначення методу showTariffDetails для виведення деталей тарифу студента
        System.out.println("Student Tariff: " + getName() + ", Monthly Fee: " + getMonthlyFee() + ", Clients: " + getNumberOfClients()); // Виведення деталей тарифу за допомогою методів геттерів
    }
}
