package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;

import java.util.List;

public interface SolarPanelRepository {

    List<SolarPanel> findAll() throws DataAccessException;

    SolarPanel findById(int id) throws DataAccessException;

    SolarPanel findBySectionRowColumn(String section, int row, int column) throws DataAccessException;

    List<SolarPanel> findBySection(String section) throws DataAccessException;

    List<SolarPanel> findByTracking(boolean isTracking) throws DataAccessException;

    SolarPanel add(SolarPanel solarPanel) throws DataAccessException;

    boolean update(SolarPanel solarPanel) throws DataAccessException;

    boolean deleteById(int id) throws DataAccessException;

}
