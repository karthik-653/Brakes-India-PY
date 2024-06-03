using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TestgroundFour.Models;

namespace TestgroundFour.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClientsideController : ControllerBase
    {
        private readonly ServersideContext _serversidedbcontext;
        private readonly ClientsideContext _clientsidedbcontext;
        private readonly ApproversideContext _approversidedbcontext;

        public ClientsideController(ServersideContext serversidedbcontext,
            ClientsideContext clientsidedbcontext, ApproversideContext approversideContext)
        {
            _serversidedbcontext = serversidedbcontext;
            _clientsidedbcontext = clientsidedbcontext;
            _approversidedbcontext = approversideContext;
        }


        [HttpGet("getplants")]
        public async Task<ActionResult<PlantsResponse>> GetUniquePlants()
        {
            var plants = await _serversidedbcontext.serverdatas
                .Select(s => s.Plant)
                .Distinct()
                .ToListAsync();

            var response = new PlantsResponse
            {
                Plants = plants
            };

            return Ok(response);
        }

        [HttpGet("getunits/{plant}")]
        public async Task<ActionResult<UnitsResponse>> GetUnitsByPlant(string plant)
        { 
            var units = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant)
                .Select(s => s.Unit)
                .Distinct()
                .ToListAsync();

            var response = new UnitsResponse
            {
                Units = units
            };

            return Ok(response);
        }

        [HttpGet("getcelltypes/{plant}/{unit}")]
        public async Task<ActionResult<CellTypesResponse>> GetCellTypesByPlantAndUnit
            (string plant, int unit)
        {
            var cellTypes = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant && s.Unit == unit)
                .Select(s => s.Celltype)
                .Distinct()
                .ToListAsync();

            var response = new CellTypesResponse
            {
                CellTypes = cellTypes
            };

            return Ok(response);
        }

        [HttpGet("getcellnumbers/{plant}/{unit}/{celltype}")]
        public async Task<ActionResult<CellNumbersResponse>> GetCellNumbersByPlantUnitAndCellType
            (string plant, int unit, string celltype)
        {
            var cellNumbers = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype)
                .Select(s => s.Cellno)
                .Distinct()
                .ToListAsync();

            var response = new CellNumbersResponse
            {
                CellNumbers = cellNumbers
            };

            return Ok(response);
        }

        [HttpGet("getmodels/{plant}/{unit}/{celltype}/{cellno}")]
        public async Task<ActionResult<ModelsResponse>> GetModelsByPlantUnitCellTypeAndCellNo
            (string plant,int unit, string celltype, int cellno)
        {
            var models = await _serversidedbcontext.serverdatas
        .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype &&
        s.Cellno == cellno)
        .Select(s => s.Model)
        .Distinct()
        .ToListAsync();

        var response = new ModelsResponse
          {
              Models = models
          };

            return Ok(response);
        }

        [HttpGet("getprocess/{plant}/{unit}/{celltype}/{cellno}/{model}")]
        public async Task<ActionResult<ProcessResponse>> GetProcess(string plant, int unit,
            string celltype, int cellno, string model)
        {
            var process = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype
                && s.Cellno == cellno && s.Model == model)
                .Select(s => s.Process)
                .Distinct()
                .ToListAsync();

            var response = new ProcessResponse
            {
                Process = process
            };
            return Ok(response);
        }

        [HttpGet("getname/{plant}/{unit}/{celltype}/{cellno}/{model}/{date}/{process}")]
        public async Task<ActionResult<object>> GetProcess(string plant, int unit, string celltype,
    int cellno, string model, string date, string process)
        {
            var name = await _clientsidedbcontext.clientdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype
                    && s.Cellno == cellno && s.Model == model && s.Date == date && s.Process == process)
                .Select(s => s.Updatename)
                .FirstOrDefaultAsync();

            var response = new
            {
                updatename = name
            };
            return Ok(response);
        }


        [HttpGet("getcheck")]
        public async Task<ActionResult<IEnumerable<string>>> GetCheck(

    [FromQuery(Name = "plant")] string plant,
    [FromQuery(Name = "unit")] int unit,
    [FromQuery(Name = "celltype")] string celltype,
    [FromQuery(Name = "cellno")] int cellno,
    [FromQuery(Name = "model")] string model,
    [FromQuery(Name = "process")] string process,
    [FromQuery(Name = "date")] string date)
        {
            var checks = await _clientsidedbcontext.clientdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype
                            && s.Cellno == cellno && s.Model == model && s.Process == process
                            && s.Date == date)
                .Select(s => s.Check)
                .ToListAsync();

            var responseData = new { check = checks };
            return Ok(responseData);
        }

        [HttpGet("getpydata/{plant}/{unit}/{celltype}/{cellno}/{model}/{process}")]
        public async Task<ActionResult<IEnumerable<object>>> GetPyData(
            string plant, int unit, string celltype, int cellno, string model, string process)
        {
            var data = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype
                && s.Cellno == cellno && s.Model == model && s.Process == process)
                .Select(s => new
                {
                    s.Pyorder,
                    s.Pyno,
                    s.Pydesc,
                    s.Pypurpose,
                    s.Pytype
                })
                .ToListAsync();
            return Ok(data);
        }

        [HttpGet("getdocno/{plant}/{unit}/{celltype}/{cellno}/{model}")]
        public async Task<ActionResult<object>> GetDocno(string plant, int unit, string celltype, int cellno, string model)
        {
            var docno = await _serversidedbcontext.serverdatas
                .Where(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype && s.Cellno == cellno && s.Model == model)
                .Select(s => s.Docno)
                .FirstOrDefaultAsync();

            if (docno == null)
            {
                return NotFound(new { message = "Docno not found for the provided parameters." });
            }

            return Ok(new { docno = docno });
        }

        [HttpPost("updateclientside")]
        public async Task<ActionResult> UpdateClientside(Clientside data)
        {
            _clientsidedbcontext.clientdatas.Add(data);
            await _clientsidedbcontext.SaveChangesAsync();
            return Ok();
        }

        [HttpPost("updatemultipleclientside")]
        public async Task<ActionResult> UpdateMultipleClientside(List<Clientside> dataList)
        {
            if (dataList == null || dataList.Count == 0)
            {
                return BadRequest("No data provided.");
            }

            foreach (var data in dataList)
            {
                data.Approvername = "";
                data.Approvercheck = "";
            }
            _clientsidedbcontext.clientdatas.AddRange(dataList);
            await _clientsidedbcontext.SaveChangesAsync();

            return Ok();
        }


        [HttpPut("updatechoiceclientside")]
        public async Task<ActionResult> UpdateChoiceClientside([FromBody] IEnumerable<Clientside> clientData)
        {
            foreach (var data in clientData)
            {
                var dataToUpdate = await _clientsidedbcontext.clientdatas
                    .Where(s => s.Plant == data.Plant &&
                                s.Unit == data.Unit &&
                                s.Celltype == data.Celltype &&
                                s.Cellno == data.Cellno &&
                                s.Model == data.Model &&
                                s.Process == data.Process &&
                                s.Date == data.Date &&
                                s.Docno == data.Docno &&
                                s.Pyno == data.Pyno &&
                                s.Pydesc == data.Pydesc &&
                                s.Pyorder == data.Pyorder &&
                                s.Pypurpose == data.Pypurpose &&
                                s.Pytype == data.Pytype
                                )
                    .ToListAsync();

                if (dataToUpdate != null && dataToUpdate.Count > 0)
                {
                    foreach (var item in dataToUpdate)
                    {
                        item.Approvercheck = data.Approvercheck;
                        item.Approvername = data.Approvername;
                    }
                }
            }

            await _clientsidedbcontext.SaveChangesAsync();
            return Ok();
        }
        
        [HttpGet("getsorteddata")]
        public async Task<ActionResult<IEnumerable<Clientside>>> GetSortedData()
        {
            // Retrieve all data sorted by date
            var sortedData = await _clientsidedbcontext.clientdatas
                .OrderBy(s => s.Date)
                .ToListAsync();

            return Ok(sortedData);
        }

        [HttpGet("getalldatafromquery_another")]
        public async Task<ActionResult<object>> AnotherGetAllData(
            string? plant = null, int? unit = null, string? celltype = null, int? cellno = null,
            string? model = null, string? process = null, string? date = null)
        {

            if (string.IsNullOrEmpty(plant) && !unit.HasValue && string.IsNullOrEmpty(celltype) &&
    !cellno.HasValue && string.IsNullOrEmpty(model) && string.IsNullOrEmpty(process) &&
    string.IsNullOrEmpty(date))
            {
                return BadRequest("No query parameters provided.");
            }

            IQueryable<Clientside> query = _clientsidedbcontext.clientdatas;

            // Filter for non-null and non-empty Approvername and get distinct plants
            if (date != null && plant == null)
            {
                var uniquePlants = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Select(c => c.Plant)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { plants = uniquePlants });
            }

            if (date != null && plant != null && unit == null)
            {
                var uniqueUnits = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Where(c => c.Plant == plant)
                    .Select(c => c.Unit)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { units = uniqueUnits });
            }

            if (date != null && plant != null && unit != null && celltype == null)
            {
                var uniqueCellTypes = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Where(c => c.Plant == plant)
                    .Where(c => c.Unit == unit)
                    .Select(c => c.Celltype)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { cellTypes = uniqueCellTypes });
            }

            if (date != null && plant != null && unit != null && celltype != null && cellno == null)
            {
                var uniqueCellNos = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Where(c => c.Plant == plant)
                    .Where(c => c.Unit == unit)
                    .Where(c => c.Celltype == celltype)
                    .Select(c => c.Cellno)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { cellNumbers = uniqueCellNos });
            }

            if (date != null && plant != null && unit != null && celltype != null && cellno != null && model == null)
            {
                var uniqueModels = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Where(c => c.Plant == plant)
                    .Where(c => c.Unit == unit)
                    .Where(c => c.Celltype == celltype)
                    .Where(c => c.Cellno == cellno)
                    .Select(c => c.Model)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { models = uniqueModels });
            }

            if (date != null && plant != null && unit != null && celltype != null && cellno != null && model != null && process == null)
            {
                var uniqueProcesses = await query
                    .Where(c => !string.IsNullOrEmpty(c.Approvername))
                    .Where(c => c.Date == date)
                    .Where(c => c.Plant == plant)
                    .Where(c => c.Unit == unit)
                    .Where(c => c.Celltype == celltype)
                    .Where(c => c.Cellno == cellno)
                    .Where(c => c.Model == model)
                    .Select(c => c.Process)
                    .Distinct()
                    .ToListAsync();
                return Ok(new { process = uniqueProcesses });
            }

            // Filter out records with null or empty Approvername for final data
            var data = await query
                .Where(c => !string.IsNullOrEmpty(c.Approvername))
                .ToListAsync();
            return Ok(data);
        }

        [HttpGet("getalldatafromquery_approvercheck")]
        public async Task<ActionResult<object>> GetApproverCheck(
            string? date = null, string? plant = null, int? unit = null,
            string? celltype = null, int? cellno = null,
            string? model = null, string? process = null)
        {
            IQueryable<Clientside> query = _clientsidedbcontext.clientdatas;

            if (date != null)
            {
                query = query.Where(a => a.Date == date);
            }

            if (plant != null)
            {
                query = query.Where(a => a.Plant == plant);
            }

            if (unit != null)
            {
                query = query.Where(a => a.Unit == unit);
            }

            if (celltype != null)
            {
                query = query.Where(a => a.Celltype == celltype);
            }

            if (cellno != null)
            {
                query = query.Where(a => a.Cellno == cellno);
            }

            if (model != null)
            {
                query = query.Where(a => a.Model == model);
            }

            if (process != null)
            {
                query = query.Where(a => a.Process == process);
            }

            var approverCheckData = await query.Select(a => a.Approvercheck).ToListAsync();
            return Ok(new { approvercheck = approverCheckData });
        }

        [HttpGet("getsorteddatawithoutapproval")]
        public async Task<ActionResult<IEnumerable<Clientside>>> GetSortedDataNoApproval()
        {
            var sortedData = await _clientsidedbcontext.clientdatas
                .Where(clientData => string.IsNullOrEmpty(clientData.Approvername))
                .OrderBy(s => s.Date)
                .ToListAsync();

            return Ok(sortedData);
        }

    }
}
