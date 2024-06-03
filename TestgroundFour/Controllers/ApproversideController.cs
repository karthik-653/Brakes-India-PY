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
    public class ApproversideController : ControllerBase
    {
        private readonly ServersideContext _serversidedbcontext;
        private readonly ClientsideContext _clientsidedbcontext;
        private readonly ApproversideContext _approversidedbcontext;

        public ApproversideController(ServersideContext serversidedbcontext,
                                      ClientsideContext clientsidedbcontext,
                                      ApproversideContext approversidedbContext)
        {
            _serversidedbcontext = serversidedbcontext;
            _clientsidedbcontext = clientsidedbcontext;
            _approversidedbcontext = approversidedbContext;
        }

        [HttpGet("getappplants")]
        public async Task<ActionResult<UnitsResponse>> GetPlantsByDate([FromQuery] string date)
        {
            var plants = await _clientsidedbcontext.clientdatas
                .Where(s => s.Date == date)
                .Select(s => s.Plant)
                .Distinct()
                .ToListAsync();

            var response = new PlantsResponse
            {
                Plants = plants
            };

            return Ok(response);
        }

        [HttpGet("getdataforapproval/{plant}/{unit}/{celltype}/{cellno}/{model}/{process}/{date}")]
        public async Task<ActionResult<IEnumerable<object>>> GetData(
        string plant, int unit, string celltype, int cellno, string model,
        string process, string date)
        {
            var data = await _clientsidedbcontext.clientdatas
                .Where(c => c.Plant == plant && c.Unit == unit && c.Celltype == celltype
                    && c.Cellno == cellno && c.Model == model && c.Process == process && c.Date == date)
                .Select(c => new
                {
                    c.Pyorder,
                    c.Pyno,
                    c.Pydesc,
                    c.Pypurpose,
                    c.Pytype,
                    c.Date,
                    c.Check,
                    c.Updatename
                })
                .ToListAsync();

            return Ok(data);
        }

        [HttpGet("getdatafromquery")]
        public async Task<ActionResult<IEnumerable<object>>> GetData(
    string? plant = null, int? unit = null, string? celltype = null, int? cellno = null,
    string? model = null, string? process = null, string? date = null)
        {
            IQueryable<Clientside> query = _clientsidedbcontext.clientdatas;

            if (date != null)
            {
                query = query.Where(c => c.Date == date);
            }

            if (plant != null)
            {
                query = query.Where(c => c.Plant == plant);
            }

            if (unit != null)
            {
                query = query.Where(c => c.Unit == unit);
            }

            if (celltype != null)
            {
                query = query.Where(c => c.Celltype == celltype);
            }

            if (cellno != null)
            {
                query = query.Where(c => c.Cellno == cellno);
            }

            if (model != null)
            {
                query = query.Where(c => c.Model == model);
            }

            if (process != null)
            {
                query = query.Where(c => c.Process == process);
            }

            var data = await query
                .Select(c => new
                {
                    c.Pyorder,
                    c.Pyno,
                    c.Pydesc,
                    c.Pypurpose,
                    c.Pytype,
                    c.Date,
                    c.Check,
                    c.Updatename
                })
                .ToListAsync();

            return Ok(data);
        }

        [HttpGet("getalldatafromquery")]
        public async Task<ActionResult<IEnumerable<object>>> GetAllData(
    string? plant = null, int? unit = null, string? celltype = null, int? cellno = null,
    string? model = null, string? process = null, string? date = null)
        {
            IQueryable<Clientside> query = _clientsidedbcontext.clientdatas;

            if (date != null)
            {
                query = query.Where(c => c.Date == date);
            }

            if (plant != null)
            {
                query = query.Where(c => c.Plant == plant);
            }

            if (unit != null)
            {
                query = query.Where(c => c.Unit == unit);
            }

            if (celltype != null)
            {
                query = query.Where(c => c.Celltype == celltype);
            }

            if (model != null)
            {
                query = query.Where(c => c.Model == model);
            }

            if (process != null)
            {
                query = query.Where(c => c.Process == process);
            }

            var data = await query.ToListAsync();

            return Ok(data);
        }

        [HttpGet("getalldatafromquery_another")]
        public async Task<ActionResult<object>> AnotherGetAllData(
    string? plant = null, int? unit = null, string? celltype = null, int? cellno = null,
    string? model = null, string? process = null, string? date = null)
        {
            IQueryable<Clientside> query = _clientsidedbcontext.clientdatas;
            query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

            if (date != null && plant == null)
            {
                var uniquePlants = await query.Select(c => c.Plant).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

                return Ok(new { plants = uniquePlants });
            }

            if (date != null && plant != null && unit == null) 
            {
                var uniqueUnits = await query.Select(c => c.Unit).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));
                return Ok(new { units = uniqueUnits });
            }

            if (date != null && plant != null && unit != null && celltype == null) 
            {
                var uniqueCellTypes = await query.Select(c => c.Celltype).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

                return Ok(new { cellTypes = uniqueCellTypes });
            }

            if (date != null && plant != null && unit != null
                && celltype != null && cellno == null) 
            {
                var uniqueCellNos = await query.Select(c => c.Cellno).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

                return Ok(new { cellNumbers = uniqueCellNos });
            }

            if (date != null && plant != null && unit != null
                && celltype != null && cellno != null && model == null)
            {
                var uniqueModels = await query.Select(c => c.Model).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

                return Ok(new { models = uniqueModels });
            }

            if (date != null && plant != null && unit != null
                && celltype != null && cellno != null && model != null && process == null)
            {
                var uniqueProcess = await query.Select(c => c.Process).Distinct().ToListAsync();
                query = query.Where(c => !string.IsNullOrEmpty(c.Approvername));

                return Ok(new { process = uniqueProcess });
            }

            var data = await query.ToListAsync();

            return Ok(data);
        }

        [HttpGet("getalldatafromquery_approvercheck")]
        public async Task<ActionResult<object>> GetApproverCheck(
            string? date = null,string? plant = null, int? unit = null,
            string? celltype = null, int? cellno = null,
            string? model = null, string? process = null)
        {
            IQueryable<Approverside> query = _approversidedbcontext.approverdatas;

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

        //Post request for approval side
        [HttpPost("updatemultipleapprovalside")]
        public async Task<ActionResult> UpdateMultipleApprovalside(List<Approverside> dataList)
        {
            if (dataList == null || dataList.Count == 0)
            {
                return BadRequest("No data provided.");
            }

            _approversidedbcontext.approverdatas.AddRange(dataList);
            await _approversidedbcontext.SaveChangesAsync();
            return Ok();
        }
    }
}
