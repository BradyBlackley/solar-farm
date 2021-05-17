package learn.solarfarm.models;

public class SolarPanel {

    private int id;
    private String section;
    private int row;
    private int column;
    private String yearInstalled;
    private MaterialType materialType;
    private boolean isTracking; //installed with sun-tracking software or not

    public SolarPanel() {

    }

    public SolarPanel(int id, String section, int row, int column, String yearInstalled, MaterialType materialType, boolean isTracking) {

        this.id = id;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.materialType = materialType;
        this.isTracking = isTracking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(String yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }
}
