package com.java24demo.feature.streamgatherer;

import java.io.IO;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class StreamGathererExample {
    public static void main(String[] args) {

        Stream<String> fruits = Stream.of("apple", "apricot", "banana", "blueberry", "cherry");

        Gatherer<String, HashMap<String, StringBuilder>, Map<String, String>> groupByFirstLetter =
                Gatherer.of(HashMap<String, StringBuilder>::new,
                        (map, fruit, downstream) -> {
                            IO.println("Adding fruit: " + fruit);
                            String key = fruit.substring(0, 1);
                            map.computeIfAbsent(key, k -> new StringBuilder()).append(fruit).append(", ");
                            return true;

                        }, (map1, map2) -> map1,
                        (map, downstream) -> {
                            Map<String, String> result = new HashMap<>();
                            map.forEach((key, value) -> {
                                // Retirer la dernière virgule et l'espace
                                String str = value.toString();
                                if (str.endsWith(", ")) {
                                    str = str.substring(0, str.length() - 2);
                                }
                                result.put(key, str);
                            });
                            downstream.push(result);
                        }
                );

        Map<String, String> result = fruits
                .gather(groupByFirstLetter)
                .findFirst().orElse(Map.of());
        IO.println(result);

    }

}
