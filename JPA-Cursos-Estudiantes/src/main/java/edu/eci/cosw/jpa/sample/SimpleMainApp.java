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

import edu.eci.cosw.jpa.sample.model.Curso;
import edu.eci.cosw.jpa.sample.model.Estudiante;
import java.util.Arrays;
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
   
    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();
        
        //Crea Estudiante
        Estudiante estudiante1 = new Estudiante(2096904, "Ramiro Vargas");
        Estudiante estudiante2 = new Estudiante(2835697, "Jose luis");       
        Curso curso1 = new Curso(1, "Soluciones de Software", "SOSW");
        Curso curso2 = new Curso(2, "Construccion de Sotfware", "COSW");
        
        estudiante1.getCursos().add(curso1);
        estudiante2.getCursos().add(curso1);
        estudiante1.getCursos().add(curso2);
        estudiante2.getCursos().add(curso2);
        
        curso1.getEstudianteses().add(estudiante2);
        curso1.getEstudianteses().add(estudiante1);
        curso2.getEstudianteses().add(estudiante2);
        curso2.getEstudianteses().add(estudiante1);
                
        s.saveOrUpdate(curso1);
        s.saveOrUpdate(curso2);
        
        //s.saveOrUpdate(curso1);
        
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
