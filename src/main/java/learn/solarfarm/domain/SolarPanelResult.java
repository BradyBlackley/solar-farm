package learn.solarfarm.domain;

import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelResult {

    private ArrayList<String> messages = new ArrayList<>();
    private SolarPanel solarPanel;

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message){
        messages.add(message);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public SolarPanel getSolarPanel(){
        return solarPanel;
    }

    public void setSolarPanel(SolarPanel solarPanel){
        this.solarPanel = solarPanel;
    }

}
