package models;

import java.util.Arrays;

public class CarModel {
  private String modelName;
  private CarYear[] years;
  public CarModel(String modelName, CarYear[] years) {
    this.modelName = modelName;
    this.years = years;
  }
  public String getModelName() {
    return modelName;
  }
  public void setModelName(String modelName) {
    this.modelName = modelName;
  }
  public CarYear[] getYears() {
    return years;
  }
  public void setYears(CarYear[] years) {
    this.years = years;
  }
  @Override
  public String toString() {
    return "CarModel [modelName=" + modelName + ", years=" + Arrays.toString(years) + "]";
  }


}