
package jpa.pkg1.servicios;

import Utilidades.Utilidad;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static jpa.pkg1.JPA1.input;
import jpa.pkg1.entidades.Cliente;
import jpa.pkg1.entidades.Libro;
import jpa.pkg1.entidades.Prestamo;

public class PrestamoServicio {
    
    public void crearPrestamo(LibroServicio ls, ClienteServicio cs) throws Exception{
        Prestamo prestamo = new Prestamo();
        Date fechaInicio = new Date();
        Date fechaFinal = new Date();
        
        System.out.print("\n-> Ingrese el día de INICIO del préstamo: ");
        fechaInicio.setDate(input.nextInt());
        System.out.print("\n-> Ingrese el mes de INICIO del préstamo: ");
        fechaInicio.setMonth(input.nextInt()-1);
        System.out.print("\n-> Ingrese el año de INICIO del préstamo: ");
        fechaInicio.setYear(input.nextInt()-1900);
        
        System.out.print("\n-> Ingrese el día que REGRESARÁ el libro: ");
        fechaFinal.setDate(input.nextInt());
        System.out.print("\n-> Ingrese el mes que REGRESARÁ el libro: ");
        fechaFinal.setMonth(input.nextInt()-1);
        System.out.print("\n-> Ingrese el año que REGRESARÁ el libro: ");
        fechaFinal.setYear(input.nextInt()-1900);
      
        System.out.print("\n-> Ingrese el nombre del libro a buscar para hacer el PRÉSTAMO: ");
        Libro aux = ls.buscarSoloUnLibro(input.next());
        if(aux == null)
        {
            System.out.println("\n-> NO SE ENCONTRÓ NINGÚN LIBRO CON ESE NOMBRE");
        }else{
            prestamo.setFechaInicio(fechaInicio);
            prestamo.setFechaTermino(fechaFinal);
            prestamo.setLibro(aux);
            prestamo.setCliente(cs.crearCliente());
            
            Utilidad.em.getTransaction().begin();
            Utilidad.em.persist(prestamo);
            Utilidad.em.getTransaction().commit();
        }
    }
    
    
    public void actualizarPrestamo(ClienteServicio cs, LibroServicio ls) throws Exception{
        String nom = " ", ape=" ", libroNom = " ";
        Date fecha = new Date();  Date fecha2 = new Date();
        
        System.out.print("\n-> Ingrese el nombre del cliente: "); nom = input.next();
        System.out.print("\n-> Ingrese el apellido del cliente: "); ape = input.next();
        System.out.print("\n-> Ingrese el nombre del libro a BUSCAR: "); libroNom = input.next();
        Cliente cliente = cs.buscarCliente(nom, ape); Libro libro = ls.buscarSoloUnLibro(libroNom);
        
        if(cliente == null || libro == null)
        {
            System.out.println("\n-> NO SE ENCONTRO EL CLIENTE O EL LIBRO PARA MODIFICAR SU PRÉSTAMO");
        }
        else
        {
            boolean validar = true;
            Prestamo prestamo = this.buscarPrestamo(cliente.getId(), libro.getTitulo());
            if(prestamo == null)
            {
                System.out.println("\n-> NO EXISTE NINGÚN PRESTAMO CON EL CLIENTE: "+cliente.getNombre()+" "+cliente.getApellido());
            }else{
            do
            {
                System.out.println("\n- - - ELIJA QUE ACTUALIZAR - - -");
                System.out.println("1. Fecha de Inicio");
                System.out.println("2. Fecha de Termino");
                System.out.println("3. Libro");
                System.out.println("4. Cliente");
                System.out.println("5. Salir");
                System.out.print("Opción: ");
                switch(input.nextInt())
                {
                    case 1:
                        System.out.print("\n-> Ingrese el NUEVO día para el préstamo: ");
                        fecha.setDate(input.nextInt());
                        System.out.print("\n-> Ingrese el NUEVO mes para el préstamo: ");
                        fecha.setMonth(input.nextInt()-1);
                        System.out.print("\n-> Ingrese el NUEVO año para el préstamo: ");
                        fecha.setYear(input.nextInt()-1900);
                        prestamo.setFechaInicio(fecha);
                        break;
                    case 2:
                        System.out.print("\n-> Ingrese el NUEVO día para FINALIZAR el préstamo: ");
                        fecha2.setDate(input.nextInt());
                        System.out.print("\n-> Ingrese el NUEVO mes para FINALIZAR el préstamo: ");
                        fecha2.setMonth(input.nextInt()-1);
                        System.out.print("\n-> Ingrese el NUEVO año para FINALIZAR el préstamo: ");
                        fecha2.setYear(input.nextInt()-1900);
                        prestamo.setFechaTermino(fecha2);
                        break;
                    case 3:
                        System.out.print("\n-> Ingrese el título del libro a ASOCIAR: ");
                        Libro libro1 = ls.buscarSoloUnLibro(input.next());
                        if(libro1 == null)
                        {
                            System.out.println("\n-> NO SE ENCONTRO EL LIBRO");
                        }else{
                            prestamo.setLibro(libro1);
                        }
                        break;
                    case 4:
                        prestamo.setCliente(cs.modificarCliente());
                        break;
                    case 5: 
                        validar = false;
                        break;
                }
            }while(validar);
                Utilidad.em.getTransaction().begin();
                Utilidad.em.merge(prestamo);
                Utilidad.em.getTransaction().commit();
                System.out.println("\nMODIFICACIÓN EXITOSA!");
            }
        }
    }
    
    
    public Prestamo buscarPrestamo(String id, String nom){
        Prestamo aux = new Prestamo();
        try
        {
            aux = (Prestamo) Utilidad.em.createQuery("SELECT p FROM Prestamo p WHERE p.cliente.id = :id AND p.libro.titulo = :nom").setParameter("id", id).setParameter("nom", nom).getSingleResult();
            
        }catch(Exception e){
            aux = null;
        }
        return aux;
    }
}
