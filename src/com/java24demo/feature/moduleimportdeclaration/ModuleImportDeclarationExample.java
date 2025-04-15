package com.java24demo.feature.moduleimportdeclaration;

//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

import module java.base;

/**
 *JEP 494: Module Import Declarations (Second Preview)
 */
public class ModuleImportDeclarationExample {


    public static void main(String[] args) {
        String[] fruits = new String[] { "apple", "berry", "citrus" };
        Map<String, String> m =
                Stream.of(fruits)
                        .collect(Collectors.toMap(s -> s.toUpperCase().substring(0,1),
                                Function.identity()));
    }
}
