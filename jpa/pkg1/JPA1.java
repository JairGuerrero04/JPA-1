package jpa.pkg1;

import Utilidades.Utilidad;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.pkg1.entidades.Autor;
import jpa.pkg1.entidades.Libro;
import jpa.pkg1.servicios.AutorService;
import jpa.pkg1.servicios.ClienteServicio;
import jpa.pkg1.servicios.EditorialServicio;
import jpa.pkg1.servicios.LibroServicio;
import jpa.pkg1.servicios.PrestamoServicio;

public class JPA1 {

    public static Scanner input = new Scanner(System.in).useDelimiter("\n");
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        LibroServicio ls = new LibroServicio();
        EditorialServicio es = new EditorialServicio();
        AutorService as = new AutorService();
        ClienteServicio cs = new ClienteServicio();
        PrestamoServicio ps = new PrestamoServicio();
        
        boolean validar = true;
        int opc = 0;
        
        do
        {
            System.out.println("\n- - - - - - M E N U - - - - - -");
            System.out.println("1. Añadir libro a la Base de Datos");
            System.out.println("2. Añadir una editorial a la Base de Datos");
            System.out.println("3. Añadir un autor a la Base de Datos");
            System.out.println("4. Eliminar AUTOR");
            System.out.println("5. Eliminar EDITORIAL");
            System.out.println("6. Eliminar LIBRO");
            System.out.println("7. Actualización LIBRO");
            System.out.println("8. Actualización EDITORIAL");
            System.out.println("9. Actualización AUTOR");
            System.out.println("10. Busqueda de Autor por nombre");
            System.out.println("11. Busqueda de un Libro por ISBN");
            System.out.println("12. Busqueda de un Libro por título");
            System.out.println("13. Busqueda de libros por autor");
            System.out.println("14. Añadir préstamo/cliente a la BD");
            System.out.println("15. Actualizar préstamo");
            System.out.println("16. Devolución de un libro");
            System.out.println("20. Salir");
            System.out.print("Opción: ");
            opc = input.nextInt();
            switch(opc)
            {
                case 1:
                    ls.addLibro(as, es);
                    break;
                case 2:
                    es.crearEditorial();
                    break;
                case 3:
                    as.crearAutor();
                    break;
                case 4:
                    as.eliminarAutor();
                    break;
                case 5:
                    es.eliminarEditorial();
                    break;
                case 6:
                    ls.eliminarLibro();
                    break;
                case 7:
                    ls.actualizarLibro();
                    break;
                case 8:
                    es.actualizarEditorial();
                    break;
                case 9:
                    as.actualizarAutor();
                    break;
                case 10:
                    System.out.println("\nIngrese el nombre del Autor");
                    String nom = input.next();
                    List<Autor> listaAutores = as.buscarPorNombre(nom);
                    for (Autor lista : listaAutores) {
                        System.out.println(lista.toString());
                    }
                    break;
                case 11: 
                    ls.buscarISBN();
                    break;
                case 12:
                    ls.buscarTitulo();
                    break;
                case 13:
                    ls.buscarLibrosPorAutor(as);
                    break;
                case 14:
                    ps.crearPrestamo(ls, cs);
                    break;
                case 15:
                    ps.actualizarPrestamo(cs, ls);
                    break;
                case 20:
                    validar = false;
                    System.out.println("\nHASTA LUEGO");
                    break;
            }
        
        }while(validar);
    }
    
}
