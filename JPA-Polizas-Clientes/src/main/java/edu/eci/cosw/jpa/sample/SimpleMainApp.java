/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cosw.jpa.sample;

import edu.eci.cosw.jpa.sample.model.Cliente;
import edu.eci.cosw.jpa.sample.model.ClienteId;
import edu.eci.cosw.jpa.sample.model.PolizaAprobada;
import edu.eci.cosw.jpa.sample.model.PolizaAprobadaId;
import edu.eci.cosw.jpa.sample.model.TipoPoliza;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author hcadavid
 */
public class SimpleMainApp {
   
    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();
        
        tx.commit();
        
        /******Agrega a la Base un cliente y una nueva poliza aprobada******/
        
        Cliente c= new Cliente(new ClienteId(10, "cc"), "Ramiro", "Calle  # 1 34", "12345");
        TipoPoliza tp= new TipoPoliza(1, "p1", "Esta es la poliza 1", 123456789);
        s.persist(c);
        s.persist(tp);
        
        PolizaAprobada pa=new PolizaAprobada();
                
        pa.setId(new PolizaAprobadaId(c.getId().getId(), c.getId().getTipoId(), tp.getCodigoPoliza())); 
        pa.setTiposPoliza(tp);
        pa.setClientes(c);               
        pa.setFechaAprobacion(new Date());
        pa.setFechaVencimiento(new Date());
                
        s.persist(pa);
        
        /****Consulta la poliza recien agregada****/
        
        PolizaAprobada p = (PolizaAprobada)s.load(PolizaAprobada.class, new PolizaAprobadaId(10, "cc", 1));
        
        System.out.println("id: "+p.getId().getClientesId());
        System.out.println("tipo id: "+p.getId().getClientesTipoId());
        System.out.println("Nombre: "+p.getClientes().getNombre());
        System.out.println("cod poliza: "+p.getTiposPoliza().getCodigoPoliza());
        System.out.println("fecha de Aprovación: "+p.getFechaAprobacion());
        System.out.println("fecha de Vencimiento: "+p.getFechaVencimiento());
        
        s.close();
        sf.close();

    }

    public static SessionFactory getSessionFactory() {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        // builds a session factory from the service registry
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
