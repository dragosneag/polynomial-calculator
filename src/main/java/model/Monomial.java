package model;

public class Monomial {

    private Double coefficient;
    private int power;

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    /**Method that returns the derivative of the initial monomial*/
    public Monomial deriveMonomial(){
        Monomial tempMonomial = new Monomial();
        tempMonomial.setCoefficient(coefficient * power);
        tempMonomial.setPower(power - 1);
        return tempMonomial;
    }

    /**Method that returns the integration of the initial monomial*/
    public Monomial integrateMonomial(){
        Monomial tempMonomial = new Monomial();
        tempMonomial.setPower(power + 1);
        tempMonomial.setCoefficient(coefficient/(power + 1));
        return tempMonomial;
    }
}