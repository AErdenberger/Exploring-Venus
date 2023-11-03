package learn.venus.ui;

import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);
    public MenuOption displayMenuAndSelect(){
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for(int i = 0; i < values.length; i++) {
            System.out.printf("%s: %s%n", i, values[i].getTitle());
        }
        return MenuOption.EXIT;
    }

    public void printHeader(String message){
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    private String readString(String prompt){

    }

}
