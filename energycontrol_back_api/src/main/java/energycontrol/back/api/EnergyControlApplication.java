package energycontrol.back.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Info;


@ApplicationPath("api")	//Ruta base de los métodos del servicio: http://localhost:8080/energycontrol/api
public class EnergyControlApplication extends Application
{
	public EnergyControlApplication()
	{
		
		/**
		 * Generar la documentación de la API con Swagger
		 * 
		 * Fichero swagger json con la documentación del API:
		 *     	http://localhost:8080/energycontrol/api/swagger.json
		 * 
		 */

	    BeanConfig beanConfig = new BeanConfig();
	    Info info = new Info();
	    info.setVersion("1.0.0");
	    info.setTitle("Energy Control");
	    beanConfig.getSwagger().info(info);
	    beanConfig.setSchemes(new String[]{"http"});
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/energycontrol/api");
		beanConfig.setResourcePackage("energycontrol.back.api");
		beanConfig.setScan(true);
	}
}
