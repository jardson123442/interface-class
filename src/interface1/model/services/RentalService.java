package interface1.model.services;

import interface1.model.entities.CarRental;
import interface1.model.entities.Invoice;

import java.time.Duration;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService texService;

    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService texService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.texService = texService;
    }

    public void processInvoice(CarRental carRental) {

        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60.0;

        double basicPayment;
        if (hours <= 12.0) {
            basicPayment = pricePerHour * Math.ceil(hours);
        } else {
            basicPayment = pricePerDay * Math.ceil(hours / 24.0);
        }

        double tax = texService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
