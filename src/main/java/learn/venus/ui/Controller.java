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
                    displayOrbiters();
                    break;
                case CREATE_ORBITER:
                    createOrbiter();
                    break;
                case UPDATE_ORBITER:
                    updateOrbiter();
                    break;
                case DELETE_ORBITER:
                    deleteOrbiter();
                    break;
            }
        } while(option != MenuOption.EXIT);
    }

    private void displayOrbiters() {
        view.printHeader(MenuOption.DISPLAY_ORBITERS.getTitle());
    }

    private void createOrbiter() {
        view.printHeader(MenuOption.CREATE_ORBITER.getTitle());
    }

    private void updateOrbiter() {
        view.printHeader(MenuOption.UPDATE_ORBITER.getTitle());
    }

    private void deleteOrbiter() {
        view.printHeader(MenuOption.DELETE_ORBITER.getTitle());
    }
}
