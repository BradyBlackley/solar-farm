package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelRepositoryDouble implements SolarPanelRepository {

    private ArrayList<SolarPanel> solarPanels = new ArrayList<>();

    public SolarPanelRepositoryDouble() {
        solarPanels.add(new SolarPanel(1,"Main", 1, 1,
                "2014", MaterialType.MULTICRYSTALLINE_SILICON, false));
        solarPanels.add(new SolarPanel(2,"Upper Hill", 1, 1,
                "2014", MaterialType.AMORPHOUS_SILICON, false));
        solarPanels.add(new SolarPanel(2,"Upper Hill", 1, 2,
                "2015", MaterialType.CADMIUM_TELLURIDE, true));
    }

    @Override
    public List<SolarPanel> findAll() throws DataAccessException {
        return new ArrayList<>(solarPanels);
    }

    @Override
    public SolarPanel findById(int id) throws DataAccessException {
        for (SolarPanel solarPanel : solarPanels) {
            if(solarPanel.getId() == id){
                return solarPanel;
            }
        }
        return null;
    }

    @Override
    public SolarPanel findBySectionRowColumn(String section, int row, int column) throws DataAccessException {
        for (SolarPanel solarPanel : solarPanels) {
            if(solarPanel.getSection().equals(section)
                && solarPanel.getRow() == row
                && solarPanel.getColumn() == column){
                return solarPanel;
            }
        }
        return null;
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for(SolarPanel solarPanel : solarPanels){
            if(solarPanel.getSection().equals(section)){
                result.add(solarPanel);
            }
        }
        return result;
    }

    @Override
    public List<SolarPanel> findByTracking(boolean isTracking) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for(SolarPanel solarPanel : solarPanels){
            if(solarPanel.isTracking()){
                result.add(solarPanel);
            }
        }
        return result;
    }

    @Override
    public SolarPanel add(SolarPanel solarPanel) throws DataAccessException {
        return solarPanel;
    }

    @Override
    public boolean update(SolarPanel solarPanel) throws DataAccessException {
        return findById(solarPanel.getId()) != null;
    }

    @Override
    public boolean deleteById(int id) throws DataAccessException {
        return findById(id) != null;
    }
}
