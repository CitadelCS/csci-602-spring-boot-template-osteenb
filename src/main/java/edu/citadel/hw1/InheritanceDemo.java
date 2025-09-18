package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InheritanceDemo {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        // Hourly employees
        employees.add(new HourlyEmployee(
                "John Doe",
                LocalDate.parse("2009-05-21"),
                50.5,
                160.0
        ));
        employees.add(new HourlyEmployee(
                "Jane Doe",
                LocalDate.parse("2005-09-01"),
                150.5,
                80.0
        ));

        // Salaried employees
        employees.add(new SalariedEmployee(
                "Moe Howard",
                LocalDate.parse("2004-01-01"),
                75_000.0
        ));
        employees.add(new SalariedEmployee(
                "Curly Howard",
                LocalDate.parse("2018-01-01"),
                105_000.0
        ));

        System.out.println("List of Employees (before sorting)");
        for (Employee e : employees) {
            System.out.println(e);
        }

        System.out.println();
        Collections.sort(employees);

        System.out.println("List of Employees (after sorting)");
        for (Employee e : employees) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("Monthly Pay");
        double total = 0.0;
        for (Employee e : employees) {
            double pay = e.getMonthlyPay();
            System.out.printf("%s: $%,.2f%n", e.getName(), pay);
            total += pay;
        }
        System.out.printf("Total Monthly Pay: $%,.2f%n", total);
    }
}
