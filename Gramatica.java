

package Gramatica;


public abstract class Gramatica
{
	
	public static final int MARCA_DERECHA = 103;

	
	public static final int NO_TERMINAL_INICIAL = 104;

	
	public static final int MAX_LADO_DER = 13;

	
	public static final int MAX_FOLLOWS = 42;

	
	public static final boolean esTerminal(int numSimbolo)
	{
		return ((0 <= numSimbolo) && (numSimbolo <= 103));
	}

	
	public static final boolean esNoTerminal(int numSimbolo)
	{
		return ((104 <= numSimbolo) && (numSimbolo <= 165));
	}

	
	public static final boolean esSimboloSemantico(int numSimbolo)
	{
		return ((166 <= numSimbolo) && (numSimbolo <= 165));
	}

	
	public static final int getTablaParsing(int numNoTerminal, int numTerminal)
	{
		return GTablaParsing.getTablaParsing(numNoTerminal, numTerminal);
	}

	
	public static final int getLadosDerechos(int numRegla, int numColumna)
	{
		return GLadosDerechos.getLadosDerechos(numRegla, numColumna);
	}

	
	public static final String getNombresTerminales(int numTerminal)
	{
		return GNombresTerminales.getNombresTerminales(numTerminal);
	}

	
	public static final int getTablaFollows(int numNoTerminal, int numColumna)
	{
		return GTablaFollows.getTablaFollows(numNoTerminal, numColumna);
	}
}
