package learn.solarfarm.ui;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.SolarPanel;

import java.util.List;

public class View {

    private final TextIO io;

    public View(TextIO io) {
        this.io = io;
    }

    public int chooseMenuOption() {
        displayHeader("Main Menu");
        io.println("0. Exit");
        io.println("1. Find Panels by Section");
        io.println("2. Add a Panel");
        io.println("3. Update a Panel");
        io.println("4. Remove a Panel");
        return io.readInt("Choose [0-4]:", 0, 4);
    }

    public SolarPanel chooseSolarPanel(List<SolarPanel> solarPanels) {
        displaySolarPanels(solarPanels);
        SolarPanel result = null;
        if (solarPanels.size() > 0) {
            int solarPanelId = io.readInt("Choose a Panel ID:");
            for (SolarPanel solarPanel : solarPanels) {
                if (solarPanel.getId() == solarPanelId) {
                    result = solarPanel;
                    break;
                }
            }
        }
        return result;
    }

    public boolean isTracking() {
        io.println("1. Installed with sun-tracking software");
        io.println("2. Not installed with sun-tracking software");
        return io.readInt("Choose [1-2]:", 1, 2) == 1;
    }

    public void displayHeader(String message) {
        int length = message.length();
        io.println("");
        io.println(message);
        io.println("=".repeat(length));
    }

    public void displayErrors(List<String> errors) {
        displayHeader("Errors:");
        for (String error : errors) {
            io.println(error);
        }
    }

    public void displaySolarPanels(List<SolarPanel> solarPanels) {
        if (solarPanels.size() == 0) {
            displayHeader("No Panels Found.");
        } else {
            displayHeader("Solar Panels:");
            for (SolarPanel solarPanel : solarPanels) {
                io.printf("Id: %s, Section: %s, Row: %s, Column: %s, " +
                                "Year Installed: %s, Material Type: %s, Sun-Tracking: %s%n",
                        solarPanel.getId(),
                        solarPanel.getSection(), solarPanel.getRow(), solarPanel.getColumn(),
                        solarPanel.getYearInstalled(), solarPanel.getMaterialType().name(),
                        solarPanel.isTracking());
            }
        }
    }

    public void displayMessage(String message) {
        io.println("");
        io.println(message);
    }

    public SolarPanel createSolarPanel() {
        displayHeader("Add a Panel");
        SolarPanel result = new SolarPanel();
        result.setSection(io.readString("Section: "));
        result.setRow(io.readInt("Row: ", 0, 250));
        result.setColumn(io.readInt("Column: ", 0, 250));
        result.setMaterialType(io.readMaterialType("Material :"));
        result.setYearInstalled(io.readString("Installation Year: "));
        result.setTracking(io.readBoolean("Tracked [y/n]: "));
        return result;
    }

    public SolarPanel editSolarPanel(SolarPanel solarPanel) {
        displayHeader("Update");

        String section = io.readString("Section (" + solarPanel.getSection() + "): ");
        if (section.trim().length() > 0){
            solarPanel.setSection(section);
        }

        int row = io.readInt("Row (" + solarPanel.getRow() + "): ");
        solarPanel.setRow(row);

        int column = io.readInt("Column (" + solarPanel.getColumn() + "): ");
        solarPanel.setColumn(column);

        String year = io.readString("Installation Year (" + solarPanel.getYearInstalled() + "): ");
        if (section.trim().length() > 0){
            solarPanel.setYearInstalled(year);
        }

        MaterialType materialType = io.readMaterialType("Material Type (" + solarPanel.getMaterialType().name() + "): ");
        solarPanel.setMaterialType(materialType);

        boolean isTracking = io.readBoolean("Tracked (" + solarPanel.isTracking() + "): ");
        solarPanel.setTracking(isTracking);

        return solarPanel;
    }
}
