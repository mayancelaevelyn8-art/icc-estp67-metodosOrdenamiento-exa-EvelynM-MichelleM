package controllers;
import models.Brand;

public class BrandController {

  public Brand[] sortBubbleDesc(Brand[] brands){
    for(int i =0; i<brands.length -1; i++){
      for(int j=0; j<brands.length -1 -i; j++){
        if(brands[j].getTotalValidYears() <brands[j+1].getTotalValidYears()){
          Brand temp = brands[j];
          brands[j]= brands[j+1];
          brands[j+1] = temp;
        }
      }
    }
    return brands;
  }

  public Brand binarySearchByValidYears(Brand[] brands, int validYears, boolean isAscending){
    

  }
}
