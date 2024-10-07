package energycontrol.back.api.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import energycontrol.back.api.MSourceService;
import energycontrol.back.entities.MSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;

@Api
@Path("/MSources")
public class MSourceServiceImpl extends EnergyControlApiService  implements MSourceService
{
	
	@ApiOperation(
		value = "Listado del maestro de orígenes de datos",
	    notes = "Esto es una nota",
	    response = MSource.class,
	    responseContainer = "List"
	    )
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMSources() 
	{
		try
		{
			GenericActionResult<List<MSource>> gar = backService.getMSources();
			
			if(EResult.OK == gar.getResult())
			{
				return Response.ok(gar.getResultObject()).build();
			}
			else
			{
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gar.getExceptionsMessages()).build();
			}
		}
		catch(Exception e)
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
	}

}
