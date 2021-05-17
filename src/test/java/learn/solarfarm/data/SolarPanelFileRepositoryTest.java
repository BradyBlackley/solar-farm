package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/test/solarpanel-seed.csv";
    static final String TEST_FILE_PATH = "./data/test/solarpanel-test.csv";

    SolarPanelFileRepository repository = new SolarPanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataAccessException {
        List<SolarPanel> actual = repository.findAll();
        assertEquals(4, actual.size());
    }

    @Test
    void findById() throws DataAccessException {

        SolarPanel solarPanel = repository.findById(1);

        assertNotNull(solarPanel);
        assertEquals("Main", solarPanel.getSection());
        assertEquals(MaterialType.MULTICRYSTALLINE_SILICON, solarPanel.getMaterialType());
        assertFalse(solarPanel.isTracking());

        solarPanel = repository.findById(12345);
        assertNull(solarPanel);

    }

    @Test
    void findBySectionRowColumn() throws DataAccessException {
        SolarPanel actual = repository.findBySectionRowColumn("Main", 1, 1);
        assertNotNull(actual);
        assertEquals("MULTICRYSTALLINE_SILICON", actual.getMaterialType().name());
        assertFalse(actual.isTracking());

        actual = repository.findBySectionRowColumn("Main", 500, 500);
        assertNull(actual);
    }

    @Test
    void findBySection() throws DataAccessException {
        List<SolarPanel> actual = repository.findBySection("Upper Hill");
        assertNotNull(actual);
        assertEquals(1, actual.size());

        actual = repository.findBySection(("Doesn't exist"));
        assertEquals(0, actual.size());
    }

    @Test
    void findByTracking() throws DataAccessException {
        List<SolarPanel> actual = repository.findByTracking(true);
        assertNotNull(actual);
        assertEquals(1, actual.size());

        actual = repository.findByTracking(false);
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    void add() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("East");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled("2021");
        solarPanel.setMaterialType(MaterialType.AMORPHOUS_SILICON);
        solarPanel.setTracking(false);


        SolarPanel actual = repository.add(solarPanel);
        assertEquals("East", actual.getSection());
        assertEquals(1, actual.getRow());
        assertEquals(1, actual.getColumn());

        List<SolarPanel> all = repository.findAll();
        assertEquals(5, all.size());

        actual = all.get(4);
        assertEquals("East", actual.getSection());
        assertEquals(1, actual.getRow());
        assertEquals(1, actual.getColumn());
        assertEquals("2021", actual.getYearInstalled());
        assertEquals(MaterialType.AMORPHOUS_SILICON, actual.getMaterialType());
        assertFalse(actual.isTracking());
    }

    @Test
    void update() throws DataAccessException {
        SolarPanel solarPanel = repository.findBySectionRowColumn("Main", 1, 1);
        solarPanel.setSection("No longer Main");
        assertTrue(repository.update(solarPanel));

        solarPanel = repository.findById(1);
        assertNotNull(solarPanel);
        assertEquals("No longer Main", solarPanel.getSection());

        SolarPanel doesNotExist = new SolarPanel();
        doesNotExist.setId(44321);
        assertFalse(repository.update(doesNotExist));

    }

    @Test
    void deleteById() throws DataAccessException {
        int count = repository.findAll().size();
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(2356));
        assertEquals(count - 1, repository.findAll().size());
    }
}