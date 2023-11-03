package learn.venus.ui;

import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);
    public MenuOption displayMenuAndSelect(){
        return MenuOption.EXIT;
    }

}
