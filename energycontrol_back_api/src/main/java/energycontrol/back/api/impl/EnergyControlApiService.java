package energycontrol.back.api.impl;

import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import energycontrol.back.services.EnergyControlBackService;
import energycontrol.back.services.EnergyControlBackServiceImpl;
import mbt.utilities.PropertiesFileReader;
import mbt.utilities.PropertiesFileReaderFactory;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.ResourceBundleReaderFactory;

/**
 * Clase base para los servicios de la API
 * 
 */
public class EnergyControlApiService 
{
	private static final Logger logger = LogManager.getLogger(EnergyControlApiService.class);
	
	protected static final EnergyControlBackService backService = initializeBackService();
	protected static final ResourceBundleReader resourceBundleReader = initializeResourceBundleReader();
	
	/**
	 * Inicializa el backService con una instancia de la clase EnergyControlBackServiceImpl
	 * 
	 * @return
	 */
	protected static EnergyControlBackService initializeBackService()
	{
		logger.debug("Entrando en initializeBackService");
		
		EnergyControlBackService bs = null;
		
		try
		{
			bs = new EnergyControlBackServiceImpl();
		}
		catch(Exception e)
		{
			logger.error("Se ha producido un error inesperado en initializeBackService()", e);
			e.printStackTrace();
		}
		
		logger.debug("Saliendo de initializeBackService");
		
		return bs;
	}
	
	/**
	 * Inicializa el resourceBundleReader con una instancia creada a partir de las propiedades
	 * Locale.Language y Locale.Country definadas en el fichero energycontrol-api.properties
	 * 
	 * @return
	 */
	protected static ResourceBundleReader initializeResourceBundleReader()
	{
		logger.debug("Entrando en initializeResourceBundleReader");
		
		ResourceBundleReader rbr = null;
		
		try
		{
			PropertiesFileReader pfr = PropertiesFileReaderFactory.getPropertiesFileReader("energycontrol-api.properties");
			String sLocaleLanguage = pfr.getProperty("Locale.Language", "es");
			String sLocaleCountry= pfr.getProperty("Locale.Country", "ES");
			Locale locale = new Locale(sLocaleLanguage, sLocaleCountry);
			rbr = ResourceBundleReaderFactory.getResourceBundleReader("ApiMessages", locale);
		}
		catch(Exception e)
		{
			logger.error("Se ha producido un error inesperado en initializeResourceBundleReader()", e);
			e.printStackTrace();
		}
		
		logger.debug("Saliendo de initializeResourceBundleReader");
		
		return rbr;
	}
}
