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
                Gatherer.of(HashMap<String, StringBuilder>::new,                                                    //initializer
                        (map, fruit, downstream) -> {    // integrator
                            IO.println("Adding fruit: " + fruit + " to private state object map");
                            String key = fruit.substring(0, 1);
                            StringBuilder stringBuilder = map.computeIfAbsent(key, k -> new StringBuilder());
                            stringBuilder.append(fruit).append(", ");
                            return true;

                        }, (map1, map2) -> map1,            // combiner
                        (map, downstream) -> {               // finisher

                            Map<String, String> result = new HashMap<>();
                            IO.println("Removing trailing comma for each value");
                            map.forEach((key, value) -> {
                                String str = value.toString();
                                if (str.endsWith(", ")) {
                                    str = str.substring(0, str.length() - 2);
                                }

                                result.put(key, str);
                            });
                            IO.println("Adding the map to downstream");
                            downstream.push(result);
                        }
                );

        Map<String, String> result = fruits
                .gather(groupByFirstLetter)
                .findFirst().orElse(Map.of());
        IO.println(result);

    }

}
