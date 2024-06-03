using System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TestgroundFour.Models;
using Microsoft.AspNetCore.Http;

namespace TestgroundFour.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ServersideController : ControllerBase
	{
        private readonly ServersideContext _dbContext;

        public ServersideController(ServersideContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpGet("/getval")]
        public async Task<ActionResult<IEnumerable<Serverside>>> GetAllServerside()
        {
            if (_dbContext.serverdatas == null)
            {
                return NotFound();
            }
            return await _dbContext.serverdatas.ToListAsync();
        }


        [HttpPost("/api/Serverside/newdata")]
        public async Task<ActionResult<Serverside>> PostServerside(Serverside data)
        {
            _dbContext.serverdatas.Add(data);
            await _dbContext.SaveChangesAsync();


            return Ok();
        }

        [HttpPut("{plant}/{unit}/{celltype}/{cellno}/{model}/{docno}/{process}/{pyorder}/{pyno}")]
        public async Task<IActionResult> PutServerside(string plant, int unit, string celltype, int cellno, string model, string docno, string process, int pyorder, string pyno, Serverside data)
        {
            if (!_dbContext.serverdatas.Any(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype && s.Cellno == cellno && s.Model == model && s.Docno == docno && s.Process == process && s.Pyorder == pyorder && s.Pyno == pyno))
            {
                return NotFound();
            }

            _dbContext.Entry(data).State = EntityState.Modified;

            try
            {
                await _dbContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ServersideExists(plant, unit, celltype, cellno, model, docno, process, pyorder, pyno))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        private bool ServersideExists(string plant, int unit, string celltype, int cellno, string model, string docno, string process, int pyorder, string pyno)
        {
            return _dbContext.serverdatas.Any(s => s.Plant == plant && s.Unit == unit && s.Celltype == celltype && s.Cellno == cellno && s.Model == model && s.Docno == docno && s.Process == process && s.Pyorder == pyorder && s.Pyno == pyno);
        }

        [HttpDelete("{plant}/{unit}/{celltype}/{cellno}/{model}/{docno}/{process}/{pyorder}/{pyno}")]
        public async Task<IActionResult> DeleteServerside(string plant, int unit, string celltype, int cellno, string model, string docno, string process, int pyorder, string pyno)
        {
            
            var entityToDelete = await _dbContext.serverdatas.FindAsync(plant, unit, celltype, cellno, model, docno, process, pyorder, pyno);
            if (entityToDelete == null)
            {
                return NotFound();
            }
            _dbContext.serverdatas.Remove(entityToDelete);
            await _dbContext.SaveChangesAsync();

            return NoContent();
        }


    }
}


