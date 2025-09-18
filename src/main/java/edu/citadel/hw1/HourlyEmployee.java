package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;

public class HourlyEmployee extends Employee {
    private final double wageRate;
    private final double hoursWorked;

    public HourlyEmployee(String name, LocalDate hireDate, double wageRate, double hoursWorked) {
        super(name, hireDate);
        this.wageRate = wageRate;
        this.hoursWorked = hoursWorked;
    }

    public double getWageRate() {
        return wageRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public double getMonthlyPay() {
        return wageRate * hoursWorked;
    }

    @Override
    public String toString() {
        return String.format(
                "HourlyEmployee[name=%s, hireDate=%s, wageRate=%s, hoursWorked=%s]",
                getName(), getHireDate(), wageRate, hoursWorked
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HourlyEmployee)) return false;
        HourlyEmployee that = (HourlyEmployee) o;
        return Double.compare(that.wageRate, wageRate) == 0
                && Double.compare(that.hoursWorked, hoursWorked) == 0
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getHireDate(), that.getHireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHireDate(), wageRate, hoursWorked);
    }
}
