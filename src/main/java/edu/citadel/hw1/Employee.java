package edu.citadel.hw1;

import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {
    private final String name;
    private final LocalDate hireDate;

    public Employee(String name, LocalDate hireDate) {
        if (name == null || hireDate == null) {
            throw new IllegalArgumentException("name and hireDate must not be null");
        }
        this.name = name;
        this.hireDate = hireDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public abstract double getMonthlyPay();

    @Override
    public int compareTo(Employee other) {
        // Order employees based on monthly salaries (ascending)
        return Double.compare(this.getMonthlyPay(), other.getMonthlyPay());
    }
}
