package com.theobriscoe.examples;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import com.theobriscoe.examples.common.Gender;
import com.theobriscoe.examples.common.Person;

public class LazyEvaluation {
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

  public static boolean isOlderThan25(Person person) {
    System.out.println("Evaluating person " + person);
    return person.getAge() > 25;
  }

  public static void main(String[] args) {
    List<Person> people = createPeople();

    // find any person whose name is four letters but is older than 25

    System.out.println(
      people.stream()
          .filter(LazyEvaluation::isOlderThan25)
          .filter(person -> person.getName().length() == 4)
          .findAny());

    System.out.println(
      people.stream()
          .filter(LazyEvaluation::isOlderThan25)
          .filter(person -> person.getName().length() == 4)
          .findFirst());

    System.out.println(
      people.stream()
          .filter(LazyEvaluation::isOlderThan25)
          .filter(person -> person.getName().length() == 4)
          .collect(toList()));

    // Fusing - all the intermediate operations are combined and executed at once on each element
    // lazy - no work is done until and unless it's needed. Elements after paul were not evaluated.

    // To see lazy let's run the sequence again, but without the terminal operation.
    System.out.println("No work got done here");
    System.out.println(
      people.stream()
          .filter(LazyEvaluation::isOlderThan25)
          .filter(person -> person.getName().length() == 4));
  }

}
