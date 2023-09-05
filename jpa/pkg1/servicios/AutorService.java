
package jpa.pkg1.servicios;

import Utilidades.Utilidad;
import java.util.List;
import static jpa.pkg1.JPA1.input;
import jpa.pkg1.entidades.Autor;
import jpa.pkg1.entidades.Editorial;


public class AutorService {
    
    public Autor crearAutor() throws Exception{
        Autor autor = new Autor();
        System.out.print("-> Ingrese el nombre del Autor: ");
        autor.setNombre(input.next());
        
        //BUSCAMOS SI EXISTE EN LA BD, EL NOMBRE DEL AUTOR
        Autor autorExtra = this.buscarSoloUnAutor(autor.getNombre());
        if(!autorExtra.getNombre().equalsIgnoreCase(" "))                        //PARA NO CREAR UN OBJ. PODEMOS CHECAR CON autorExtra == null;
        {
            return autorExtra;
        }
        else
        {
            //CREAR OBJETO EN LA BASE DE DATOS, SI NO EXISTE EL AUTOR
            Utilidad.em.getTransaction().begin();
            Utilidad.em.persist(autor);
            Utilidad.em.getTransaction().commit();
            return autor;
        }
    }
    
    
    public Autor buscarSoloUnAutor(String nom) throws Exception{
        try
        {
            return (Autor) Utilidad.em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre").setParameter("nombre", nom).getSingleResult();
        }
        catch(Exception ex)
        {
            Autor autorExtra = new Autor("0"," ",false);
            return autorExtra;
        }
    }
    
    
    public void eliminarAutor() throws Exception{
        System.out.print("\nIngrese el autor que quiere eliminar: ");
        Autor extra = this.buscarSoloUnAutor(input.next());
        if(!extra.getNombre().equalsIgnoreCase(" "))
        {
            try
            {
                Utilidad.em.getTransaction().begin();
                Utilidad.em.remove(extra);
                Utilidad.em.getTransaction().commit();
                System.out.println("\n-> AUTOR ELIMINADO");
            }
            catch(Exception e)
            {
                System.out.println("\n-> NO SE PUEDE ELIMINAR ESTE AUTOR PORQUE HAY UN LIBRO ASOCIADO");
            }
        }else{
            System.out.println("\n-> Lo sentimos, no se encontró ese autor");
        }
    }
    
    
    public void actualizarAutor() throws Exception{
        System.out.print("\n-> Ingrese el nombre del AUTOR a actualizar: ");
        Autor extra = this.buscarSoloUnAutor(input.next());
        if(extra == null)
        {
            System.out.println("\n-> NO EXISTE EL AUTOR INGRESADO");
        }
        else
        {
             boolean validar = true;
            do
            {
                System.out.println("\n- - - ELIJA QUE ACTUALIZAR - - -");
                System.out.println("1. Nombre");
                System.out.println("2. Alta o Baja");
                System.out.println("3. Salir");
                System.out.print("Opción: ");
                switch(input.nextInt())
                {
                    case 1:
                        System.out.print("-> Ingrese el NUEVO nombre del autor: ");
                        extra.setNombre(input.next());
                        break;
                    case 2:
                        System.out.print("-> Ingrese la alta del autor: \n1.Alta\n2.Baja: ");
                        if(input.nextInt() == 1)
                        {
                            extra.setAlta(true);
                        }else{
                            extra.setAlta(false);
                        }
                        break;
                    case 3:
                        validar = false;
                        break;
                }
            }while(validar);
            Utilidad.em.getTransaction().begin();
            Utilidad.em.merge(extra);
            Utilidad.em.getTransaction().commit();
            System.out.println("\n-> Actualización EXITOSA");
        }
     }
    
    
    
    public List<Autor> buscarPorNombre(String nom){
        return (List<Autor>) Utilidad.em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre").setParameter("nombre", nom).getResultList();
    }
}
