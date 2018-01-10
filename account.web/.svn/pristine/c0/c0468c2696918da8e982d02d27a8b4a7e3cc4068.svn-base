package com.bliss.account.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.bliss.framework.common.Config;

public class DAFactory {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, Object> settings = new HashMap<>();
				settings.put(Environment.DRIVER, Config.getParam("xAccount_database", "driver"));
				settings.put(Environment.URL, Config.getParam("xAccount_database", "url"));
				settings.put(Environment.USER, Config.getParam("xAccount_database", "user_name"));
				settings.put(Environment.PASS, Config.getParam("xAccount_database", "password"));
				settings.put(Environment.HBM2DDL_AUTO, "update");
				settings.put(Environment.SHOW_SQL, true);
				// settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				// c3p0 configuration
				settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
				settings.put(Environment.C3P0_MAX_SIZE, 32);        //Maximum size of pool
				settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted 
				settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
				settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size

				settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 64);

				registryBuilder.applySettings(settings);

				registry = registryBuilder.build();
				MetadataSources sources = new MetadataSources(registry).addResource("hibernate.cfg.xml");
				Metadata metadata = sources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception ex) {
				shutdown();
				ex.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			synchronized (registry) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}
	}
}
