package edu.upc.dsa.DAOG;

import edu.upc.dsa.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO_InterfaceUserImp extends DAO implements DAO_InterfaceUser {

    public boolean loguejarUsuari(String contrassenya,String email)throws Exception{
    /*    Jugador j = new Jugador();
        j.select("email", "s@hola.com");
        j.getContraseña().equals(contrassenya)
      */

    try{
        Connection conn = getConnection();
        StringBuffer consulta = new StringBuffer("SELECT nom,email,contrasenya FROM jugador");
        consulta.append(" WHERE email = '"+email+"' AND contrasenya ='"+contrassenya+"';");
        try{
            PreparedStatement pstm = conn.prepareStatement(consulta.toString());
            ResultSet resultado = pstm.executeQuery();
            if(resultado.next()){//usuari existeix
               System.out.println("usuari registrat");
               pstm.close();
                return true;
            }
            else{
                System.out.println("usuari no registrat");
                pstm.close();
                return false;
            }
        }
        catch(Exception e){
            System.out.println("no con");
            e.printStackTrace();
            return false;
        }

    }
    catch(Exception e){
        System.out.println("no entra");
        return false;
    }

    }
    protected void insertUsuari()throws Exception{insert();}
    protected boolean UpdateUsuari()throws Exception{
        if(update()==true)
        {
           System.out.println("Usuari actualitzat");
           return true;
        }
        else
            return false;}
    public String recuperarContrassenya(String email){


        return null;
    }
}
