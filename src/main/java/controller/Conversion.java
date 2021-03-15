package controller;

import model.Monomial;
import model.Polynomial;
import view.ValidationFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversion {

    private String firstPolynomial;
    private String secondPolynomial;
    private Polynomial polynomial1 = new Polynomial();
    private Polynomial polynomial2 = new Polynomial();

    public String getFirstPolynomial() {
        return firstPolynomial;
    }

    public void setFirstPolynomial(String firstPolynomial) {
        this.firstPolynomial = firstPolynomial;
    }

    public String getSecondPolynomial() {
        return secondPolynomial;
    }

    public void setSecondPolynomial(String secondPolynomial) {
        this.secondPolynomial = secondPolynomial;
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

    /**The method converts string polynomial into polynomial type*/
    public void decodify(){
        if (checkFormat(firstPolynomial) && checkFormat(secondPolynomial)) {
            String str = " ";
            firstPolynomial = firstPolynomial.replaceAll("\\+", " +");
            firstPolynomial = firstPolynomial.replaceAll("-", " -");
            secondPolynomial = secondPolynomial.replaceAll("\\+", " +");
            secondPolynomial = secondPolynomial.replaceAll("-", " -");
            List<String> array1 = Arrays.asList(firstPolynomial.split(str));
            List<String> array2 = Arrays.asList(secondPolynomial.split(str));
            List<Monomial> monomials1 = new ArrayList<Monomial>();
            List<Monomial> monomials2 = new ArrayList<Monomial>();
            for (String s : array1) {
                if (s.length() > 0) {
                    monomials1.add(initializeMonomial(calculateCoefficient(s),calculatePower(s))); /**Calculates coefficient and power for each monomial of polynomial 1*/
                }
            }
            polynomial1.setMonomials(monomials1);
            for (String s : array2) {
                monomials2.add(initializeMonomial(calculateCoefficient(s),calculatePower(s))); /**Calculates coefficient and power for each monomial of polynomial 1*/
            }
            polynomial2.setMonomials(monomials2);
        }
        else {
            throw new ValidationFailedException("Wrong input!");
        }
    }

    private Monomial initializeMonomial(Double coefficient, int power){
        Monomial monomial = new Monomial();
        monomial.setCoefficient(coefficient);
        monomial.setPower(power);
        return monomial;
    }

    /**Method that extracts the coefficient from strings and converts it into numerical value*/
    public Double calculateCoefficient(String s){
        int i;
        String coefficient = "";
        Double dCoefficient;
        i = 0;
        while (i < s.length() && s.charAt(i) != 'x') {
            coefficient = coefficient + s.charAt(i);
            i++; /**Iterate until character 'x' is met*/
        }
        if (coefficient.length() > 0) { /**check if there exits a coefficient*/
            if (coefficient.charAt(0) != '-' && coefficient.charAt(0) != '+') {
                dCoefficient = Double.parseDouble(coefficient); /**if there is no sign, convert straight away*/
            }
            else {
                if (coefficient.length() != 1) {
                    dCoefficient = Double.parseDouble(coefficient.substring(1)); /**convert if there exists a coefficient*/
                }
                else {
                    return 1.0;
                }
                if (coefficient.charAt(0) == '-') {
                    dCoefficient = (-1) * dCoefficient; /**if sign is - multiply by -1*/
                }
            }
            return dCoefficient;
        } else {
            return 1.0;
        }
    }

    /**Method that extracts the power from strings and converts it into numerical value*/
    public int calculatePower(String s){
        int i;
        String coefficient = "";
        i = 0;
        while (i < s.length() && s.charAt(i) != '^'){
            i++; /**Iterate until character '^' is met*/
        }
        if(i == s.length()){
            if (i > 0 && s.charAt(i - 1) == 'x'){ /**if there is no '^' check if there is an x*/
                return 1; /**if yes => power 1*/
            }
            else {
                return 0; /**if no => power 0*/
            }
        }
        else {
            i++;
            while (i < s.length()) { /**if there is '^', iterate until the end of string and convert*/
                coefficient = coefficient + s.charAt(i);
                i++;
            }
            return Integer.parseInt(coefficient);
        }
    }

    private boolean checkFormat(String string){
        Pattern pattern = Pattern.compile("([+-]?\\d*(x\\^|x)?\\d*)+"); /**check if string matches pattern*/
        Matcher matcher = pattern.matcher(string);
        boolean check = matcher.matches();
        return check;
    }
}