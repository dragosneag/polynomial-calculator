package controller;

import model.Monomial;
import model.Polynomial;
import view.ValidationFailedException;

import java.util.ArrayList;
import java.util.List;

/**Class that assures the connection between the model and view classes*/

public class Operation {

    private Polynomial polynomial1;
    private Polynomial polynomial2;
    private Polynomial result = new Polynomial();
    private Polynomial remainderOfDivision = new Polynomial();

    public Operation(String poly1, String poly2) {

        Conversion conversion = new Conversion();
        conversion.setFirstPolynomial(poly1);
        conversion.setSecondPolynomial(poly2);
        try {
            conversion.decodify();
        } catch (ValidationFailedException exception){
            throw new ValidationFailedException("Wrong input!");
        }
        this.polynomial1 = conversion.getPolynomial1();
        this.polynomial2 = conversion.getPolynomial2();
    }

    public Polynomial getPolynomial1() {
        return polynomial1;
    }

    public void setPolynomial1(Polynomial polynomial1) {
        this.polynomial1 = polynomial1;
    }

    public Polynomial getPolynomial2() {
        return polynomial2;
    }

    public void setPolynomial2(Polynomial polynomial2) {
        this.polynomial2 = polynomial2;
    }

    public Polynomial getResult() {
        return result;
    }

    public void setResult(Polynomial result) {
        this.result = result;
    }

    public Polynomial getRemainderOfDivision() {
        return remainderOfDivision;
    }

    public void polynomialSum(){
        result.setMonomials(polynomialSumSubtraction(polynomial1,polynomial2,1));
    }

    public void polynomialSubtraction(){
        result.setMonomials(polynomialSumSubtraction(polynomial1,polynomial2,0));
    }

    public List<Monomial> polynomialSumSubtraction(Polynomial poly1, Polynomial poly2, int isSum) {
        List<Monomial> monomialList1 = poly1.getMonomials();
        List<Monomial> monomialList2 = poly2.getMonomials();
        List<Monomial> resultList = new ArrayList<Monomial>();
        int poynomialPower1 = 0;
        int poynomialPower2 = 0;
        if (!monomialList1.isEmpty()) {
            poynomialPower1 = monomialList1.get(0).getPower(); /**determines max power of polynom 1*/
        }
        if (!monomialList2.isEmpty()) {
            poynomialPower2 = monomialList2.get(0).getPower(); /**determines max power of polynom 2*/
        }
        int pow = Math.max(poynomialPower1, poynomialPower2); /**determines the greatest power out of both polynomials*/
        while (pow >= 0) {
            Double coeff = searchPower(monomialList1, monomialList2, pow, isSum); /**determines coefficient at power pow*/
            if (coeff != 0.0) { /**if it's not 0, a new monomial is created and added to the list*/
                Monomial monomial = new Monomial();
                monomial.setPower(pow);
                monomial.setCoefficient(coeff);
                resultList.add(monomial);
            }
            pow--; /**iteration continues*/
        }
        return resultList;
    }

    private Double searchPower(List<Monomial> monomialList1, List<Monomial> monomialList2, int pow, int isSum){
        Double coeff = 0.0;
        for (Monomial monomial : monomialList1) {
            if(monomial.getPower() == pow){ /**searches for power pow in monomialList1*/
                coeff = coeff + monomial.getCoefficient(); /**adds the coefficient if found*/
            }
        }
        for (Monomial monomial : monomialList2) {
            if(monomial.getPower() == pow){ /**searches for power pow in monomialList2*/
                if(isSum == 1){
                    coeff = coeff + monomial.getCoefficient(); /**adds the coefficient if found and the result is Sum*/
                }
                else {
                    coeff = coeff - monomial.getCoefficient(); /**subtracts the coefficient if found and the result is not Sum*/
                }
            }
        }
        return coeff;
    }

    public void polynomialMultiplication(){
        result.setMonomials(polynomialGeneralMultiplication(polynomial1,polynomial2));
    }

    public List<Monomial> polynomialGeneralMultiplication(Polynomial poly1, Polynomial poly2){
        List<Monomial> monomialList1 = poly1.getMonomials();
        List<Monomial> monomialList2 = poly2.getMonomials();
        List<Monomial> resultList = new ArrayList<Monomial>();
        for (Monomial monomial : monomialList1) { /**iterates through the first polynomial*/
            List<Monomial> resultListTemp = new ArrayList<Monomial>();
            for (Monomial monomial1 : monomialList2) { /**iterates through the second polynomial*/
                Monomial tempMonomial = new Monomial();
                tempMonomial.setCoefficient(monomial.getCoefficient()*monomial1.getCoefficient()); /**multiplies monomial from poly1 with each monomial from poly2*/
                tempMonomial.setPower(monomial.getPower()+monomial1.getPower());
                resultListTemp.add(tempMonomial); /**adds each multiplication to the temporary result*/
            }
            Polynomial tempPolynomial1 = new Polynomial();
            Polynomial tempPolynomial2 = new Polynomial();
            tempPolynomial1.setMonomials(resultList);
            tempPolynomial2.setMonomials(resultListTemp);
            resultList = polynomialSumSubtraction(tempPolynomial1,tempPolynomial2,1); /**temporary result is added to the final result*/
        }
        return resultList;
    }

    public void polynomialDivision(){

        result.setMonomials(polynomialGeneralDivision(polynomial1,polynomial2));
    }

    public List<Monomial> polynomialGeneralDivision(Polynomial poly1, Polynomial poly2){
        List<Monomial> monomialList1 = new ArrayList<Monomial>();
        List<Monomial> monomialList2 = new ArrayList<Monomial>();
        monomialList1 = poly1.getMonomials();
        monomialList2 = poly2.getMonomials();
        if (poly1.polynomialDegree() < poly2.polynomialDegree()) {
            swapLists(monomialList1, monomialList2); /**orders polynomials by their rank*/
        }
        List<Monomial> resultList = new ArrayList<Monomial>();
        int ok = 1;
        while (ok == 1) {
            Polynomial tempPolynomial1 = new Polynomial();
            Polynomial tempPolynomial2 = new Polynomial();
            Polynomial tempPolynomial3 = new Polynomial();
            if(monomialList1.get(0).getPower() == 0 || monomialList2.get(0).getPower() == 0){
                ok = 0;
            }
            Monomial monomial = initializeMonomial(monomialList1.get(0).getCoefficient() / monomialList2.get(0).getCoefficient(),monomialList1.get(0).getPower() - monomialList2.get(0).getPower());
            resultList.add(monomial); /**divides the first monomial of poly1 with the first monomial of poly2*/
            List<Monomial> tempMonomialList = new ArrayList<Monomial>();
            tempMonomialList.add(monomial); /**adds quotiend to result*/
            tempPolynomial1.setMonomials(monomialList2);
            tempPolynomial2.setMonomials(tempMonomialList);
            tempPolynomial3.setMonomials(polynomialGeneralMultiplication(tempPolynomial1,tempPolynomial2)); /**multiplies poly2 with quotient*/
            tempPolynomial1.setMonomials(monomialList1);
            tempPolynomial2.setMonomials(polynomialSumSubtraction(tempPolynomial1,tempPolynomial3,0)); /**subtracts the result from poly1*/
            monomialList1 = tempPolynomial2.getMonomials(); /**This is the remainder*/
            remainderOfDivision = tempPolynomial2;
            tempPolynomial1.setMonomials(monomialList2);
            if(tempPolynomial2.polynomialDegree() < tempPolynomial1.polynomialDegree()) {
                ok = 0;
            }
        }
        return resultList;
    }

    private Monomial initializeMonomial(Double coefficient, int power){
        Monomial monomial = new Monomial();
        monomial.setCoefficient(coefficient);
        monomial.setPower(power);
        return monomial;
    }

    private void swapLists(List<Monomial> monomialList1, List<Monomial> monomialList2){
        List<Monomial> auxList = new ArrayList<Monomial>(monomialList1);
        monomialList1.clear();
        monomialList1.addAll(monomialList2);
        monomialList2.clear();
        monomialList2.addAll(auxList);
    }

    public void polynomialDerivative(Polynomial poly){
        result.setMonomials(polynomialGeneralDerivative(poly));
    }

    public List<Monomial> polynomialGeneralDerivative(Polynomial poly){
        Polynomial polynomialCopy = poly;
        List<Monomial> resultList = new ArrayList<Monomial>();
        List<Monomial> monomialList = polynomialCopy.getMonomials();
        for (Monomial monomial : monomialList) { /**iterate through polynomial*/
            Monomial tempMonomial = monomial.deriveMonomial(); /**obtain the derivative of the monomial*/
            if(tempMonomial.getPower() >= 0) {
                resultList.add(tempMonomial); /**adds it to the result if the power is at least 0*/
            }
        }
        return resultList;
    }

    public void polynomialIntegration(Polynomial poly){
        result.setMonomials(polynomialGeneralIntegration(poly));
    }

    public List<Monomial> polynomialGeneralIntegration(Polynomial poly){
        List<Monomial> resultList = new ArrayList<Monomial>();
        List<Monomial> monomialList = poly.getMonomials();
        int markZero = 0;
        for (Monomial monomial : monomialList) { /**iterate through polynomial*/
            Monomial tempMonomial = monomial.integrateMonomial(); /**obtain the integration of the monomial*/
            if(tempMonomial.getPower() >= 0 && tempMonomial.getCoefficient() != 0.0) {
                resultList.add(tempMonomial); /**adds it to the result if the power is at least 0 and coefficient is different than 0*/
            }
            else {
                if(tempMonomial.getPower() == 1 && tempMonomial.getCoefficient() == 0.0){
                    markZero = 1;
                }
            }
        }
        if(resultList.isEmpty() && markZero == 1){ /**if the only monomial in the polynomial was of power 0*/
            Monomial tempMonomial = new Monomial();
            tempMonomial.setPower(0);
            tempMonomial.setCoefficient(0.0); /**adds value 0 to the result*/
            resultList.add(tempMonomial);
        }
        return resultList;
    }
}