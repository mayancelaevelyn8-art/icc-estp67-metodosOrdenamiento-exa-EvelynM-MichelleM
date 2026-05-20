package models;

import java.util.Arrays;

public class Brand {
  private String brandName;
  private CarModel[] models;

  
  public Brand(String brandName, CarModel[] models) {
    this.brandName = brandName;
    this.models = models;
  }
  public String getBrandName() {
    return brandName;
  }
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
  public CarModel[] getModels() {
    return models;
  }
  public void setModels(CarModel[] models) {
    this.models = models;
  }
  
  public int getTotalValidYears() {
    //cada marca barrido de car moddls y cada carmmodelun barrido de caryears si es suma mi contadory se returncontador
    int contador =0;
    for(CarModel model : models){
      for(CarYear year : model.getYears()){
        if(year.isValid()) 
          contador++;
      }
    }
    return contador;
  }

  @Override
  public String toString() {
    return "Brand [brandName=" + brandName + ", models=" + Arrays.toString(models) + "]";
  }

}