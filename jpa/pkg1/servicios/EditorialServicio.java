
package jpa.pkg1.servicios;

import Utilidades.Utilidad;
import static jpa.pkg1.JPA1.input;
import jpa.pkg1.entidades.Autor;
import jpa.pkg1.entidades.Editorial;
import jpa.pkg1.entidades.Libro;


public class EditorialServicio {
    
    public Editorial crearEditorial() throws Exception{
        Editorial edi = new Editorial();
        System.out.print("-> Ingrese el nombre de la editorial: ");
        edi.setNombre(input.next());
        
         //BUSCAMOS SI EXISTE EN LA BD, EL NOMBRE DEL AUTOR
         Editorial ediExtra = this.buscarSoloUnaEditorial(edi.getNombre());
          if(!ediExtra.getNombre().equalsIgnoreCase(" "))
           {
               return ediExtra;
           }
           else
           {
              //CREAR OBJETO EN LA BASE DE DATOS, SI NO EXISTE LA EDITORIAL
              Utilidad.em.getTransaction().begin();
              Utilidad.em.persist(edi);
              Utilidad.em.getTransaction().commit();
              return edi;
           }
    }
    
    
     public Editorial buscarSoloUnaEditorial(String nom) throws Exception{
         try
         {
            return (Editorial) Utilidad.em.createQuery("SELECT a FROM Editorial a WHERE a.nombre = :nombre").setParameter("nombre", nom).getSingleResult();
         }
         catch(Exception ex)
         {
             Editorial ediExtra = new Editorial("0"," ",false);
             return ediExtra;
         }
    }
     
     
     public void eliminarEditorial() throws Exception{
        System.out.print("\nIngrese la editorial que quiere eliminar: ");
        Editorial extra = this.buscarSoloUnaEditorial(input.next());
        if(!extra.getNombre().equalsIgnoreCase(" "))
        {
            try
            {
                Utilidad.em.getTransaction().begin();
                Utilidad.em.remove(extra);
                Utilidad.em.getTransaction().commit();
                System.out.println("\n-> EDITORIAL ELIMINADO");
            }
            catch(Exception e)
            {
                System.out.println("\n-> NO SE PUEDE ELIMINAR ESTA EDITORIAL PORQUE HAY UN LIBRO ASOCIADO");
            }
        }else{
            System.out.println("\n-> Lo sentimos, no se encontró esa editorial");
        }
    }
     
     
     public void actualizarEditorial() throws Exception{
        System.out.print("\n-> Ingrese el nombre de la EDITORIAL a actualizar: ");
        Editorial extra = this.buscarSoloUnaEditorial(input.next());
        if(extra == null)
        {
            System.out.println("\n-> NO EXISTE LA EDITORIAL INGRESADA");
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
                        System.out.print("-> Ingrese el NUEVO nombre de la editorial: ");
                        extra.setNombre(input.next());
                        break;
                    case 2:
                        System.out.print("-> Ingrese la alta de la editorial: \n1.Alta\n2.Baja: ");
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
     
}
