/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import Compilador.Token ; 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author josed
 */
public class Scanner {
    
    
    
    String nombreArchivo ;
    Map< ArrayList<Integer> , Integer > tablaTransiciones  ; 
    FileInputStream fin ;  
    File file ;
    byte fileContent[] ;
    String buffer ;
    int posicionBuffer  ;
    int lineaActual ;
    ArrayList<Integer> listaEstadosFinales ;
    int estadoActual ;
    ArrayList<String> listaCodigosFamilia ;
    ArrayList<Integer> listaEstadosError ;
    int filaActual ; 
    int columnaInicialActual ; 
    int columnaFinalActual ; 
    ArrayList<Token> listaToken ; 
    ArrayList<int[]> matriz = new ArrayList<>();
    ArrayList<String> columnaMatrizTransicion ;
    ArrayList<Character> listaSeparadores; 
    ArrayList<String> columnaMatrizTransicion2 ;
    TablaTexto tablaTexto ;
    HashMap<Integer, Integer> diccionario ; 
    ArrayList<Integer> listaPosEnter ; 
    
    public Scanner(String nombreArchivo) throws IOException { 
        
        this.nombreArchivo = nombreArchivo ;
        this.tablaTransiciones = new HashMap< ArrayList<Integer>  ,Integer>() ;
        this.file = null ; 
        this.fin  = null ;
        this.posicionBuffer = 0 ; 
        this.listaEstadosFinales = new ArrayList<Integer>() ; 
        this.estadoActual  = 100 ; 
        this.listaCodigosFamilia = new ArrayList<String>();
        this.listaEstadosError = new ArrayList<Integer>() ;
        this.filaActual = 1 ;
        this.columnaInicialActual = 1 ;
        this.columnaFinalActual = 1 ;
        this.listaToken = new ArrayList<Token>() ; 
        this.tablaTexto = new TablaTexto();
        this.diccionario = new HashMap<Integer,Integer>();
        this.listaPosEnter = new ArrayList<Integer>();
        
       
    }
    
    
    public void TomeEsteCaracter(){
        
        this.posicionBuffer-- ; 
    }
    
    
    public char DemeSiguienteCaracter() throws IOException { // Devuelve el siguiente
        
        
        
        // Ciclo para consumir todos los espaciadores
        
        
        
        if(this.posicionBuffer < this.buffer.length()){
 
        
          if ( buffer.charAt(this.posicionBuffer) == '\n' || 
              buffer.charAt(this.posicionBuffer) == '\t' || 
              buffer.charAt(this.posicionBuffer) == ' ' ) {
              
            char espaciador = buffer.charAt(this.posicionBuffer) ;
            if( espaciador == ' ') {
                
                this.posicionBuffer++;
                this.columnaInicialActual++;
               // System.out.println("Espacio en blanco");
                return espaciador ; 
            }
            else if ( espaciador ==  '\n'){
                
               // System.out.println("CAMDIO DE LINEA");
                this.posicionBuffer++;
                this.filaActual++;
                return espaciador ; 
            }
            
            else{
                System.out.println("TAB");
                this.posicionBuffer++; 
                this.columnaInicialActual += 4;
                return espaciador ; 
            }
              
              
          }


        
        if ( this.posicionBuffer < this.buffer.length()  ) {
            
            this.posicionBuffer++ ;
            this.columnaInicialActual++ ;
            
            if(buffer.charAt(this.posicionBuffer-1) == '\n'){
                return buffer.charAt(posicionBuffer); 
            }
            else{
                return buffer.charAt(posicionBuffer-1) ;      
                
            }
            
        }
        
        else{ // Se alcanzo el final del archivo 
            return 0; 
            
        }
        
        
            
            
            
        }
        
        return 0 ; 
        

    }
    
    public void replace(){
        
        
        for(int i = 0 ; i < this.buffer.length(); i++){
            
            if(this.buffer.charAt(i) == '\n'){
                this.listaPosEnter.add(i);
            }
        }
        
    }
      
    
    public void ConsumirEspaciadores(){
        
        
        if(this.posicionBuffer < this.buffer.length() ){
            
        
        while(
                
              
              buffer.charAt(this.posicionBuffer) == '\n' || 
              buffer.charAt(this.posicionBuffer) == '\t' || 
              buffer.charAt(this.posicionBuffer) == ' '  )     {
            
           // System.out.println("Pos Buffer: " + this.posicionBuffer + " Largo del buffer: " + this.buffer.length());
            
            char espaciador = buffer.charAt(this.posicionBuffer) ;
            //System.out.println("LEIDO" + espaciador + "LEIDO");
            if( espaciador == ' ') {
                
                if(this.listaPosEnter.contains(this.posicionBuffer)){  // ES UN ENTER
                    this.posicionBuffer++;
                    this.filaActual++;
                    this.columnaInicialActual = 1 ;
                }
                else{
                    this.posicionBuffer++;
                    this.columnaInicialActual++;
                    
                }
                
                //System.out.println("Espacio en blanco");
            }
            
            else if ( espaciador ==  '\n'){
                
                //System.out.println("CAMDIO DE LINEA");
                this.posicionBuffer++;
                this.filaActual++;
            }
            
            else{
                //System.out.println("TAB");
                this.posicionBuffer++; 
                this.columnaInicialActual += 4;
            }
            


            if(this.posicionBuffer == buffer.length()){
                
                break;
            }
        
        }
        
        
        
        
    }
        
    }
    
    public Token DemeToken() throws IOException {
        
        char obtenido ;
        String lexema = "" ;
        int codigoFamilia = 0 ;
        int error = 0 ;
        int columnaFinal = this.columnaInicialActual ;
        int columnaMatrizTransicion; 
        

        while(!this.listaEstadosFinales.contains(this.estadoActual)) {
            
            if (lexema.equals("")){
                
                //System.out.println("Se consumen los espacios");
                this.ConsumirEspaciadores();
            }
            
            obtenido = this.DemeSiguienteCaracter();
            if(obtenido == '\n'){
                //System.out.println("Found you");
            }
            

            if ( lexema.equals("")) { // 0 representa end  of file 

                if(obtenido == 0 ){
                    
                     if (this.listaEstadosError.contains(this.estadoActual)) {
            
                        // La lista de estados de error contiene los estados finales que representan un error, ordenados por el codigo 
                        error = this.listaEstadosError.indexOf(this.estadoActual) ; 
                    }
                
                    //System.out.println("Convertimos el codigo a cero!");
                    codigoFamilia = 0 ;
                    Token token = new Token(codigoFamilia , "", error ,this.filaActual , this.columnaInicialActual , columnaFinal ) ; 
                    return token ; 
                    
                    
                    
                }
                
         
                
               
            }
            
            
            
            
            if (!lexema.equals("") && this.listaSeparadores.contains(obtenido) || obtenido == 0 ){  // Si es un separador
                
                // Opcion 1
                
                
                if (this.listaEstadosError.contains(this.estadoActual)) {
            
                // La lista de estados de error contiene los estados finales que representan un error, ordenados por el codigo 
                    error = this.listaEstadosError.indexOf(this.estadoActual) ; 
                }
                

                    //String obt  = Character.toString(obtenido);
                    int estadoViejo = this.estadoActual ; 
                    //System.out.println("Codigo de familia viejo: " + this.estadoActual);
                    this.estadoActual = this.tablaTexto.getCelda(this.estadoActual, this.columnaMatrizTransicion.indexOf("term"));
                    //System.out.println("En el estado " + estadoViejo + " con un caracter de " + '"' + "term" + '"' + " llega a " + this.estadoActual);
     
                    codigoFamilia  = this.estadoActual ; 
                    
                    // Se actualiza el estado actual final con el equivalente a la gramatica
                    
                    
                    
                     //System.out.println("Estado Actual: " + this.estadoActual);
                     this.estadoActual = this.diccionario.get(this.estadoActual);
                    // System.out.println("Estado correspondiente a la gramatica: " + this.estadoActual);
                     codigoFamilia = this.estadoActual;
                    
                    // Lista update
                    
                    Token token = new Token(codigoFamilia , lexema, error ,this.filaActual , this.columnaInicialActual , columnaFinal ) ;
                    
                    if(obtenido == 0 ){
                        return token;
                    }
                    else{
                        
                        this.TomeEsteCaracter();
                        return token ; 
                        
                    }
                    
                    
                
                
                // Opcion 2
                /*
                
                this.TomeEsteCaracter();
                columnaMatrizTransicion = this.columnaMatrizTransicion.indexOf("term");
                System.out.println("Char obtenido: " + obtenido + "je" + "Columna: " + columnaMatrizTransicion);
                this.estadoActual = this.tablaTexto.getCelda(this.estadoActual, columnaMatrizTransicion);
                */
                
                    
                }
            
            
            else{
    
                
                int estadoViejo = this.estadoActual ; 
                String obt  = Character.toString(obtenido);
                columnaMatrizTransicion = this.columnaMatrizTransicion.indexOf(obt);
                
               // System.out.println("Obtenido: " + obt);
               // System.out.println("ACTUAL FILA: " + this.estadoActual + " obtenido index col: " + columnaMatrizTransicion);
                this.estadoActual = this.tablaTexto.getCelda(this.estadoActual, columnaMatrizTransicion);
               // System.out.println("En el estado " + estadoViejo + " con un caracter de " + '"' + obt + '"' + " llega a " + this.estadoActual);
                
                lexema += obtenido; 
            
                
                
            }
                
 
            
                
            
        }
        
        if (this.listaEstadosError.contains(this.estadoActual)) {
            
            // La lista de estados de error contiene los estados finales que representan un error, ordenados por el codigo 
            error = this.listaEstadosError.indexOf(this.estadoActual) ; 
        }
        
        
        codigoFamilia = this.estadoActual ;
        
        Token token = new Token(codigoFamilia , lexema, error ,this.filaActual , this.columnaInicialActual , columnaFinal ) ; 
        return token ; 
        
         
        
    }
    

    
    public void InicializarScanner() throws FileNotFoundException, IOException {
        
        
        
        
        // Valida extensión del archivo .lht
        
    
        if(!this.nombreArchivo.endsWith("lht")) { 
            
            System.out.println("Error , el archivo no es de extensión '.lht' ");
            Runtime.getRuntime().halt(0);
                    
        }
        
        // Iniciarlizar automata en estado inicial
        this.estadoActual = 0 ;  // Estado inicial
        
        
        //this.columnaMatrizTransicion =  new ArrayList<Character> ( Arrays.asList('a' ,'b' ,'c' ,'d', 'e' ,'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '!', '=', '<', '>', '[', ']', '(', ')', ',', '.', ':', '&', '?', '#', '_', '^' ,'%', '~', '|' ) );
        
        
        this.columnaMatrizTransicion =  new ArrayList<String>(Arrays.asList( "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "!", "=", "<", ">", "[", "]", "(", ")", ",", ".", ":", "&", "?", "#", "_", "^", "%", "~", "\""  , "'", "|", "@",  "term"));
        
        
        // Se abre  el archivo
        
        this.file = new File(this.nombreArchivo) ; 
        this.fin = new FileInputStream(file) ;
        this.fileContent  = new byte [(int) file.length()] ; 
        try {
            this.fin.read(this.fileContent) ;
        } catch (IOException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.buffer = new String(fileContent) ;
        this.buffer = this.buffer.toLowerCase() ;
        
        
        
       // System.out.println("Archivo de texto: ");
        //System.out.println(buffer);
        
        // Se borran los cambios de linea
        
        
       // System.out.println("Largo Original del archivo: "  + this.buffer.length());
        this.replace();
        buffer = buffer.replace("\n", " ").replace("\r", " ");
        //System.out.println("Largo Original del archivo: "  + this.buffer.length());
        
        
        // Agrega estados de error 
        
        this.listaEstadosError = new ArrayList<Integer> (
        
        Arrays.asList(397, 400 , 401)) ; 
        
        // Agrega  estados finales a lista
        
        
        this.listaEstadosFinales =  new ArrayList<Integer>(
        Arrays.asList(103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 126, 127, 128, 129, 130, 131, 132, 133,  135, 136, 138, 139, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150,151, 153, 156, 159, 166, 169, 171, 178, 181, 184, 189, 200, 208,  211, 218, 227, 233, 238, 240, 242, 251, 256, 260, 263, 268, 272, 274, 281, 282, 289, 295, 301, 302, 307, 312, 317, 320, 328, 331, 332, 337, 340, 342, 344, 347, 351, 356, 358, 363, 368, 369, 370, 375, 379, 386, 389, 390, 405, 413) 
        
        );
        
        
        // Agregar lista de codigos de familia
        this.listaCodigosFamilia = new ArrayList<String>(
        
        Arrays.asList("fe","-", "!", "%", "%-", "%!", "%&", "%?", "%~", "%+", "(", ")", "*", "*=", ",", ".", "/", "/=", ":", ":=", "[", "]", "|", "|*", "|<", "|>", "|><|", "+", "+=", "<", "<#>", "<&>", "<^>", "<_>", "<|", "<<", "<=", "<>", "=", "-=", ">", ">|", ">=", ">>", "akat", "akko", "arrekaan", "arreksek", "arrekvos", "assokh", "athzhokwazar", "che", "emralat", "esinasolat", "evat", "evolat", "evoon", "gache", "ha", "hake", "hanakhaan", "hethkat", "id", "irge", "iste", "kash", "kemat", "khado", "khadokh", "khalassar", "laqat", "lirikh", "literalbooleana", "literalchar", "literalconjunto", "literalnumeral", "literalregistro", "literalstring", "ma", "marilat", "nakho", "save", "she", "shim", "sille", "soroh", "tat", "thikh", "veneser", "verat", "vineserat", "vo", "xche", "yanqokh", "yarat", "yorosor", "rissat", "fenat", "ejervanat", "anaquisan", "govat", "disisse", "vosoakah", "addrivat")
        ) ; 
        
        
        // Agrega separadores a lista 
        
        this.listaSeparadores = new ArrayList<Character>(
        
        Arrays.asList(' ', '\t'  , '\n', '+', '-', '*', '/', '=', '.', '%', '|', '<', '>', ':','[' )
        ) ; 
        
    
        // Diccionarios con equivalentes a estados finales segun la gramatica
        //this.diccionario = new HashMap<Integer,Integer>();
        
       int[] valores = new int[] {103,105,106,107,108,109,110,111,112,116,115,113,114,117,118,119,120,127,128,130,129,121,122,123,124,126,131,132,133,141,135,138,139,142,143,144,145,150,104,146,147,148,149,166,171,169,208,178,181,184,189,200,211,218,227,233,251,242,238,240,256,268,272,282,274,281,289,260,263,320,317,332,331,328,307,312,156,151,159,153,302,301,337,295,347,340,342,344,405,351,358,356,368,363,389,369,370,375,379,386,390,413,398,397,400,401,416,420,423,429,430,435};
       int[] llaves = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112};

       //int[] valores = new int[] {103,105,106,107,108,109,110,111,112,116,115,113,114,117,118,119,120,127,128,130,129,121,122,123,124,126,131,132,133,141,135,138,139,142,143,144,145,150,104,146,147,148,149,166,171,169,208,178,181,184,189,200,211,218,227,233,251,242,238,240,256,268,272,282,274,281,289,260,263,320,317,332,331,328,307,312,156,151,159,153,302,301,337,295,347,340,342,344,405,351,358,356,368,363,389,369,370,375,379,386,390,413,398,397,400,401};
       //int[] llaves = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106};
        
        
        
        int e = 0;
        while(e<valores.length){
            //System.out.println(llaves[e]);
            //System.out.println(valores[e]);
            diccionario.put( valores[e], llaves[e]);
            e++;
        }
        
  
        // Prueba
        
        
        
        while(this.posicionBuffer < this.buffer.length() ) {
            
           //System.out.println("Posicion buffer" + this.posicionBuffer);
           Token token = this.DemeToken();
           if (token == null){
               
               break ; 
           }
          // token.Imprimir();
           
           
              
           this.listaToken.add(token );
           this.estadoActual = 0 ; 

            
        }



        





        System.out.println("Listo");
        
        
        
       
        // Escribir HTML Muro de Ladrillos
       
        String HTML = " Hola \n Bryan" ;  // Este String es el contenido del archivo HTML 
        PrintWriter writer = new PrintWriter("MuroDeLadrillos.html", "UTF-8");
        BufferedWriter out = new BufferedWriter(new FileWriter("MuroDeLadrillos.html"));
        out.write(HTML);
        
    
        
        
    }
    
    public void ImprimirToken(){  // Imprime Todos los Tokens dentro de la lista de Tokens
        
        String informacionToken = "" ; 
        Token token;
        for(int i = 0; i < this.listaToken.size(); i++){
            token = this.listaToken.get(i);
            
            informacionToken = "Lexema: " + token.lexema + " Codigo Familia: " +  token.codigoFamilia + " Codigo de error: " + token.codigoError + " Fila: " + token.fila + " Columna Inicial: " +  token.columnaInicial + " Columna Final: " + token.columnaFinal; 
            System.out.println(informacionToken);
        }
        
    }
    
    
    public int pruebaTransicion(int estadoActual, char caracter){
        
        
        
        String obt = Character.toString(caracter);  
        int nuevoEstado = this.tablaTexto.getCelda(estadoActual, this.columnaMatrizTransicion.indexOf(obt));
        System.out.println(nuevoEstado);
        return nuevoEstado;
    }
    

    
   

    
}
