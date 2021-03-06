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

import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
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
    
    public static void main(String a[]) {
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction(); 
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        
        Paciente pl = (Paciente) s.load(Paciente.class, new PacienteId(1, "cc"));
        
        Set<Consulta> setConsultas = pl.getConsultas();
        
        System.out.println("CONSULTAS OBTENIDAS: "+"\n"+pl.getConsultas()
                          +"\n"+"PACIENTE: "+pl.getNombre());
        
        setConsultas.add(new Consulta(cal.getTime(), "resumen"));
        pl.setConsultas(setConsultas);
        
        /**
         * No usar en exceso, esta agregando muchas consultas.
         * 
         */
        s.saveOrUpdate(pl);
                
        System.out.println("CONSULTAS NUEVAS OBTENIDAS: "+"\n"+pl.getConsultas()
                          +"\n"+"PACIENTE: "+pl.getNombre());
        
        
        tx.commit();    
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