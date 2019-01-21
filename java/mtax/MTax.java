import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {

    public MTax(){

    }

    public static List<String> validate(List<X_Tax> xTaxList) {

        List<String> errorList = new ArrayList<>();

//        List<String> taxCategoryList = MInfoTaxCategory.getTaxCategoryStringList();

        if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            int cont = 0;
            for (X_Tax tax : xTaxList) {
                if(tax.getId() != null){
                    validIds.add(tax.getIdString());
                }
//                if(tax.getAmount() == null) {
//                    errorList.add("El importe es obligatorio");
//                }

                if(tax.getTax() == null) {
                    errorList.add("El impuesto es obligatorio");
                }
//                else if(!taxCategoryList.contains(tax.getTax())) {
//                    errorList.add("El impuesto no es un dato valido");
//                }

//                if(tax.isLocal()){
//                    if(tax.isTrasladado() && tax.getTaxAmount() == null ) {
//                        errorList.add("El importe es obligatorio");
//                    }
//                }
//                else {
//                    if(tax.getTaxAmount() == null ) {
//                        errorList.add("El importe es obligatorio");
//                    }
//                }

                if(!tax.isLocal()){
                    cont++;
                }
            }
            if(cont<=0){
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            if(validIds.size() > 0){

                    List<X_Tax> xt = TaxsByListId(validIds, false);
                    if(xt.size() != validIds.size()){
                        errorList.add("Existen datos no guardados previamente");
                    }else{
                        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();
                        for(X_Tax tax: xt){
                            map_taxs.put(tax.getIdString(), tax);
                        }
                        for(int i = 0; i < xTaxList.size(); i++){
                          String xTxList = xTaxList.get(i);
                            if(xTxList.getId() != null){
                                xTxList.setCreated(
                                        map_taxs.get(xTxList.getIdString())
                                                .getCreated());
                            }
                        }
                    }
            }
        }
//        else {
//            errorList.add("El documento no tiene tasas");
//        }

        return errorList;
    }

}
