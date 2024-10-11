package energycontrol.back.api.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import energycontrol.back.api.MSourceService;
import energycontrol.back.entities.MSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;

@Api
@Path("/MSources")
public class MSourceServiceImpl extends EnergyControlApiService  implements MSourceService
{
	private static final Logger logger = LogManager.getLogger(MSourceServiceImpl.class);
	
	@ApiOperation(
		value = "Listado del maestro de orígenes de datos",
		notes = "Devuelve todo el maestro de orígenes de datos",
		tags = "Maestro de orígenes de datos",
	    response = MSource.class,
	    responseContainer = "List"
	    )
	@ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "Operación ejecutada correctamente"),
		@ApiResponse(code = 500, message = "Se ha producido un error interno al ejecutar la operación")
		})
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
	
	@ApiOperation(
		value = "Origen de datos maestro por código",
		notes = "Devuelve el origen de datos maestro con código igual al pasado por argumento",
		tags = "Maestro de orígenes de datos",
	    response = MSource.class,
	    responseContainer = "String"
	    )
	@ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "Operación ejecutada correctamente"),
	    @ApiResponse(code = 404, message = "Origen de datos maestro no encontrado"),
		@ApiResponse(code = 500, message = "Se ha producido un error interno al ejecutar la operación")
		})
	@GET
	@Path("/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByCode(@PathParam("code") String code)
	{
		try
		{
			GenericActionResult<MSource> gar = backService.findMSourceByCode(code);
			
			if(EResult.OK == gar.getResult())
			{
				if(gar.getResultObject() == null)
				{
					return Response.status(Status.NOT_FOUND).build();
				}
				else
				{
					return Response.ok(gar.getResultObject()).build();
				}
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
	
	@ApiOperation(
		value = "Crea un nuevo origen de datos maestro",
		notes = "No debe exitir ya otro origen de datos maestro con el mismo código",
		tags = "Maestro de orígenes de datos",
	    response = MSource.class,
	    responseContainer = "String"
	    )
	@ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "Operación ejecutada correctamente"),
	    @ApiResponse(code = 412, message = "El origen de datos maestro a crear ya existe"),
		@ApiResponse(code = 500, message = "Se ha producido un error interno al ejecutar la operación")
		})
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(MSource mSource)
	{
		try
		{
			logger.debug("Entrando en create()");
			
			GenericActionResult<MSource> gar = backService.findMSourceByCode(mSource.getCode());
			
			if(EResult.OK == gar.getResult())
			{
				if(gar.getResultObject() == null)
				{
					gar = backService.saveMSource(mSource);
					
					if(EResult.OK == gar.getResult())
					{
						logger.debug("El MSource se ha creado correctamente");
						
						return Response.ok(gar.getResultObject()).build();
					}
					else
					{
						logger.debug(String.format("Se ha producido un error al intentar crear el MSource: %s", gar.getExceptionsMessages()));
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gar.getExceptionsMessages()).build();
					}
				}
				else
				{
					logger.debug("Se ha intentado crear un MSource ya existente");
					String message = resourceBundleReader.getLocalizedMessage(EApiMessagesKeys.CreateMSourceAlreayExists.stringValue, null);
					return Response.status(Status.PRECONDITION_FAILED).entity(message).build();
				}
			}
			else
			{
				logger.debug(String.format("Error al comprobar si ya existe un MSource con el mismo código: %s", gar.getExceptionsMessages()));
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gar.getExceptionsMessages()).build();
			}
		}
		catch(Exception e)
		{
			logger.error("Error no contralado en create()", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@ApiOperation(
		value = "Actualiza el origen de datos maestro",
		notes = "Si el origen de datos maestro a actualizar no existe, lo crea",
		tags = "Maestro de orígenes de datos",
	    response = MSource.class,
	    responseContainer = "String"
	    )
	@ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "Operación ejecutada correctamente"),
		@ApiResponse(code = 500, message = "Se ha producido un error interno al ejecutar la operación")
		})
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(MSource mSource)
	{
		try
		{
			logger.debug("Entrando en update()");
			
			GenericActionResult<MSource>  gar = backService.saveMSource(mSource);
			
			if(EResult.OK == gar.getResult())
			{
				logger.debug("El MSource se ha actualizado correctamente");
				
				return Response.ok(gar.getResultObject()).build();
			}
			else
			{
				logger.debug(String.format("Se ha producido un error al intentar actualizar el MSource: %s", gar.getExceptionsMessages()));
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gar.getExceptionsMessages()).build();
			}
		}
		catch(Exception e)
		{
			logger.error("Error no contralado en update()", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@ApiOperation(
		value = "Borra el origen de datos maestro",
		tags = "Maestro de orígenes de datos"
	    )
	@ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "Operación ejecutada correctamente"),
		@ApiResponse(code = 500, message = "Se ha producido un error interno al ejecutar la operación")
		})
	@DELETE
	@Path("/delete/{code}")
	public Response delete(@PathParam("code") String code)
	{
		try
		{
			logger.debug("Entrando en delete()");
			
			ActionResult ar = backService.deleteMSource(code);
			
			if(EResult.OK == ar.getResult())
			{
				logger.debug("El MSource se ha borrado correctamente");
				
				return Response.ok().build();
			}
			else
			{
				logger.debug(String.format("Se ha producido un error al intentar borrar el MSource: %s", ar.getExceptionsMessages()));
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ar.getExceptionsMessages()).build();
			}
		}
		catch(Exception e)
		{
			logger.error("Error no contralado en delete()", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
