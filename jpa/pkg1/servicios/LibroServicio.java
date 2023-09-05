package jpa.pkg1.servicios;

import Utilidades.Utilidad;
import java.util.ArrayList;
import java.util.List;
import static jpa.pkg1.JPA1.input;
import jpa.pkg1.entidades.Autor;
import jpa.pkg1.entidades.Editorial;
import jpa.pkg1.entidades.Libro;

public class LibroServicio {
    
    public void addLibro(AutorService as, EditorialServicio es) throws Exception{
        Libro libro1 = new Libro();
        
        System.out.print("\n-> Ingrese el título del libro: ");
        libro1.setTitulo(input.next());
        System.out.print("-> Ingrese el año del libro: ");
        libro1.setAnio(input.nextInt());
        System.out.print("-> Ingrese el número de ejemplares DISPONIBLES del libro: ");
        libro1.setEjemplares(input.nextInt());
        libro1.setEjemplaresRestantes(libro1.getEjemplares());
        
        if(this.buscarSoloUnLibro(libro1.getTitulo())==null)
        {
            libro1.setAutor(as.crearAutor());
            libro1.setEditorial(es.crearEditorial());
            System.out.println("\n-> LIBRO AGREGADO A LA BASE DE DATOS");
            
             //CREAMOS EL OBJETO EN LA BASE DE DATOS
            Utilidad.em.getTransaction().begin();
            Utilidad.em.persist(libro1);
            Utilidad.em.getTransaction().commit();
        }else{
            System.out.println("\n-> NO SE PUEDE AGREGAR ESE LIBRO, PORQUE YA EXISTE EN LA BASE DE DATOS");
        }
       
    }
    
    
     public Libro buscarSoloUnLibro(String nom) throws Exception{
         try
         {
            return (Libro) Utilidad.em.createQuery("SELECT a FROM Libro a WHERE a.titulo = :titulo").setParameter("titulo", nom).getSingleResult();
         }
         catch(Exception ex)
         {
             Libro extra = null;
             return extra;
         }
    }
    
    
    public void eliminarLibro() throws Exception{
        System.out.print("\n-> Ingrese el título del libro a eliminar: ");
        Libro ex = this.buscarSoloUnLibro(input.next());
        if(ex == null)
        {
            System.out.println("\n-> NO EXISTE EL LIBRO INGRESADO");
        }
        else
        {
            Utilidad.em.getTransaction().begin();
            Utilidad.em.remove(ex);
            Utilidad.em.getTransaction().commit();
            System.out.println("\n-> Eliminación EXITOSA");
        }    
    }
    
    
    public void actualizarLibro() throws Exception{
        System.out.print("\n-> Ingrese el nombre del libro a actualizar: ");
        Libro extra = this.buscarSoloUnLibro(input.next());
        if(extra == null)
        {
            System.out.println("\n-> NO EXISTE EL LIBRO INGRESADO");
        }
        else
        {
            boolean validar = true;
            do
            {
                System.out.println("\n- - - ELIJA QUE ACTUALIZAR - - -");
                System.out.println("1. Titulo");
                System.out.println("2. Año");
                System.out.println("3. Ejemplares");
                System.out.println("4. Ejemplares Prestados");
                System.out.println("5. Ejemplares Restantes");
                System.out.println("6. Editorial");
                System.out.println("7. Autor");
                System.out.println("8. Salir");
                System.out.print("Opción: ");
                switch(input.nextInt())
                {
                    case 1:
                        System.out.print("-> Ingrese el NUEVO título: ");
                        extra.setTitulo(input.next());
                        break;
                    case 2:
                        System.out.print("-> Ingrese el NUEVO año: ");
                        extra.setAnio(input.nextInt());
                        break;
                    case 3:
                        System.out.print("-> Ingrese el NUEVO número de ejemplares: ");
                        extra.setEjemplares(input.nextInt());
                        break;
                    case 4:
                        System.out.print("-> Ingrese el NUEVO número de ejemplares prestados: ");
                        extra.setEjemplaresPrestados(input.nextInt());
                        break;
                    case 5:
                        System.out.print("-> Ingrese el NUEVO número de ejemplares restantes: ");
                        extra.setEjemplaresRestantes(input.nextInt());
                        break;
                    case 6:
                        Editorial editorial = new Editorial();
                        System.out.print("-> Ingrese el ID de la NUEVA editorial: ");
                        editorial.setId(input.next());
                        System.out.print("-> Ingrese el nombre de la NUEVA editorial: ");
                        editorial.setNombre(input.next());
                        editorial.setAlta(true);
                        extra.setEditorial(editorial);
                        break;
                    case 7:
                        Autor autor = new Autor();
                        System.out.print("-> Ingrese el ID del NUEVO autor: ");
                        autor.setId(input.next());
                        System.out.print("-> Ingrese el nombre del NUEVO autor: ");
                        autor.setNombre(input.next());
                        autor.setAlta(true);
                        extra.setAutor(autor);
                        break;
                    case 8:
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
    
    
    public void buscarISBN(){
        System.out.print("\n-> Ingrese el ISBN del libro a buscar: ");
        Libro libro = Utilidad.em.find(Libro.class, input.next());
        if(libro == null)
        {
            System.out.println("\nNO SE ENCONTRO EL LIBRO CON ESE ISBN");
        }
        else
        {
            System.out.println("\nLIBRO ENCONTRADO!!: ");
            System.out.println(libro.toString());
        }
    }
    
    
    public void buscarTitulo(){
        System.out.print("\n-> Ingrese el título a buscar: ");
        String titulo = input.next();
        Libro libro;
        
        try
        {
            libro = (Libro) Utilidad.em.createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo").setParameter("titulo", titulo).getSingleResult();
        }catch(Exception e){
            libro = null;
        }
        
        if(libro == null)
        {
            System.out.println("\n-> NO SE ENCONTRO NINGÚN LIBRO");
        }else{
            System.out.println("\nLIBRO ENCONTRADO!!: ");
            System.out.println(libro.toString());
        }
    }
    
    
    public void buscarLibrosPorAutor(AutorService as) throws Exception{
        List<Libro> libros = new ArrayList();
        
        System.out.print("\n->Ingrese el nombre del autor para buscar sus Libros: "); 
        Autor autor = as.buscarSoloUnAutor(input.next()); 
        //System.out.println(autor.getId()); String au = autor.getId();
        
        libros = (List<Libro>) Utilidad.em.createQuery("SELECT l FROM Libro l WHERE l.autor.id = :autor").setParameter("autor", autor.getId()).getResultList();
        
        if(libros==null){
            System.out.println("\nNO SE ENCONTRARON LIBROS CON ESE NOMBRE");
        }else{
            for (Libro lib : libros) {
                System.out.println(lib.toString());
            }
        }
    }
}
