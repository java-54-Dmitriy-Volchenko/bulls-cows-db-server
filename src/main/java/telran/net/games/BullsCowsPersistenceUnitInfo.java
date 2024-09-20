package telran.net.games;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

public class BullsCowsPersistenceUnitInfo implements PersistenceUnitInfo {

	@Override
	public String getPersistenceUnitName() {
		
		return "bulls-cows-persistence-unit";
	}

	@Override
	public String getPersistenceProviderClassName() {
		
		return "org.hibernate.jpa.HibernatePersistenceProvider";
	}

	@Override
	public String getScopeAnnotationName() {
		return null;
	}

	@Override
	public List<String> getQualifierAnnotationNames() {
		return null;
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return null;
	}

	@Override
	public DataSource getJtaDataSource() {
		
		return null;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost/postgres");
		ds.setPassword("l12850FG");
		ds.setUsername("postgres");
		ds.setDriverClassName("org.postgresql.Driver");
		return ds;
	}

	@Override
	public List<String> getMappingFileNames() {
		
		return null;
	}

	@Override
	public List<URL> getJarFileUrls() {
		
		
		return null;
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		
		return null;
	}

	@Override
	public List<String> getManagedClassNames() {
		
		return List.of("telran.net.games.Gamer",
				"telran.net.games.Game",
				"telran.net.games.GameGamer",
				"telran.net.games.Move");
	}

	@Override
	public boolean excludeUnlistedClasses() {
		
		return false;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		
		return null;
	}

	@Override
	public ValidationMode getValidationMode() {
		
		return null;
	}

	@Override
	public Properties getProperties() {
		
		return null;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		
		return null;
	}

	@Override
	public void addTransformer(ClassTransformer transformer) {
		

	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		
		return null;
	}

}
