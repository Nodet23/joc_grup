package edu.upc.dsa;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.List;

public class JugadorTest extends TestCase {

    public void testCreacioJugador()
    {
        try {
            SingletonMundo instanciaunica = SingletonMundo.getInstance();
            Usuario Prueba = new Usuario("Gnomo", "atacant", 10,20, 30,40);
            Jugador Anna = new Jugador("Anna", "marianet", "marianet9990", Prueba);
        }
        catch (Exception e)
        {
            fail();
        }
    }


}
