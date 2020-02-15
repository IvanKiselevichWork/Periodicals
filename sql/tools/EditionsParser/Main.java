package by.kiselevich;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        String format = "INSERT INTO edition(name, type, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period) VALUES(\"%s\", \"%s\", %d, %d, %d, %.2f);";

	    List<String> list = Files.readAllLines(Paths.get("input.txt"));
	    String allText = new String();
	    for (String s : list) {
	        allText += s + "\n";
        }
	    String regex1 = "(?s)\\n\\n";
	    String replace1 = "";
        String regex2 = "(?s)\\t";
        String replace2 = "";
        //String regex3 = "(?s)\\n(.+)\\n(.+)\\n(.+)\\n(.+)";
        //String replace3 = "$1;$2;$3;$4\\n";
        String regex4 = "(?s)\\d{5}\\b";
        String replace4 = "";
        String regex5 = "(?s)(\\n\\d+),";
        String replace5 = "$1.";
/*
        allText = Pattern.compile(regex1, Pattern.DOTALL).matcher(allText).replaceAll(replace1);
        allText = Pattern.compile(regex2, Pattern.DOTALL).matcher(allText).replaceAll(replace2);
        allText = Pattern.compile(regex3, Pattern.DOTALL).matcher(allText).replaceAll(replace3);
        allText = Pattern.compile(regex4, Pattern.DOTALL).matcher(allText).replaceAll(replace4);
        allText = Pattern.compile(regex5, Pattern.DOTALL).matcher(allText).replaceAll(replace5);

 */
        allText = allText.replaceAll(regex1, replace1);
        allText = allText.replaceAll(regex2, replace2);
        //allText = allText.replaceAll(regex3, replace3);
        allText = allText.replaceAll(regex4, replace4);
        allText = allText.replaceAll(regex5, replace5);
        String[] allArray = allText.split("\n");
	    Files.write(Paths.get("output.txt"), "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	    for (int i = 1; i < allArray.length;) {

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt", true))) {
                bufferedWriter.write(String.format(format,
                        allArray[i++].replaceAll("\"", "\\\\\""),
                        "Журнал",
                        24,
                        new Scanner(allArray[i++]).nextInt() * 2,
                        new Scanner(allArray[i++]).nextInt(),
                        new Scanner(allArray[i++]).nextDouble()
                ));
                bufferedWriter.write(System.lineSeparator());

            } catch (IOException | InputMismatchException e) {
                e.printStackTrace();
                //System.out.println("string = " + string);
            }
        }
    }
}
