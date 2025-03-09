package model; // Оголошення пакету, в якому знаходиться клас

// Імпорт інтерфейсу Postpaid з пакету service
import service.Postpaid;
import java.io.Serializable;

public class PostpaidTariff extends Tariff implements Postpaid, Serializable {
    private static final long serialVersionUID = 1L;
    // Конструктор класу PostpaidTariff, що приймає назву тарифу,
    // місячну плату та кількість клієнтів
    public PostpaidTariff(String name, double monthlyFee, int numberOfClients) {
        super(name, monthlyFee, numberOfClients); // Виклик конструктора батьківського класу
    }

    // Реалізація методу payBill з інтерфейсу Postpaid для оплати рахунку
    @Override
    public void payBill(double amount) {
        System.out.println("Bill paid: " + amount); // Виведення інформації про оплату рахунку
    }

    // Перевизначений метод для відображення деталей тарифу
    @Override
    public void showTariffDetails() {
        // Виведення інформації про постоплатний тариф у консоль
        System.out.println("Postpaid Tariff: " + name +
                ", Monthly Fee: " + monthlyFee +
                " USD, Clients: " + numberOfClients);
    }
}
