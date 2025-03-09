package model; // Оголошення пакету model

import service.Prepaid; // Імпорт інтерфейсу Prepaid з пакету service
import java.io.Serializable; // Імпорт для серіалізації

public class PrepaidTariff extends Tariff implements Prepaid, Serializable { // Клас реалізує Prepaid і Serializable
    private static final long serialVersionUID = 1L;
    public PrepaidTariff(String name, double monthlyFee, int numberOfClients) { // Конструктор
        super(name, monthlyFee, numberOfClients); // Виклик конструктора батьківського класу Tariff
    }

    @Override
    public void loadBalance(double amount) { // Реалізація методу з інтерфейсу Prepaid
        System.out.println("Balance loaded with: " + amount); // Повідомлення про поповнення балансу
    }

    @Override
    public void showTariffDetails() { // Реалізація абстрактного методу з Tariff
        System.out.println("Prepaid Tariff: " + getName() + ", Fee: " + getMonthlyFee() + " USD, Clients: " + getNumberOfClients());
    }
}