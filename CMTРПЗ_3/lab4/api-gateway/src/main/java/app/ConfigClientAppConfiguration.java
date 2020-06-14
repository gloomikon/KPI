package app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
public class ConfigClientAppConfiguration {
    public ConfigClientAppConfiguration() {
    }

    public String getFirstprop() {
        return firstprop;
    }
    public void setFirstprop(String firstprop) {
        this.firstprop = firstprop;
    }
    private String firstprop;

    public String getSecondprop() {
        return secondprop;
    }

    public void setSecondprop(String secondprop) {
        this.secondprop = secondprop;
    }

    private String secondprop;

    public String getThirdprop() {
        return thirdprop;
    }

    public void setThirdprop(String thirdprop) {
        this.thirdprop = thirdprop;
    }

    private String thirdprop;

    public String getFourthprop() {
        return fourthprop;
    }

    public void setFourthprop(String fourthprop) {
        this.fourthprop = fourthprop;
    }

    private String fourthprop;
}
