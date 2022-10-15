package com.DatabaseLike;

import java.util.ArrayList;

import com.Models.Employee;

import java.util.*;

public class DatabaseLike {
    static public ArrayList<Employee> employees = new ArrayList<>();

    static {
        Collections.addAll(employees,
            new Employee("Vlad", "qwerty"),
            new Employee("Red", "123456"),
            new Employee("Victor", "qwerty123"),
            new Employee("John Doe", "qwerty123"),
            new Employee("Eod Hoj", "123456"),
            new Employee("Derek Flower", "qwerty123")
        );
    }

   
}
