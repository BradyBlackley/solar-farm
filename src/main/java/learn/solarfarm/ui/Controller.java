package learn.solarfarm.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;

import java.util.List;

public class Controller {
    private final View view;
    private final SolarPanelService service;

    public Controller(View view, SolarPanelService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.displayHeader("Welcome to Solar Farm");
        try {
            runApp();
        } catch (DataAccessException ex) {
            view.displayErrors(List.of(ex.getMessage()));
        }
    }

    private void runApp() throws DataAccessException {

        for (int option = view.chooseMenuOption();
             option > 0;
             option = view.chooseMenuOption()) {

            switch (option) {
                case 1:
                    viewSolarPanels();
                    break;
                case 2:
                    addSolarPanel();
                    break;
                case 3:
                    updateSolarPanel();
                    break;
                case 4:
                    deleteSolarPanel();
                    break;
            }

        }
    }

    private void viewSolarPanels() throws DataAccessException {
        List<SolarPanel> solarPanels = getSolarPanels("View Solar Panels");
        view.displaySolarPanels(solarPanels);
    }

    private void addSolarPanel() throws DataAccessException {
        SolarPanel solarPanel = view.createSolarPanel();
        SolarPanelResult result = service.add(solarPanel);
        if (result.isSuccess()) {
            view.displayMessage("Solar panel " + result.getSolarPanel().getId() + " created.");
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private void updateSolarPanel() throws DataAccessException {
        List<SolarPanel> solarPanels = getSolarPanels("Update a Panel");
        SolarPanel solarPanel = view.chooseSolarPanel(solarPanels);
        if (solarPanel == null) {
            view.displayMessage("Panel not found");
            return;
        }
        solarPanel = view.editSolarPanel(solarPanel);
        SolarPanelResult result = service.update(solarPanel);
        if (result.isSuccess()) {
            view.displayMessage("Panel " + result.getSolarPanel().getId() + " updated.");
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private void deleteSolarPanel() throws DataAccessException {
        List<SolarPanel> solarPanels = getSolarPanels("Delete a Panel");
        SolarPanel solarPanel = view.chooseSolarPanel(solarPanels);
        if (solarPanel != null && service.deleteById(solarPanel.getId()).isSuccess()) {
            view.displayMessage("Panel " + solarPanel.getId() + " deleted.");
        } else {
            view.displayMessage("Panel not found.");
        }
    }

    private List<SolarPanel> getSolarPanels(String title) throws DataAccessException {
        view.displayHeader(title);
        return service.findAll();
    }

}
