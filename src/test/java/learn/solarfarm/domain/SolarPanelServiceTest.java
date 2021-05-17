package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelServiceTest {

    SolarPanelService service;

    @BeforeEach
    void setUp() {
        SolarPanelRepositoryDouble repository = new SolarPanelRepositoryDouble();
        service = new SolarPanelService(repository);
    }

    @Test
    void shouldFindThreeSolarPanels() throws DataAccessException {
        List<SolarPanel> actual = service.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    void shouldFindOneBySectionRowColumn() throws DataAccessException {
        SolarPanel actual = service.findBySectionRowColumn("Main", 1, 1);
        assertNotNull(actual);
        assertEquals("Main", actual.getSection());
        assertEquals(1, actual.getRow());
        assertEquals(1, actual.getColumn());
    }

    @Test
    void shouldNotFindBySectionRowColumn() throws DataAccessException {
        SolarPanel actual = service.findBySectionRowColumn("Doesn't Exist", 1, 1);
        assertNull(actual);
    }

    @Test
    void shouldFindTwoBySection() throws DataAccessException {
        List<SolarPanel> actual = service.findBySection("Upper Hill");
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldNotFindBySection() throws DataAccessException {
        List<SolarPanel> actual = service.findBySection("Doesn't Exist");
        assertEquals(0, actual.size());
    }

    @Test
    void shouldFindByTracking() throws DataAccessException {
        List<SolarPanel> actual = service.findByTracking(true);
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void shouldAddValidSolarPanel() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Lower Hill");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2012");
        solarPanel.setMaterialType(MaterialType.MONOCRYSTALLINE_SILICON);
        solarPanel.setTracking(true);

        SolarPanelResult result = service.add(solarPanel);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddSolarPanelWithAlreadySetId() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setId(1);
        solarPanel.setSection("Lower Hill");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2012");
        solarPanel.setMaterialType(MaterialType.MONOCRYSTALLINE_SILICON);
        solarPanel.setTracking(true);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddSolarPanelWithInvalidDate() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Lower Hill");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2031");
        solarPanel.setMaterialType(MaterialType.MONOCRYSTALLINE_SILICON);
        solarPanel.setTracking(true);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddSolarPanelWithInvalidRow() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Lower Hill");
        solarPanel.setRow(251);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2012");
        solarPanel.setMaterialType(MaterialType.MONOCRYSTALLINE_SILICON);
        solarPanel.setTracking(true);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddSolarPanelWithInvalidColumn() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Lower Hill");
        solarPanel.setRow(1);
        solarPanel.setColumn(-1);
        solarPanel.setYearInstalled("2012");
        solarPanel.setMaterialType(MaterialType.MONOCRYSTALLINE_SILICON);
        solarPanel.setTracking(true);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddSolarPanelInAnOccupiedSectionRowColumn() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Main");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2014");
        solarPanel.setMaterialType(MaterialType.MULTICRYSTALLINE_SILICON);
        solarPanel.setTracking(false);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEmptySection() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2014");
        solarPanel.setMaterialType(MaterialType.MULTICRYSTALLINE_SILICON);
        solarPanel.setTracking(false);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddNullSection() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2014");
        solarPanel.setMaterialType(MaterialType.MULTICRYSTALLINE_SILICON);
        solarPanel.setTracking(false);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEmptyYear() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Main");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("");
        solarPanel.setMaterialType(MaterialType.MULTICRYSTALLINE_SILICON);
        solarPanel.setTracking(false);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddNullYear() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Main");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setMaterialType(MaterialType.MULTICRYSTALLINE_SILICON);
        solarPanel.setTracking(false);

        SolarPanelResult result = service.add(solarPanel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdateFoundSolarPanel() throws DataAccessException {
        SolarPanel solarPanel = service.findById(1);
        solarPanel.setSection("New Section");

        SolarPanelResult result = service.update(solarPanel);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateEmptyContent() throws DataAccessException {
        SolarPanel solarPanel = service.findById(1);
        solarPanel.setSection("");

        SolarPanelResult result = service.update(solarPanel);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`section`"));
    }
}