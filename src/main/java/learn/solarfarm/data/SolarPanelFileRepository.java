package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.SolarPanel;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SolarPanelFileRepository implements SolarPanelRepository {

    private final String filePath;
    private final String delimiter = ",";

    public SolarPanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<SolarPanel> findAll() throws DataAccessException {
        ArrayList<SolarPanel> allSolarPanels = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                SolarPanel solarPanel = lineToSolarPanel(line);
                if (solarPanel != null) {
                    allSolarPanels.add(solarPanel);
                }
            }
        } catch (FileNotFoundException ex) {
            //ignore
        } catch (IOException ex) {
            throw new DataAccessException("Could not open the file path: " + filePath, ex);
        }
        return allSolarPanels;
    }

    @Override
    public SolarPanel findById(int id) throws DataAccessException {

        List<SolarPanel> all = findAll();
        for (SolarPanel solarPanel : all) {
            if (solarPanel.getId() == id) {
                return solarPanel;
            }
        }
        return null;
    }

    @Override
    public SolarPanel findBySectionRowColumn(String section, int row, int column) throws DataAccessException {

        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.getSection().equals(section)
                    && solarPanel.getRow() == row
                    && solarPanel.getColumn() == column) {
                return solarPanel;
            }
        }
        return null;
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws DataAccessException {

        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.getSection().equals(section)) {
                result.add(solarPanel);
            }
        }
        return result;
    }

    @Override
    public List<SolarPanel> findByTracking(boolean isTracking) throws DataAccessException {

        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.isTracking() == isTracking) {
                result.add(solarPanel);
            }
        }
        return result;
    }

    @Override
    public SolarPanel add(SolarPanel solarPanel) throws DataAccessException {

        List<SolarPanel> all = findAll();
        int nextId = getNextId(all);
        solarPanel.setId(nextId);
        all.add(solarPanel);
        writeToFile(all);
        return solarPanel;
    }

    @Override
    public boolean update(SolarPanel solarPanel) throws DataAccessException {

        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == solarPanel.getId()) {
                all.set(i, solarPanel);
                writeToFile(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataAccessException {

        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    private void writeToFile(List<SolarPanel> solarPanels) throws DataAccessException {

        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (SolarPanel solarPanel : solarPanels) {
                writer.println(solarPanelToLine(solarPanel));
            }
        } catch (IOException ex) {
            throw new DataAccessException("Could not write to file path: " + filePath, ex);
        }

    }

    private SolarPanel lineToSolarPanel(String line) {
        String[] fields = line.split(delimiter);

        if (fields.length != 7) {
            return null;
        }

        return new SolarPanel(
                Integer.parseInt(fields[0]),
                fields[1],
                Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]),
                fields[4],
                MaterialType.valueOf(fields[5]),
                "true".equals(fields[6])
        );
    }

    private String solarPanelToLine(SolarPanel solarPanel) {

        StringBuilder buffer = new StringBuilder(100);
        buffer.append(solarPanel.getId()).append(delimiter);
        buffer.append(cleanField(solarPanel.getSection())).append(delimiter);
        buffer.append(solarPanel.getRow()).append(delimiter);
        buffer.append(solarPanel.getColumn()).append(delimiter);
        buffer.append(cleanField(solarPanel.getYearInstalled())).append(delimiter);
        buffer.append(solarPanel.getMaterialType()).append(delimiter);
        buffer.append(solarPanel.isTracking());
        return buffer.toString();

    }

    private String cleanField(String field) {

        return field.replace(delimiter, "")
                .replace("/r", "")
                .replace("/n", "");
    }

    private int getNextId(List<SolarPanel> all) {
        int maxId = 0;
        for (SolarPanel solarPanel : all) {
            if (maxId < solarPanel.getId()) {
                maxId = solarPanel.getId();
            }
        }
        return maxId + 1;
    }

}
