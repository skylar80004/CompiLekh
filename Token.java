/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Compilador;

/**
 *
 * @author josed
 */
public class Token {
    
    
    int codigoFamilia ;
    String lexema  ; 
    int codigoError  ;
    int fila ; 
    int columnaInicial ; 
    int columnaFinal  ; 
    
    
    public Token(int codigoFamilia , String lexema, int codigoError, int fila, 
            int columnaInicial ,int columnaFinal) { 
        
        
        this.codigoFamilia = codigoFamilia ; 
        this.lexema = lexema; 
        this.codigoError = codigoError; 
        this.fila = fila ; 
        this.columnaInicial = columnaInicial ; 
        this.columnaFinal  = columnaFinal ; 
        
        
    }
    
    public void Imprimir(){
        
        
        
        String informacionToken = "" ; 
        
        informacionToken = "Lexema: " + this.lexema + " Codigo Familia: " +  this.codigoFamilia + " Codigo de error: " + this.codigoError + " Fila: " + this.fila + " Columna Inicial: " +  this.columnaInicial + " Columna Final: " + this.columnaFinal; 
        System.out.println(informacionToken);
        
        
    
    
    }
    
    
    
}
