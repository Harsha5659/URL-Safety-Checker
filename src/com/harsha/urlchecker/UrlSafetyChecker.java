package com.harsha.urlchecker;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class UrlSafetyChecker {

    private static final List<String> blacklist = Arrays.asList("phish.com", "malware.org", "badsite.net");

    private static boolean isSuspicious(String url) {
        String lower = url.toLowerCase();

        if (!lower.startsWith("https://")) {
            System.out.println("Uses HTTP (no HTTPS) - less secure");
            return true;
        }
        for (String bad : blacklist) {
            if (lower.contains(bad)) {
                System.out.println("Matches blacklist domain: " + bad);
                return true;
            }
        }
        if (lower.length() > 75) {
            System.out.println("URL unusually long (" + lower.length() + " chars)");
            return true;
        }
        if (Pattern.compile("[0-9]{5,}").matcher(lower).find()) {
            System.out.println("Contains suspicious digit sequence");
            return true;
        }
        return false;
    }

    private static void checkUrl(String url, PrintWriter history) {
        System.out.println("> " + url);
        boolean bad = isSuspicious(url);
        if (bad) {
            System.out.println("Result: Suspicious/Unsafe\n");
            history.println(url + " -> Suspicious");
        } else {
            System.out.println("Result: Likely Safe");
            if (url.startsWith("https://")) {
                System.out.println("Uses HTTPS");
            }
            System.out.println();
            history.println(url + " -> Safe");
        }
        history.flush();
    }

    public static void main(String[] args) {
        try (PrintWriter history = new PrintWriter(new FileWriter("history.txt", true))) {
            if (args.length > 0) {
                try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        checkUrl(line.trim(), history);
                    }
                }
            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("URL Safety Checker - enter a URL (type 'exit' to quit):");
                while (true) {
                    System.out.print("> ");
                    String url = sc.nextLine();
                    if (url.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting. History saved to history.txt");
                        break;
                    }
                    checkUrl(url, history);
                }
                sc.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
