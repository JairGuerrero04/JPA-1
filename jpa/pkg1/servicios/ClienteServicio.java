
package jpa.pkg1.servicios;

import Utilidades.Utilidad;
import jpa.pkg1.entidades.Cliente;
import static jpa.pkg1.JPA1.input;

public class ClienteServicio {
    
    public Cliente crearCliente(){
        Cliente cliente = new Cliente();
        
        System.out.print("\n-> Ingrese el nombre del cliente: ");
        cliente.setNombre(input.next());
        System.out.print("\n-> Ingrese el apellido del cliente: ");
        cliente.setApellido(input.next());
        System.out.print("\n-> Ingrese el teléfono del cliente: ");
        cliente.setTelefono(input.next());
        System.out.print("\n-> Ingrese el número de documento: ");
        cliente.setDocumento(input.nextLong());
        
        Cliente aux = this.buscarCliente(cliente.getNombre(), cliente.getApellido());
        if(aux==null)
        {
            Utilidad.em.getTransaction().begin();
            Utilidad.em.persist(cliente);
            Utilidad.em.getTransaction().commit();
            return cliente;
        }else{
            System.out.print("\n-> CLIENTE YA REGISTRADO");
            return aux;
        }
    }
    
    public Cliente buscarCliente(String nom, String ape){
        Cliente aux = new Cliente();
        try
        {
            aux = (Cliente) Utilidad.em.createQuery("SELECT c FROM Cliente c WHERE c.nombre = :nom AND c.apellido = :ape").setParameter("nom", nom).setParameter("ape",ape).getSingleResult();
            
        }catch(Exception e){
            aux = null;
        }
        return aux;
    }
    
    
    public Cliente modificarCliente(){
        Cliente cliente = new Cliente(); //NUEVO OBJETO
        System.out.print("\n-> Ingrese el nombre del NUEVO cliente: ");
        cliente.setNombre(input.next());
        System.out.print("\n-> Ingrese el apellido del NUEVO cliente: ");
        cliente.setApellido(input.next());
        System.out.print("\n-> Ingrese el número telefónico del NUEVO cliente: ");
        cliente.setTelefono(input.next());
        System.out.print("\n-> Ingrese el documento del NUEVO cliente: ");
        cliente.setDocumento(input.nextLong());
        
        Cliente cliente1 = this.buscarCliente(cliente.getNombre(), cliente.getApellido());
        if(cliente1==null)
        {
            return cliente;
        }else{
            return cliente1;
        }
    }
}
