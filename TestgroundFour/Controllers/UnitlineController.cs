using System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using TestgroundFour.Models;

namespace TestgroundFour.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UnitlineController : ControllerBase
	{
        private readonly UnitlineContext _unitlinedbContext;

		public UnitlineController(UnitlineContext unitlinedbContext)
		{
            _unitlinedbContext = unitlinedbContext;
		}

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Unitline>>> GetAllUnitline()
        {
            if (_unitlinedbContext.Unitlinedatas == null)
            {
                return NotFound();
            }
            return await _unitlinedbContext.Unitlinedatas.ToListAsync();
        }

        [HttpGet("{unit}")]
        public async Task<ActionResult<List<Unitline>>> GetUnitline(string unit)
        {
            var unitline = await _unitlinedbContext.Unitlinedatas.Where(u => u.Unit == unit).ToListAsync();
            if (unitline == null || !unitline.Any())
            {
                return NotFound();
            }
            return unitline;
        }

        [HttpGet("{unit}/assemblies")]
        public async Task<ActionResult<List<string>>> GetAssembliesByUnit(string unit)
        {
            var assembly = await _unitlinedbContext.Unitlinedatas
                                                     .Where(u => u.Unit == unit)
                                                     .Select(u => u.Assembly)
                                                     .Distinct()
                                                     .ToListAsync();

            if (assembly == null || !assembly.Any())
            {
                return NotFound();
            }

            return assembly;
        }


        [HttpGet("{unit}/{assembly}")]
        public async Task<ActionResult<List<Unitline>>> GetUnitlinesByUnitAndAssembly(string unit, string assembly)
        {
            var unitlines = await _unitlinedbContext.Unitlinedatas
                                                      .Where(u => u.Unit == unit && u.Assembly == assembly)
                                                      .ToListAsync();

            if (unitlines == null || !unitlines.Any())
            {
                return NotFound();
            }

            return unitlines;
        }

        [HttpGet("unique-units")]
        public async Task<ActionResult<List<string>>> GetUniqueUnitValues()
        {
            var uniqueUnits = await _unitlinedbContext.Unitlinedatas
                                                       .Select(u => u.Unit)
                                                       .Distinct()
                                                       .ToListAsync();

            if (uniqueUnits == null || !uniqueUnits.Any())
            {
                return NotFound();
            }

            return uniqueUnits;
        }


        [HttpPost]
        public async Task<ActionResult<Unitline>> PostUnitline(Unitline data)
        {
            _unitlinedbContext.Unitlinedatas.Add(data);
            await _unitlinedbContext.SaveChangesAsync();

            var Unitline = new Unitline
            {
                Index = data.Index,
                Unit = data.Unit,
                Assembly = data.Assembly,
                Cell = data.Cell

            };

            return CreatedAtAction(nameof(GetUnitline), new { unit = Unitline.Index }, Unitline);
        }



    }
}

