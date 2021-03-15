package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Polynomial implements Comparator<Monomial>{

    private List<Monomial> monomials;

    public List<Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(List<Monomial> monomials) {
        this.monomials = monomials;
        orderPowers();
    }

    /**Sort by power method*/
    private void orderPowers(){

        Collections.sort(monomials,this::compare);
    }

    /**This method returns the polynomial as string*/
    public String getThisPolynomial(int withDecimals){
        String output = "";
        for (Monomial monomial : monomials) {
            output = output + coefficientToDisplay(monomial,withDecimals); /**concatenate the coefficient*/
            if (monomial.getPower() != 0){
                if (monomial.getPower() == 1) {
                    output = output + "x"; /**if power is 1 concatenate only 'x'*/
                }
                else {
                    output = output + "x^" + monomial.getPower(); /**power is greater than 1, concatenate power also*/
                }
            }
        }
        if  (output.length() > 0) {
            if(output.charAt(0) == '+') {
                output = output.substring(1); /**if output's first character is +, delete it*/
            }
        }
        else {
            return "0";
        }
        return output;
    }

    /**Method that calculates the coefficient that must be displayed*/
    private String coefficientToDisplay(Monomial monomial, int withDecimals){
        String output = "";
        int coeff = monomial.getCoefficient().intValue();
        if (monomial.getCoefficient() > 0) {
            output = output + "+"; /**concatenate + if cofficient is positive*/
        }
        if (monomial.getCoefficient() != 1 && monomial.getCoefficient() != -1){ /**if coefficient is not -1 or 1*/
            if (withDecimals == 0) {
                output = output + coeff; /**output gets integers values*/
            }
            else {
                output = output + String.format("%.02f", monomial.getCoefficient()); /**output gets float values*/
            }
        }
        else { /**coefficient is either -1 or 1*/
            if (monomial.getPower() == 0) { /**if power is 0*/
                if (withDecimals == 0) {
                    output = output + coeff; /**concatenate it*/
                }
                else {
                    output = output + monomial.getCoefficient();
                }
            }
            else{
                if (monomial.getCoefficient() == -1) {
                    output = output + "-"; /**if power is not 0 and the coefficient is -1, concatenate the -*/
                }
            }
        }
        return output;
    }

    public int compare(Monomial o1, Monomial o2) {

        return o2.getPower()-o1.getPower();
    }

    public int polynomialDegree(){
        if (!monomials.isEmpty()){
            return monomials.get(0).getPower();
        }
        return 0;
    }
}