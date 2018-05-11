/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import Compilador.Scanner;
import Compilador.Parser;
import java.io.IOException;

/**
 *
 * @author josed
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
       //Scanner scanner = new Scanner("C:\\Users\\josed\\OneDrive\\Documents\\NetBeansProjects\\Compilador\\src\\compilador\\lehk.lht");
       //scanner.InicializarScanner();
       //scanner.ImprimirToken();
        
     //   scanner.pruebaTransicion(361, 'a');
       
     Parser parser  = new Parser("C:\\Users\\josed\\OneDrive\\Documents\\NetBeansProjects\\Compilador\\src\\compilador\\lehk.lht");
     parser.Parsing();
        
        
        
    }
    
}
