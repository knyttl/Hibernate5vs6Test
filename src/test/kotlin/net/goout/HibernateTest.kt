package net.goout

import org.hibernate.Session
import org.hibernate.cfg.Configuration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HibernateTest {
    @Test
    fun `persist, flush, refresh entity with OneToMany map relation`() {
        // Firstly, let's connect to DB, make sure to have set these java properties:
        //   -Dhibernate.server = "10.10.10.01"
        //   -Dhibernate.database = "my-database-name"
        //   -Dhibernate.user = "my-user-name"
        //   -Dhibernate.password = "my-secret-password
        // See hibernate.cfg.xml for details
        val sessionFactory = Configuration().configure().buildSessionFactory()
        val session: Session = sessionFactory.openSession()
        session.beginTransaction()

        // Then create an entity with complex OneToMany map.
        val car = Car().also { it.name = "Audi" }
        session.persist(car)
        session.flush()

        // Refreshing works correctly on Hibernate 5, but fails on Hibernate 6.
        session.refresh(car)

        // There must be brand name for each country - not less, not more.
        // Hibernate 5 correctly passes, logs here: https://gist.github.com/knyttl/ac8010f853bc22b1a0176e856cad0a3e
        assertEquals(3, car.brandNames.size)
        for (country in Country.values()) {
            assertTrue(country in car.brandNames)
        }

        // And close the session.
        session.getTransaction().rollback()
        session.close()
        sessionFactory.close()
    }
}
