package learn.venus.ui;

import learn.venus.domain.OrbiterService;

public class Controller {

    private final OrbiterService service;
    private final View view;

    public Controller(OrbiterService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run(){
        MenuOption option;
        do {
            option = view.displayMenuAndSelect();
            switch(option){
                case EXIT:
                    System.out.println(option.getTitle());
                    break;
                case DISPLAY_ORBITERS:
                    break;
                case CREATE_ORBITER:
                    break;
                case UPDATE_ORBITER:
                    break;
                case DELETE_ORBITER:
                    break;
            }
        } while(option != MenuOption.EXIT);
    }
}
