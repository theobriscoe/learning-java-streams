package com.theobriscoe.examples;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.theobriscoe.examples.common.Gender;
import com.theobriscoe.examples.common.Person;

public class Collect {

  public static List<Person> createPeople() {
    return Arrays.asList(
      new Person("Sara", Gender.FEMALE, 20),
      new Person("Sara", Gender.FEMALE, 22),
      new Person("Bob", Gender.MALE, 20),
      new Person("Paula", Gender.FEMALE, 32),
      new Person("Paul", Gender.MALE, 32),
      new Person("Jack", Gender.MALE, 2),
      new Person("Jack", Gender.MALE, 72),
      new Person("Jill", Gender.FEMALE, 12));
  }

  public static void main(String[] args) {
    List<Person> people = createPeople();

    // list of all adult names in uppercase
    List<String> names = new ArrayList<>();

    people.stream()
        .filter(person -> person.getAge() > 17)
        .map(Person::getName)
        .map(String::toUpperCase)
        .forEach(names::add); // DON'T DO THIS

    // causes side-effect, interfering, mutating, ugly, smelly, dangerous, can't make this concurrent.

    System.out.println(names);

    // list of all adult names in uppercase
    names = people.stream()
        .filter(person -> person.getAge() > 17)
        .map(Person::getName)
        .map(String::toUpperCase)
        .collect(
          () -> new ArrayList<String>(),
          (list, name) -> list.add(name),
          (list1, list2) -> list1.addAll(list2));

    System.out.println(names);

    // Smelly, but illustrates some essential points.
    /**
     * The collect takes three parameters: <br>
     * 1. Supplier - the initial value for the reduction 2.<br>
     * The accumulator - this takes an element and accumulates to the evolving result<br>
     * 3. The combiner - this is useful during parallel execution, this can take two partial results and combine into
     * one.
     */

    // Simplification
    List<String> names2 = people.stream()
        .filter(person -> person.getAge() > 17)
        .map(Person::getName)
        .map(String::toUpperCase)
        .collect(toList());

    System.out.println(names2);

  }
}
