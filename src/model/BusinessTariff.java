package model;

import java.io.Serializable;

public class BusinessTariff extends PostpaidTariff implements Serializable {
    private static final long serialVersionUID = 1L;
    // Конструктор класу BusinessTariff, що приймає назву тарифу,
    // місячну плату та кількість клієнтів
    public BusinessTariff(String name, double monthlyFee, int numberOfClients) {
        super(name, monthlyFee, numberOfClients); // Виклик конструктора батьківського класу
    }

    // Перевизначений метод для відображення деталей тарифу
    @Override
    public void showTariffDetails() {
        // Виведення інформації про бізнес-тариф у консоль
        System.out.println("Business Tariff: " + name +
                ", Monthly Fee: " + monthlyFee +
                " USD, Clients: " + numberOfClients);
    }
}
