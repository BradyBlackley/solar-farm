package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.models.SolarPanel;

import java.util.List;

public class SolarPanelService {

    private final SolarPanelRepository repository;

    public SolarPanelService(SolarPanelRepository repository) {
        this.repository = repository;
    }

    public List<SolarPanel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public SolarPanel findById(int id) throws DataAccessException {
        return repository.findById(id);
    }

    public SolarPanel findBySectionRowColumn(String section, int row, int column) throws DataAccessException {
        return repository.findBySectionRowColumn(section, row, column);
    }

    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public List<SolarPanel> findByTracking(boolean isTracking) throws DataAccessException {
        return repository.findByTracking(isTracking);
    }

    public SolarPanelResult add(SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = validate(solarPanel);

        if (solarPanel.getId() > 0) {
            result.addErrorMessage("Solar panel `id` should not be set.");
        }

        List<SolarPanel> all = repository.findAll();
        for (SolarPanel solarPanel1 : all) {
            if (solarPanel1.getRow() == solarPanel.getRow()
                    && solarPanel1.getColumn() == solarPanel.getColumn()
                    && solarPanel1.getSection().equals(solarPanel.getSection())) {
                result.addErrorMessage("A solar panel at `row` " + solarPanel.getRow()
                        + ", `column` " + solarPanel.getColumn()
                        + ",`section` " + solarPanel.getSection()
                        + " already exists.");
                return result;
            }
        }

        if (result.isSuccess()) {
            solarPanel = repository.add(solarPanel);
            result.setSolarPanel(solarPanel);
        }
        return result;
    }

    public SolarPanelResult update(SolarPanel solarPanel) throws DataAccessException {

        SolarPanelResult result = validate(solarPanel);

        if (solarPanel.getId() <= 0) {
            result.addErrorMessage("Solar panel `id` is required.");
        }

        if (result.isSuccess()) {
            if (repository.update(solarPanel)) {
                result.setSolarPanel(solarPanel);
            } else {
                String message = String.format("Solar panel `id` %s was not found.", solarPanel.getId());
                result.addErrorMessage(message);
            }
        }
        return result;
    }

    public SolarPanelResult deleteById(int solarPanelId) throws DataAccessException {

        SolarPanelResult result = new SolarPanelResult();
        if (!repository.deleteById(solarPanelId)) {
            String message = String.format("Solar panel id %s was not found.", solarPanelId);
            result.addErrorMessage(message);
        }
        return result;
    }

    public SolarPanelResult validate(SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        if (solarPanel == null) {
            result.addErrorMessage("Solar panel cannot be null.");
            return result;
        }

        if (solarPanel.getSection() == null || solarPanel.getSection().isBlank()) {
            result.addErrorMessage("Solar panel `section` is required.");
            return result;
        }

        if (solarPanel.getYearInstalled() == null || solarPanel.getYearInstalled().isBlank()) {
            result.addErrorMessage("Solar panel `year installed` is required.");
            return result;
        }

        if (solarPanel.getRow() > 250 || solarPanel.getRow() <= 0) {
            result.addErrorMessage("Solar panel `row` " + solarPanel.getRow() + " is not in range. [1-250]");
        }

        if (solarPanel.getColumn() > 250 || solarPanel.getColumn() <= 0) {
            result.addErrorMessage("Solar panel `column` " + solarPanel.getColumn() + " is not in range. [1-250]");
        }

        if (Integer.parseInt(solarPanel.getYearInstalled()) > 2021) {
            result.addErrorMessage("Solar panel `year installed` " + solarPanel.getYearInstalled() + " is not in range. [2021 or earlier]");
        }

        return result;
    }

}
