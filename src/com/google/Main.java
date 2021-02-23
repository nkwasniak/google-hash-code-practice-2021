package com.google;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static String fileIn = "a_example";
    private static String fileOut = "output_a.txt";

    public static void main(String[] args) {
        Scanner scanner = scanFile();

        int amountOfPizza = scanner.nextInt();
        int duetsQuantity = scanner.nextInt();
        int triosQuantity = scanner.nextInt();
        int quartetsQuantity = scanner.nextInt();

        List<Pizza> pizzas = new ArrayList<>();

        for (int i = 0; i < amountOfPizza; i++) {
            int amountOfIngrid = scanner.nextInt();
            pizzas.add(new Pizza(i));
            for (int j = 0; j < amountOfIngrid; j++) {
                pizzas.get(i).ingredients.add(scanner.next());
            }
        }

        Collections.sort(pizzas);

        int maxPoints = 0;
        List<Result> results = new ArrayList<>();
        int numberOfHungryQuartets = quartetsQuantity;
        int numberOfHungryTrios = triosQuantity;
        int numberOfHungryDuos = duetsQuantity;

        for (int i = 0; i < quartetsQuantity + triosQuantity + duetsQuantity; i++) {
            int pizzaIndex = 0;
            int notDeliveredPizzas = amountOfPizza;
            Set<String> pizzaIngredientsForQuartets = new HashSet<>();
            Set<String> pizzaIngredientsForTrios = new HashSet<>();
            Set<String> pizzaIngredientsForDuos = new HashSet<>();
            List<Result> temp = new ArrayList<>();
            for (int j = i; j < (quartetsQuantity + triosQuantity + duetsQuantity) && notDeliveredPizzas >= 2; j++) {
                if (numberOfHungryQuartets-- > 0 && notDeliveredPizzas >= 4) {
                    Result resultForQuartet = new Result(4);

                    Stream.of(
                            pizzas.get(pizzaIndex),
                            pizzas.get(pizzaIndex + 1),
                            pizzas.get(pizzaIndex + 2),
                            pizzas.get(pizzaIndex + 3)
                    ).forEach(pizza -> {
                        pizzaIngredientsForQuartets.addAll(pizza.ingredients);
                        resultForQuartet.pizzas.add(pizza.id);
                    });

                    temp.add(resultForQuartet);
                    pizzaIndex += 4;
                    notDeliveredPizzas -= 4;
                } else if (numberOfHungryTrios-- > 0 && notDeliveredPizzas >= 3) {
                    Result resultForTrio = new Result(3);

                    Stream.of(
                            pizzas.get(pizzaIndex),
                            pizzas.get(pizzaIndex + 1),
                            pizzas.get(pizzaIndex + 2)
                    ).forEach(pizza -> {
                        pizzaIngredientsForTrios.addAll(pizza.ingredients);
                        resultForTrio.pizzas.add(pizza.id);
                    });

                    temp.add(resultForTrio);
                    pizzaIndex += 3;
                    notDeliveredPizzas -= 3;
                } else if (numberOfHungryDuos-- > 0) {
                    Result resultForDuo = new Result(2);

                    Stream.of(
                            pizzas.get(pizzaIndex),
                            pizzas.get(pizzaIndex + 1)
                    ).forEach(pizza -> {
                        pizzaIngredientsForDuos.addAll(pizza.ingredients);
                        resultForDuo.pizzas.add(pizza.id);
                    });

                    temp.add(resultForDuo);
                    pizzaIndex += 2;
                    notDeliveredPizzas -= 2;
                }
            }
            int points =
                    getPoints(pizzaIngredientsForQuartets, pizzaIngredientsForTrios, pizzaIngredientsForDuos);
            if (points > maxPoints) {
                maxPoints = points;
                results = temp;
            }
        }
        saveResultToFile(results);

    }

    private static void saveResultToFile(List<Result> results) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(results.size());
        for (Result res : results) {
            printWriter.print(res.teamId + " ");
            for (int pizzaId : res.pizzas) {
                printWriter.print(pizzaId + " ");
            }
            printWriter.println();
        }
        printWriter.close();
    }

    private static int getPoints(Set<String> pizzaIngredientsForQuartets, Set<String> pizzaIngredientsForTrios, Set<String> pizzaIngredientsForDuos) {
        return (int) (Math.pow(pizzaIngredientsForQuartets.size(), 2) + Math.pow(pizzaIngredientsForTrios.size(), 2) + Math.pow(pizzaIngredientsForDuos.size(), 2));
    }

    private static Scanner scanFile() {
        File file =
                new File(fileIn);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanner;
    }
}
