package interfaces;

import java.time.LocalDate;

public interface IProfitable {
    double calculateDailyProfit(double serviceFee); // serviceFee parametresi eklendi
    double calculateDailyProfit(LocalDate targetDate, double serviceFee);
    double calculateTotalProfit();
}