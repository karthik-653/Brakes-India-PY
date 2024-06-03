using System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;
using TestgroundFour.Models;
using Microsoft.EntityFrameworkCore;

namespace TestgroundFour.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserdataController : ControllerBase
	{
        private readonly UserdataContext _dbContext;

        public UserdataController(UserdataContext dbContext)
        {
            _dbContext = dbContext;
        }


        
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Userdata>>> GetAllUserdata()
        {
            if(_dbContext.datas == null)
            {
                return NotFound();
            }
            return await _dbContext.datas.ToListAsync();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<PasswordDTO>> GetUserdata(int id)
        {
            var userData = await _dbContext.datas.FindAsync(id);
            if (userData == null)
            {
                return NotFound();
            }

            var userPasswordDTO = new PasswordDTO
            {
                Passsword = userData.Passsword
            };

            return userPasswordDTO;
        }


        [HttpPost]
        public async Task<ActionResult<UserDataDTO>> PostUserdata(Userdata data)
        {
            _dbContext.datas.Add(data);
            await _dbContext.SaveChangesAsync();

            var userDataDTO = new UserDataDTO
            {
                ID = data.ID,
                Email = data.Email
            };

            return CreatedAtAction(nameof(GetUserdata), new { id = userDataDTO.ID }, userDataDTO);
        }



        [HttpPut("{id}")]
        public async Task<ActionResult> PutUserdata(int id, Userdata data)
        {
            if (id != data.ID)
            {
                return BadRequest();
            }
            _dbContext.Entry(data).State = EntityState.Modified;
            try
            {
                await _dbContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserAvailable(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
                
            }
            return Ok();
        }

        private bool UserAvailable(int id)
        {
            return (_dbContext.datas?.Any(x => x.ID == id)).GetValueOrDefault();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUserdata(int id)
        {
            if (_dbContext.datas == null)
            {
                return NotFound();
            }
            var b = await _dbContext.datas.FindAsync(id);
            if (b == null)
            {
                return NotFound();
            }
            else
            {
                _dbContext.datas.Remove(b);
            }
            await _dbContext.SaveChangesAsync();
            return Ok();

        }

    }
}

