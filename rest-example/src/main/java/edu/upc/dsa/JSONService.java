package edu.upc.dsa;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")//porta
public class JSONService {

    //Para los metodos de añadir jugador/personaje/objeto se podrian quitar los parametros y llamar al constructor?
    //Añadir comprobaciones y comprobar comportamiento en caso de error/que no exista algo

    Mundo miMundo;

    public JSONService() {
        miMundo = Mundo.getIntanceMundo();
    }


    @GET
    @Path("/Jugador/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jugador buscarJugador(@PathParam("email") String email) {
        return miMundo.getMapaJugadores().get(email.hashCode());
    }

    @GET
    @Path("/Personaje/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Personatge buscarPersonaje(@PathParam("nombre") String nombre) {
        return miMundo.getMapaPersonajes().get(nombre);
    }

    @GET
    @Path("/objetosPersonaje/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Objeto> objetosPersonaje(@PathParam("nombre") String nombre) {
        return miMundo.getMapaPersonajes().get(nombre).getArrMisObjetos();
    }

    @GET
    @Path("/Objeto/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Objeto buscarObjeto(@PathParam("nombre") String nombre) {
        return miMundo.getMapaObjetos().get(nombre);
    }

    //Expecificar como se quiere añadir un jugador y con que campos, de momento le pasas el email y le asigna el email y la id sin mas
    @POST
    @Path("/nuevoJugador/{jugadorEmail}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoJugador(@PathParam("jugadorEmail") String email) {
        Jugador jugador = new Jugador();
        jugador.setEmail(email);
        jugador.setId(email.hashCode());
        miMundo.getMapaJugadores().put(jugador.getId(), jugador);
        return Response.status(201).entity("Añadido jugador con correo:" +email).build();
    }

    @POST
    @Path("/eliminarJugador/{emailJugador}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarJugador(@PathParam("emailJugador") String email) {
        miMundo.getMapaJugadores().remove(email.hashCode());
        return Response.status(201).entity("Eliminado jugador con correo:" +email).build();
    }


    @POST //  rescate del proyecto
    @Path("/nuevoPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonaje(@PathParam("personajeNombre") String nombre) {
        Personatge personaje = new Personatge();
        personaje.setNombre(nombre);
        miMundo.getMapaPersonajes().put(nombre, personaje);
        return Response.status(201).entity("Añadido personaje con nombre:" +nombre).build();
    }
    /*
    @POST
    @Path("/nuevoPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonaje(Personatge personaje, @PathParam("personajeNombre") String nombre) {
        personaje.setNombre(nombre);
        miMundo.getMapaPersonajes().put(nombre, personaje);
        return Response.status(201).entity("Añadido personaje con nombre:" +nombre).build();
    }
    */

    @POST
    @Path("/eliminarPersonaje/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarPersonaje(@PathParam("personajeNombre") String nombre) {
        miMundo.getMapaPersonajes().remove(nombre);
        return Response.status(201).entity("Eliminado personaje con nombre:" +nombre).build();
    }

    @POST
    @Path("/nuevoObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjeto(@PathParam("objetoNombre") String nombre) {
        Objeto objeto = new Objeto();
        objeto.setNombre(nombre);
        miMundo.getMapaObjetos().put(nombre, objeto);
        return Response.status(201).entity("Añadido objeto con nombre:" +nombre).build();
    }

    /*@POST
    @Path("/nuevoObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjeto(Objeto objeto, @PathParam("objetoNombre") String nombre) {
        objeto.setNombre(nombre);
        miMundo.getMapaObjetos().put(nombre, objeto);
        return Response.status(201).entity("Añadido objeto con nombre:" +nombre).build();
    }*/

    @POST
    @Path("/eliminarObjeto/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarObjeto(@PathParam("objetoNombre") String nombre) {
        miMundo.getMapaObjetos().remove(nombre);
        return Response.status(201).entity("Eliminado objeto con nombre:" +nombre).build();
    }

    @POST
    @Path("/nuevoObjetoPersonaje/{personajeNombre}/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjetoPersonaje(@PathParam("personajeNombre") String nombrePersonaje,
                                         @PathParam("objetoNombre") String nombreObjeto) {

        //Añadir comprobaciones de errores y tal
        miMundo.getMapaPersonajes().get(nombrePersonaje).getArrMisObjetos().add(miMundo.getMapaObjetos().get(nombreObjeto));

        return Response.status(201).entity("Añadido objeto con nombre:" +nombreObjeto +"a personaje" +nombrePersonaje).build();
    }

    @POST
    @Path("/transferirObjeto/{personajeNombre1}/{personajeNombre2}/{objetoNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferirObjeto(@PathParam("personajeNombre1") String nombrePersonaje,
                                     @PathParam("personajeNombre2") String nombrePersonaje2,
                                     @PathParam("objetoNombre") String nombreObjeto) {
        //Comprobacion o dara nullpointer probablemente
        miMundo.getMapaPersonajes().get(nombrePersonaje2).getArrMisObjetos().add(
                            miMundo.buscarObjeto(miMundo.mapaPersonajes.get(nombrePersonaje).arrMisObjetos, nombreObjeto));
        miMundo.getMapaPersonajes().get(nombrePersonaje).getArrMisObjetos().remove(
                miMundo.buscarObjeto(miMundo.mapaPersonajes.get(nombrePersonaje).arrMisObjetos, nombreObjeto));

        return Response.status(201).entity("Transferido el objeto" +nombreObjeto +"a personaje" +nombrePersonaje).build();
    }


    //Ya existente
    @POST
    @Path("/nuevoPersonajeJugador/{jugadorEmail}/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPersonajeJugador(@PathParam("jugadorEmail") String jugadorEmail,
                                          @PathParam("personajeNombre") String personajeNombre) {

        //Añadir comprobaciones de errores y tal
        miMundo.getMapaJugadores().get(jugadorEmail.hashCode()).getPersonatges().add(miMundo.getMapaPersonajes().get(personajeNombre));

        return Response.status(201).entity("Añadido personaje con nombre:" +personajeNombre +"a jugador con email" +jugadorEmail).build();
    }

    @POST
    @Path("/perderResistencia/{personajeNombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void perderResistencia(@PathParam("personajeNombre") String personajeNombre){
        miMundo.getMapaPersonajes().get(personajeNombre).setResistencia(miMundo.getMapaPersonajes().get(personajeNombre).getResistencia()-1);
    }


}
    /*
    //servei d'autentificació arreglar loginsera
    @POST
    @Path("/Jugador/{email}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Jugador Login(@PathParam("email") String email,String contrassenya){
        System.out.println(contrassenya);
        Jugador j = miMundo.consultarUsuarioMail(email);
        if(j.getContrasenya().equals(contrassenya))
        {
            return j;
        }

        return null;
    }
    @GET
    @Path("/Login/{email}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response buscarJugador2(@PathParam("email") String email,@PathParam("password") String password){
     Jugador j = new Jugador();



        return Response.status(101).build();
    }

    @GET
    @Path("/Personaje/{nombrePersonaje}/{idJugador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Personatge buscarPersonaje(@PathParam("nombrePersonaje") String nombrePersonaje, @PathParam("idJugador") int id) {
        return new Personatge("Hamlet",1,1,1,1);
    }
    @GET
    @Path("/Personatge/{nombre}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response AfegirPersonatge(@PathParam("nombre") String nombre) throws Exception{
        Personatge p = new Personatge();
        p.setNombre(nombre);
        try{p.insert();
            System.out.println(p.getId());//id base de dades
            try{

            }
            catch(Exception e)
            {
                return Response.status(201).entity("no s'ha pogut al jugador:" +nombre).build();
            }
            return Response.status(201).entity("afegit el personatge amb nom:" +nombre).build();
        }
        catch(Exception e){e.printStackTrace();
            return Response.status(201).entity("no s'ha pogut afegir el personatge:" +nombre).build();
        }

    }
    @GET
    @Path("/Mapa")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMapa() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper.writeValueAsString( miMundo.mapa);
    }*/


