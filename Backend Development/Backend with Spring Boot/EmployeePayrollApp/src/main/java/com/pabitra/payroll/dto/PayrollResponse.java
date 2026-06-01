package com.pabitra.payroll.dto;

public class PayrollResponse {

    private String employeeName;
    private double salary;
    private double bonus;
    private double totalSalary;

    public PayrollResponse(String employeeName,
                           double salary,
                           double bonus,
                           double totalSalary) {
        this.employeeName = employeeName;
        this.salary = salary;
        this.bonus = bonus;
        this.totalSalary = totalSalary;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public double getTotalSalary() {
        return totalSalary;
    }
}