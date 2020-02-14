package by.kiselevich;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        String format = "INSERT INTO edition(name, type, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period) VALUES(\"%s\", \"%s\", %d, %d, %d, %.2f);";

	    List<String> list = Files.readAllLines(Paths.get("input.txt"));
	    Files.write(Paths.get("output.txt"), "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	    for (String string : list) {
	        String[] array = string.split(";");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt", true))) {
                bufferedWriter.write(String.format(format,
                        array[0].replaceAll("\"", "\\\\\""),
                        "Газета",
                        5,
                        new Scanner(array[1]).nextInt() * 2,
                        new Scanner(array[2]).nextInt(),
                        new Scanner(array[3]).nextDouble()
                ));
                bufferedWriter.write(System.lineSeparator());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
