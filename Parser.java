/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import Compilador.Scanner ; 

import Gramatica.*;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;


import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayDeque;
import java.util.Deque;





/**
 *
 * @author josed
 */
public class Parser {
    
    Scanner scanner ; 
    String nombreArchivo;
    Token TA;               // Token Actual
    Deque<Integer> pilaParsing ; 
    int codigoEOF;
    int EAP; // Elemento actual en proceso
    int regla ; // Regla potencial a utilizar
    int simboloInicial;
    int MAXLADODER;
    int contador = 0 ; 
    
    
    public Parser(String nombreArchivo) throws IOException{
        
       this.nombreArchivo = nombreArchivo ; 
       this.scanner = new Scanner(this.nombreArchivo);
       this.pilaParsing = new ArrayDeque<Integer>();
       this.codigoEOF = 0 ;
       this.MAXLADODER = Gramatica.MAX_LADO_DER;
       this.simboloInicial = Gramatica.NO_TERMINAL_INICIAL ; 
    }
    
    
    public void Parsing() throws IOException{
       
        
       this.scanner.InicializarScanner();
       this.TA = this.scanner.DemeToken();
       
       this.TA = this.scanner.listaToken.get(contador);
       contador++;
       
       
        System.out.println("Impresión desde Parser: ---------------------------------------");
       this.TA.Imprimir();
       this.pilaParsing.push(this.simboloInicial);  // Push simbolo incial
       
       
        
       while(this.TA.codigoFamilia != this.codigoEOF){
           
           //System.out.println("CICLO");
           
           try{
               
               this.EAP = this.pilaParsing.pop();
               //System.out.println("EAP: " + this.EAP);
               
           }
           
           catch(Exception e){
               break;
           }
           
           //System.out.println("EAP:" + EAP);
           
           if ( this.Terminal(EAP)){
               
               //System.out.println("El simbolo: " + this.EAP + " es terminal");
               if (this.EAP == this.TA.codigoFamilia -1){
                   
                   this.TA = this.scanner.DemeToken();
                   
                   this.TA = this.scanner.listaToken.get(contador);
                   contador++;
       
                   this.TA.Imprimir();
               }
               else{
                   
                   this.ErrorSintactico(this.TA, this.EAP);
                   
               }
           }
           else{
               
               //System.out.println("El simbolo: " + this.EAP + " no es terminal");
               int fila = abs(EAP - this.simboloInicial) ; 
               int col  = this.TA.codigoFamilia - 1 ; 
               //System.out.println("Fila en tP: " + fila + " Columna TP: " + col);
               this.regla = GTablaParsing.getTablaParsing( abs(EAP-this.simboloInicial) , this.TA.codigoFamilia-1 );
               //System.out.println("Regla: " + this.regla);
                
               if(this.regla < 0){
                   
                   this.ErrorSintactico(this.regla);
               }
               else{
                   int i = 0 ;
                   int ladoDerecho = GLadosDerechos.getLadosDerechos(this.regla,i);
                   //System.out.println("Lado Derecho: " + ladoDerecho);
                           while(  ladoDerecho > -1 && i < this.MAXLADODER){
                               this.pilaParsing.push(GLadosDerechos.getLadosDerechos(this.regla,i++));
                           }
               }
               
               
               
           }
           
       }
       
       
       if (!this.pilaParsing.isEmpty()){
           System.out.println("Pila: " + this.pilaParsing);
           this.ErrorSintactico();
       }
       
       // System.out.println("Parsing Completado");
       
       
        
    }
    
    public void ErrorSintactico(){ // EOF inesperado
        
        System.out.println("Error: Fin de Archivo Inesperado");
    }
    
    public void ErrorSintactico(int Regla){
        
        System.out.println("Error Sintactico con la palabra reservada:" + this.TA.lexema + " En la Linea: " + this.TA.fila + " Columna: " + this.TA.columnaInicial);
        
        
    }
    public void ErrorSintactico(Token TA, int EAP){
        
        String error = " Error Sintáctico: \n se esperaba " + TA.lexema +  " y se recibió "  + Integer.toString(EAP) + " En la Linea: " + this.TA.fila + " Columna: " + this.TA.columnaInicial;
    }
    
    public boolean Terminal(int EAP){
        
        if(EAP <= 103) {
            return true;
        }
        else{
            return false;
        }
    }
    
}
