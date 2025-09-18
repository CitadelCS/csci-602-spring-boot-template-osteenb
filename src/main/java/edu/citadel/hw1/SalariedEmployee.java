package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;

public class SalariedEmployee extends Employee {
    private final double annualSalary;

    public SalariedEmployee(String name, LocalDate hireDate, double annualSalary) {
        super(name, hireDate);
        this.annualSalary = annualSalary;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    @Override
    public double getMonthlyPay() {
        return annualSalary / 12.0;
    }

    @Override
    public String toString() {
        return String.format(
                "SalariedEmployee[name=%s, hireDate=%s, annualSalary=%s]",
                getName(), getHireDate(), annualSalary
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalariedEmployee)) return false;
        SalariedEmployee that = (SalariedEmployee) o;
        return Double.compare(that.annualSalary, annualSalary) == 0
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getHireDate(), that.getHireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHireDate(), annualSalary);
    }
}
