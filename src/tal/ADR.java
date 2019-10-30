package tal;

import static tal.Token.Type.*;

/**
 * Analizador sintáctico implementado mediante el método
 * descendente recursivo.
 * <p>En esta clase se debe implementar la gramática del lenguaje.
 */
public class ADR extends ASin
{
/**
 * Construye un analizador descendente recursivo.
 * @param lex Analizador léxico.
 */
public ADR(ALex lex)
{
    super(lex);
}

/**
 * Símbolo inicial de la gramática.
 */
public void programa()
{
    declaracion();
    bloque();
    tokenRead(EOF);
}

//------------------------------------------------------------------------
private void declaracion()
{
    switch(tokenType())
    {
        case ENTERO:
            tokenRead(ENTERO);
            tokenRead(ID);
            declaracion();
            break;

        case CADENA:
            tokenRead(CADENA);
            tokenRead(ID);
            declaracion();
            break;
    }
}

//------------------------------------------------------------------------
private void bloque()
{
    switch(tokenType())
    {
        case ID:
            asignacion();
            bloque();
            break;
        
        case IMPRIMIR:
            impresion();
            bloque();
            break;
            
        case SI:
            condicion();
            bloque();
            break;
            
        case MIENTRAS:
            iteracion();
            bloque();
            break;
    }
}


private void asignacion()
{
    tokenRead(ID);
    tokenRead(ASIGN);
    expresion();
}


private void impresion()
{
    tokenRead(IMPRIMIR);
    expresion();
}


private void condicion()
{
    tokenRead(SI);
    expresion();
    bloque();
    sino();
    tokenRead(FIN);
}


private void sino()
{
    if(tokenType() == SINO){
        tokenRead(SINO);
        bloque();
    }
}


private void iteracion()
{
    tokenRead(MIENTRAS);
    expresion();
    bloque();
    tokenRead(FIN);
    
}


private void expresion()
{
    vor();
    vor1();    
}


private void vor()
{
    vand();
    vand1();
}


private void vor1()
{
    if(tokenType() == OR){
        tokenRead(OR);
        vor();
        vor1();
    }
}


private void vand() //aquí no hay landa, entonces el if se hace con if else, o como??
{
    if(tokenType() == NEG){
        tokenRead(NEG);
        vrel();
        vrel1();
    }
    else{
        vrel();
        vrel1();
    }
}


private void vand1()
{
    if(tokenType() == AND){
        tokenRead(AND);
        vand();
        vand1();
    }
}


private void vrel()
{
    if(tokenType() == SUM){
        tokenRead(SUM);
        vsum();
        vsum1();
    }else{
        vsum();
        vsum1();
    }
}

private void vrel1()
{
    if(tokenType() == REL){
        tokenRead(REL);
        vrel();
    }
}


private void vsum()
{
    vmul();
    vmul1();
}


private void vsum1()
{
    if(tokenType() == SUM){
        tokenRead(SUM);
        vsum();
        vsum1();
    }
}


private void vmul()
{
    if(tokenType() == IPAR){
        tokenRead(IPAR);
        expresion();
        tokenRead(DPAR);
    }
    else
        valor();
}


private void vmul1()
{
    if(tokenType() == MUL){
        tokenRead(MUL);
        vmul();
        vmul1();
    }
}


private void valor()
{
    if(tokenType() == ID){
        tokenRead(ID);
    }
    else if(tokenType() == INTVAL){
        tokenRead(INTVAL);
    }
    else
        tokenRead(STRVAL);
    ///hay que hacer else tokenRead(STRVAL) o no?
}


} // ADR
